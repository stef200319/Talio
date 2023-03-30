package server.api;

import commons.Card;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ColumnRepository;
import server.database.SubtaskRepository;
import server.services.BoardService;
import server.services.CardService;
import server.services.ColumnService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnControllerTest {

    private ColumnController columnController;

    private ColumnService columnService;
    private BoardService boardService;
    private CardService cardService;
    private ColumnRepository columnRepository;
    private BoardRepository boardRepository;
    private SubtaskRepository subtaskRepository;
    private CardRepository cardRepository;
    private CardController cardController;


    @BeforeEach
    public void setup() {
        boardRepository = new TestBoardRepository();
        columnRepository = new TestColumnRepository();
        cardRepository = new TestCardRepository();
        subtaskRepository = new TestSubtaskRepository();

        columnService = new ColumnService(columnRepository);
        boardService = new BoardService(boardRepository);
        cardService = new CardService(cardRepository);

        cardController = new CardController(cardRepository, columnRepository,subtaskRepository);
        columnController = new ColumnController(columnService, boardService, cardService);
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


