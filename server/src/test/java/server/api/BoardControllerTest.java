package server.api;

import commons.Board;
import commons.Card;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.CardTagRepository;
import server.services.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BoardControllerTest {

    private TestBoardRepository boardRepository;
    private TestColumnRepository columnRepository;
    private TestCardRepository cardRepository;
    private TestSubtaskRepository subtaskRepository;

    private BoardService boardService;
    private ColumnService columnService;
    private CardService cardService;
    private SubtaskService subtaskService;

    private CardTagService cardTagService;
    private BoardController boardController;
    private ColumnController columnController;
    private CardController cardController;
    private CardTagController cardTagController;
    private CardTagRepository cardTagRepository;





    @BeforeEach
    void setUp() {
        boardRepository = new TestBoardRepository();
        columnRepository = new TestColumnRepository();
        subtaskRepository = new TestSubtaskRepository();
        cardTagRepository = new TestCardTagRepository();
        cardRepository = new TestCardRepository();


        boardService = new BoardService(boardRepository);
        columnService = new ColumnService(columnRepository);
        cardService = new CardService(cardRepository, subtaskRepository);
        subtaskService = new SubtaskService(subtaskRepository);
        cardTagService = new CardTagService(cardTagRepository);

        cardController = new CardController(cardService, columnService, subtaskService);
        columnController = new ColumnController(columnRepository, columnService, boardService, cardService);
        cardTagController = new CardTagController(cardTagService, boardService, cardService);
        boardController = new BoardController(boardService, columnService, cardTagService, cardService, cardTagController);

    }


    @Test
    void getAllBoards() {
        List<Board> boards = new ArrayList<>();
        Board b1 = new Board("Test1");
        Board b2 = new Board("Test2");
        Board b3 = new Board("Test3");
        b1.setId(0);
        b2.setId(1);
        b3.setId(2);
        boards.add(b1);
        boards.add(b2);
        boards.add(b3);

        List<Board> ret = boardController.getAllBoards();

        assertEquals(boards, ret);
    }

    @Test
    void getBoardById() {
        Board board = new Board("Test1");
        board.setId(0);
        ResponseEntity<Board> ret = boardController.getBoardByBoardId(0L);

        assertEquals(board, ret.getBody());
    }

    @Test
    void getBoardByIdNotFound() {
        // not in the database
        ResponseEntity<Board> ret = boardController.getBoardByBoardId(10L);

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void addBoardSuccessful() {
        ResponseEntity<Board> ret = boardController.addBoard("Test3");

        Board b = (Board) ret.getBody();
        assertEquals("Test3", b.getTitle());
    }

    @Test
    void addBoardEmptyString() {
        ResponseEntity<Board> ret = boardController.addBoard("");
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void addBoardNullString() {
        ResponseEntity<Board> ret = boardController.addBoard(null);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void putBoardSuccessful() {
        ResponseEntity<Board> ret = boardController.editBoardTitle("Test3", 0L);

        Board updated = new Board("Test3");
        updated.setId(1L);

        List<Board> allBoards = boardController.getAllBoards();

        for (Board b : allBoards) {
            assertNotEquals(b.getTitle(), "Test1");
        }

        assertEquals(Objects.requireNonNull(ret.getBody()).getTitle(), updated.getTitle());
    }

    @Test
    void putBoardNotFound() {
        ResponseEntity<Board> ret = boardController.editBoardTitle("ThisBoardIdDoesNotExist", 23450969123L);

        List<Board> allBoards = boardController.getAllBoards();
        for (Board b : allBoards) {
            assertNotEquals("ThisBoardIdDoesNotExist", b.getTitle());
        }

        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void deleteBoardNotFound() {
        ResponseEntity ret = boardController.deleteBoard(3L);

        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void deleteBoardFound() {
        ResponseEntity<Board> ret = boardController.deleteBoard(0L);
        Board expected = new Board("Test1");
        expected.setId(0L);

        assertEquals(expected, ret.getBody());
        assertEquals(ResponseEntity.badRequest().build(), boardController.deleteBoard(0L));

        for (Column c : columnRepository.findAll()) {
            assertNotEquals(0, c.getBoardId());
        }
    }

    @Test
    void getColumnsByBoardIdTestSuccess() {
        ResponseEntity<List<Column>> ret = boardController.getColumnsByBoardId(0L);

        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Test1", 0L));
        columns.get(0).setId(1);

        columns.add(new Column("Test2", 0L));
        columns.get(1).setId(2);

        Iterator<Column> columnIterator = columns.listIterator();

        for (Column c : ret.getBody()) {
            Column temp = columnIterator.next();
            assertEquals(c.toString(), temp.toString());
        }


    }

    @Test
    void getColumnsByBoardIdTestNotFound() {
        ResponseEntity<List<Column>> ret = boardController.getColumnsByBoardId(6969L);
        assertEquals(ret, ResponseEntity.badRequest().build());
    }

    @Test
    void deleteBoardAndItsColumnsTestSuccess() {
        ResponseEntity<List<Column>> columnsWithBoardId = boardController.getColumnsByBoardId(5L);
        boardController.deleteBoard(5L);
        assertEquals(ResponseEntity.badRequest().build(), boardController.getColumnsByBoardId(5L));
    }

    @Test
    void deleteNonExistingBoardTest() {
        ResponseEntity<List<Column>> allColumns = columnController.getAllColumns();
        List<Card> allCards = cardController.getAllCards();
        boardController.deleteBoard(39485768L);
        assertEquals(allCards, cardController.getAllCards());
        assertEquals(allColumns, columnController.getAllColumns());
    }
}

