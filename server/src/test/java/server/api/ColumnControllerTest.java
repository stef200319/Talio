package server.api;

import commons.Card;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ColumnRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnControllerTest {

    private ColumnController columnController;
    private ColumnRepository columnRepository;
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private CardController cardController;


    @BeforeEach
    public void setup() {
        boardRepository = new TestBoardRepository();
        columnRepository = new TestColumnRepository();
        cardRepository = new TestCardRepository();
        cardController = new CardController(cardRepository, columnRepository);
        columnController = new ColumnController(columnRepository, boardRepository, cardRepository, cardController);
    }


//     @Test
//     void addColumnsWithGoodId() {
//         ResponseEntity<Column> ret = columnController.addColumn("TODO", 1L);
//         assertEquals("TODO", ret.getBody().getTitle());
//         assertEquals(1, ret.getBody().getBoardId());
//     }

// //    @Test
// //    void addColumnIdNotInColumn() {
// //        ResponseEntity<Column> ret = sut.addColumn("Todo", 348622698L);
// //        assertEquals(ResponseEntity.notFound().build(), ret);
// //    }

//     @Test
//     void removeColumnFound() {
//         ResponseEntity<String> ret = columnController.removeColumn(1L);
//         assertEquals(ResponseEntity.ok("Column deleted successfully"), ret);
//     }

//     @Test
//     void removeColumnNotFound() {
//         ResponseEntity<String> ret = columnController.removeColumn(1000);
//         assertEquals(ResponseEntity.notFound().build(), ret);
//     }

//     @Test
//     void removeColumnAndItsCards() {
//         ResponseEntity<String> ret = columnController.removeColumn(1L);
//         assertEquals(cardRepository.findAll(), new ArrayList<>());
//     }

//     @Test
//     void editColumnFound() {
//         ResponseEntity<String> ret = columnController.editColumn(2, "Test3");

//         assertEquals(ret, ResponseEntity.ok("Card edited successfully"));
//         assertEquals("Test3", columnController.getColumnByID(2).getBody().getTitle());
//     }

//     @Test
//     void editColumnNotFound() {
//         ResponseEntity<String> ret = columnController.editColumn(1000, "Test3");

//         assertEquals(ResponseEntity.notFound().build(), ret);
//     }

//     @Test
//     void getColumnByIDFound() {
//         ResponseEntity<Column> ret = columnController.getColumnByID(1);

//         Column expected = new Column("Test1", 5);
//         expected.setId(1);
//         assertEquals(ResponseEntity.ok(expected), ret);
//     }

//     @Test
//     void getColumnByIDNotFound() {
//         ResponseEntity<Column> ret = columnController.getColumnByID(1000);
//         assertEquals(ResponseEntity.notFound().build(), ret);
//     }

//     @Test
//     void getAllColumns() {
//         ResponseEntity<List<Column>> ret = columnController.getAllColumns();

//         Iterator<Column> it = columnController.getAllColumns().getBody().iterator();

//         for (Column l : ret.getBody()) {
//             assertEquals(l, it.next());
//         }
//     }

// //Todo: we have change this TestColumnRepo
// //    @Test
// //    void getAllColumnsEmpty() {
// //        sut.removeColumn(1);
// //        sut.removeColumn(2);
// //
// //        ResponseEntity<List<Column>> ret = sut.getAllColumns();
// //
// //        assertEquals(ResponseEntity.notFound().build(), ret);
// //    }


//     @Test
//     void testGetCardByListId_2entries() {
//         List<Card> ret = columnController.getCardsByColumnId(1L);
//         System.out.println(ret);
//         List<Card> expected = new ArrayList<>();
//         Card c1 = new Card("Test1", 1);
//         Card c2 = new Card("Test2", 1);
//         c1.setId(0);
//         c2.setId(1);
// //        Id's are auto set to 0 for some reason;
//         expected.add(c1);
//         expected.add(c2);
//         assertEquals(expected, ret);
//     }


//     @Test
//     void testGetCardByListId_noMatch() {
//         List<Card> ret = columnController.getCardsByColumnId(2L);

//         List<Card> expected = new ArrayList<>();

//         assertEquals(expected, ret);
//     }

//     @Test
//     void testGetCardByListId_empty() {
//         cardController.deleteCard(0L);
//         cardController.deleteCard(1L);

//         List<Card> ret = columnController.getCardsByColumnId(1L);
//         List<Card> expected = new ArrayList<>();

//         assertEquals(expected, ret);
//     }

    @Test
    void addColumnsWithGoodId() {
        ResponseEntity<Column> ret = columnController.addColumn("TODO", 1L);
        assertEquals("TODO", ret.getBody().getTitle());
        assertEquals(1, ret.getBody().getBoardId());
    }

//    @Test
//    void addColumnIdNotInColumn() {
//        ResponseEntity<Column> ret = sut.addColumn("Todo", 348622698L);
//        assertEquals(ResponseEntity.notFound().build(), ret);
//    }

    @Test
    void removeColumnFound() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1L);
        assertEquals(ResponseEntity.ok("Column deleted successfully"), ret);
    }

    @Test
    void removeColumnNotFound() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1000);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void removeColumnAndItsCards() {
        ResponseEntity<Column> ret = columnController.deleteColumn(1L);
        assertEquals(cardRepository.findAll(), new ArrayList<>());
    }

    @Test
    void editColumnFound() {
        ResponseEntity<Column> ret = columnController.editColumnTitle(2, "Test3");

        assertEquals(ret, ResponseEntity.ok("Card edited successfully"));
        assertEquals("Test3", columnController.getColumnByColumnId(2).getBody().getTitle());
    }

    @Test
    void editColumnNotFound() {
        ResponseEntity<Column> ret = columnController.editColumnTitle(1000, "Test3");

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getColumnByIDFound() {
        ResponseEntity<Column> ret = columnController.getColumnByColumnId(1);

        Column expected = new Column("Test1", 5);
        expected.setId(1);
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

//Todo: we have change this TestColumnRepo
//    @Test
//    void getAllColumnsEmpty() {
//        sut.removeColumn(1);
//        sut.removeColumn(2);
//
//        ResponseEntity<List<Column>> ret = sut.getAllColumns();
//
//        assertEquals(ResponseEntity.notFound().build(), ret);
//    }


    @Test
    void testGetCardByListId_2entries() {
        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(1L);
        System.out.println(ret);
        List<Card> expected = new ArrayList<>();
        Card c1 = new Card("Test1", 1);
        Card c2 = new Card("Test2", 1);
        c1.setId(0);
        c2.setId(1);
//        Id's are auto set to 0 for some reason;
        expected.add(c1);
        expected.add(c2);
        assertEquals(expected, ret);
    }


    @Test
    void testGetCardByListId_noMatch() {
        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(2L);

        List<Card> expected = new ArrayList<>();

        assertEquals(expected, ret);
    }

    @Test
    void testGetCardByListId_empty() {
        cardController.deleteCard(0L);
        cardController.deleteCard(1L);

        ResponseEntity<List<Card>> ret = columnController.getCardsByColumnId(1L);
        List<Card> expected = new ArrayList<>();

        assertEquals(expected, ret);
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


