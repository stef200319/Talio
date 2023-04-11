package server.api;

import commons.Board;
import commons.Card;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.database.*;
import server.services.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ColumnControllerTest {

    private ColumnController columnController;
    private BoardController boardController;
    private CardTagController cardTagController;

    private ColumnService columnService;
    private BoardService boardService;
    private CardService cardService;
    private CardTagService cardTagService;
    private SubtaskService subtaskService;
    private ColumnRepository columnRepository;
    private BoardRepository boardRepository;
    private SubtaskRepository subtaskRepository;
    private CardRepository cardRepository;
    private CardController cardController;
    private CardTagRepository cardTagRepository;


    @BeforeEach
    public void setup() {
        boardRepository = new TestBoardRepository();
        columnRepository = new TestColumnRepository();
        cardRepository = new TestCardRepository();
        subtaskRepository = new TestSubtaskRepository();
        cardTagRepository = new TestCardTagRepository();

        columnService = new ColumnService(columnRepository);
        boardService = new BoardService(boardRepository);
        cardService = new CardService(cardRepository, subtaskRepository);
        subtaskService = new SubtaskService(subtaskRepository);
        cardTagService = new CardTagService(cardTagRepository);


        cardController = new CardController(cardService, columnService, subtaskService);
        cardTagController = new CardTagController(cardTagService, boardService, cardService);
        columnController = new ColumnController(columnRepository, columnService, boardService, cardService);
        boardController = new BoardController(boardService, columnService, cardTagService, cardService, cardTagController);
//        columnController = new ColumnController(columnService, boardService, cardService);
    }

    @Test
    void addColumnsWithGoodId() {
        ResponseEntity<Column> ret = columnController.addColumn("TODO", 1L);
        assertEquals("TODO", ret.getBody().getTitle());
        assertEquals(1, ret.getBody().getBoardId());
    }

    @Test
    void addColumnIdNotInColumn() {
        ResponseEntity<Column> ret = columnController.addColumn("Todo", 348622698L);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void removeColumnFound() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1L);

        Column expected = new Column("Test2", 0);
        expected.setId(1);
        expected.setPosition(1);

        assertEquals(expected, ret.getBody());
    }

    @Test
    void removeColumnFound2() {
        ResponseEntity<Column> ret = columnController.deleteColumn(0L);

        Column expected = new Column("Test1", 0);
        expected.setId(0);
        expected.setPosition(0);

        assertEquals(expected, ret.getBody());
    }

    @Test
    void removeColumnNotFound() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1000);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void removeColumnAndItsCards() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1L);
        assertEquals(cardRepository.findAll(), new ArrayList<>());
    }

    @Test
    void editColumnFound() {
        ResponseEntity<Column> ret = columnController.editColumnTitle(1, "Test3");

        Column expected = new Column("Test3", 0);
        expected.setId(1);
        expected.setPosition(1);

        assertEquals(ret.getBody(), expected);
        assertEquals("Test3", columnController.getColumnByColumnId(1).getBody().getTitle());
    }

    @Test
    void editColumnNotFound() {
        ResponseEntity<Column> ret = columnController.editColumnTitle(1000, "Test3");

        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void getColumnByIDFound() {
        ResponseEntity<Column> ret = columnController.getColumnByColumnId(1);

        Column expected = new Column("Test2", 0);
        expected.setId(1);
        expected.setPosition(1);
        assertEquals(ResponseEntity.ok(expected), ret);
    }

    @Test
    void getColumnByIDNotFound() {
        ResponseEntity<Column> ret = columnController.getColumnByColumnId(1000);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getAllColumns() {
        ResponseEntity<List<Column>> ret = columnController.getAllColumns();

        Iterator<Column> it = columnController.getAllColumns().getBody().iterator();

        for (Column l : ret.getBody()) {
            assertEquals(l, it.next());
        }
    }

    @Test
    void getAllColumns_empty() {
        List<Column> cols = new ArrayList<>(columnController.getAllColumns().getBody());

        for (Column c: cols) {
            columnRepository.delete(c);
        }

        assertEquals(ResponseEntity.notFound().build(), columnController.getAllColumns());

    }

    @Test
    void testGetCardByListId_2entries() {
        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(1L);
        List<Card> expected = new ArrayList<>();
        Card c1 = new Card("Test1", 1);
        Card c2 = new Card("Test2", 1);
        c1.setId(0);
        c1.setPosition(1);
        c2.setId(1);
        c2.setPosition(2);

        expected.add(c1);
        expected.add(c2);
        assertEquals(c1.toString(), Objects.requireNonNull(ret.getBody()).get(0).toString());
        assertEquals(c2.toString(), ret.getBody().get(1).toString());
    }


    @Test
    void testGetCardByListId_noMatch() {
        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(2L);

        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void testGetCardByListId_empty() {
        cardController.deleteCard(0L);
        cardController.deleteCard(1L);

        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(1L);
        List<Card> expected = new ArrayList();

        assertEquals(expected, ret.getBody());
    }

    @Test
    void editColumnBackgroundColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnBackgroundColour(retColumn.getBody().getId(), "black");

        assertEquals(retColumn.getBody().getBgColour(), "black");
    }

    @Test
    void editColumnBorderColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnBorderColour(retColumn.getBody().getId(), "black");

        assertEquals(retColumn.getBody().getBorderColour(), "black");
    }

    @Test
    void editColumnFontTypeTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnFontType(retColumn.getBody().getId(), "Arial");

        assertEquals(retColumn.getBody().getFontType(), "Arial");
    }

    @Test
    void editColumnFontStyleBold() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnFontStyleBold(retColumn.getBody().getId(), true);

        assertTrue(retColumn.getBody().getFontStyleBold());
    }

    @Test
    void editColumnFontStyleItalic() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnFontStyleItalic(retColumn.getBody().getId(), false);

        assertFalse(retColumn.getBody().getFontStyleItalic());
    }

    @Test
    void editColumnFontColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        columnController.editColumnFontColour(retColumn.getBody().getId(), "yellow");

        assertEquals(retColumn.getBody().getFontColour(), "yellow");
    }

    @Test
    void updateColumnTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("board1");

        ResponseEntity<Column> retColumn = columnController.addColumn("column1", retBoard.getBody().getId());

        Column testColumn = columnController.updateColumn(retColumn.getBody());

        assertTrue(retColumn.getBody().equals(testColumn));
    }

//     //Todo: we have change this TestColumnRepo
// //    @Test
// //    void getByBoardIdSubset() {
// //        sut.addColumn("Test3", 2L);
// //
// //        List<Column> expected = new LinkedList<>();
// //
// //        Column newCol = new Column("Test3", 2L);
// //        newCol.setId(2);
// //        expected.add(newCol);
// //
// //        assertArrayEquals(expected.toArray(), sut.getColumnByBoardId(2L).getBody().toArray());
// //    }
}

    //Todo: we have change this TestColumnRepo
//    @Test
//    void getByBoardIdSubset() {
//        sut.addColumn("Test3", 2L);
//
//        List<Column> expected = new LinkedList<>();
//
//        Column newCol = new Column("Test3", 2L);
//        newCol.setId(2);
//        expected.add(newCol);
//
//        assertArrayEquals(expected.toArray(), sut.getColumnByBoardId(2L).getBody().toArray());
//    }


