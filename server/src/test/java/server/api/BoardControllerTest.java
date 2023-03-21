package server.api;

import commons.Board;
import commons.Card;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.BoardRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    private TestBoardRepository boardRepository;
    private TestColumnRepository columnRepository;
    private TestCardRepository cardRepository;
    private BoardController boardController;
    private ColumnController columnController;
    private CardController cardController;

    @BeforeEach
    void setUp() {
        boardRepository = new TestBoardRepository();
        columnRepository = new TestColumnRepository();
        cardRepository = new TestCardRepository();
        cardController = new CardController(cardRepository, columnRepository);
        columnController = new ColumnController(columnRepository, boardRepository, cardRepository, cardController);
        boardController = new BoardController(boardRepository, columnRepository, columnController);
    }

    // @Test
    // void getAllBoards() {
    //     List<Board> boards = new ArrayList<>();
    //     Board b1 = new Board("Test1");
    //     Board b2 = new Board("Test2");
    //     Board b3 = new Board("Test3");
    //     b1.setId(0);
    //     b2.setId(1);
    //     b3.setId(5);
    //     boards.add(b1);
    //     boards.add(b2);
    //     boards.add(b3);

    //     List<Board> ret = boardController.getAllBoards();

    //     assertEquals(boards, ret);
    // }

    // @Test
    // void getBoardById() {
    //     Board board = new Board("Test1");
    //     board.setId(0);
    //     Board ret = boardController.getBoardById(0L);

    //     assertEquals(board, ret);
    // }

    // @Test
    // void addBoardSuccessful() {
    //     ResponseEntity ret = boardController.addBoard("Test3");

    //     Board b = (Board) ret.getBody();
    //     assertEquals("Test3", b.getTitle());
    // }

    // @Test
    // void addBoardEmptyString() {
    //     ResponseEntity ret = boardController.addBoard("");
    //     assertEquals(ResponseEntity.badRequest().build(), ret);
    // }

    // @Test
    // void addBoardNullString() {
    //     ResponseEntity ret = boardController.addBoard(null);
    //     assertEquals(ResponseEntity.badRequest().build(), ret);
    // }

    // @Test
    // void putBoardSuccessfull() {
    //     ResponseEntity ret = boardController.putBoard("Test3", 0L);

    //     List<Board> allBoards = boardController.getAllBoards();

    //     for (Board b : allBoards) {
    //         assertNotEquals(b.getTitle(), "Test1");
    //     }

    //     assertEquals(ret, ResponseEntity.ok("Board title updated successfully"));
    // }

    // @Test
    // void putBoardNotFound() {
    //     ResponseEntity ret = boardController.putBoard("ThisBoardIdDoesNotExist", 23450969123L);

    //     List<Board> allBoards = boardController.getAllBoards();
    //     for (Board b : allBoards) {
    //         assertNotEquals("ThisBoardIdDoesNotExist", b.getTitle());
    //     }

    //     assertEquals(ResponseEntity.notFound().build(), ret);
    // }

    // @Test
    // void deleteBoardNotFound() {
    //     ResponseEntity ret = boardController.deleteBoard(3L);

    //     assertEquals(ResponseEntity.notFound().build(), ret);
    // }

    // @Test
    // void getColumnsByBoardIdTestSuccess() {
    //     List<Column> ret = boardController.getColumnsByBoardId(5L);

    //     List<Column> columns = new ArrayList<>();
    //     columns.add(new Column("Test1", 5));
    //     columns.get(0).setId(1);

    //     columns.add(new Column("Test2", 5));
    //     columns.get(1).setId(2);

    //     assertEquals(columns, ret);

    // }

    // @Test
    // void getColumnsByBoardIdTestNotFound() {
    //     List<Column> ret = boardController.getColumnsByBoardId(6969L);
    //     assertEquals(ret.size(), 0);
    // }

    // @Test
    // void deleteBoardAndItsColumnsTestSuccess() {
    //     List<Column> columnsWithBoardId = boardController.getColumnsByBoardId(5L);
    //     boardController.deleteBoard(5L);
    //     assertEquals(boardController.getColumnsByBoardId(5L).size(), 0);
    //     for (Column c : columnsWithBoardId) {
    //         assertEquals(columnController.getCardsByColumnId(c.getId()).size(), 0);
    //     }
    // }

    // @Test
    // void deleteNonExistingBoardTest() {
    //     List<Column> allColumns = columnController.getAllColumns().getBody();
    //     List<Card> allCards = cardController.getAllCards();
    //     boardController.deleteBoard(39485768L);
    //     assertEquals(allCards, cardController.getAllCards());
    //     assertEquals(allColumns, columnController.getAllColumns().getBody());
    // }
}
