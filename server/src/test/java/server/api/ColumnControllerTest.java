package server.api;

import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.BoardRepository;
import server.database.ColumnRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnControllerTest {

    private ColumnController sut;
    private ColumnRepository repo;
    private BoardRepository boardRepository;

    @BeforeEach
    public void setup() {
        boardRepository = new TestBoardRepository();
        repo = new TestColumnRepository();
        sut = new ColumnController(repo, boardRepository);
    }

    @Test
    void addColumnsWithGoodId() {
        ResponseEntity<Column> ret = sut.addColumn("TODO", 2L);
        assertEquals("TODO", ret.getBody().getTitle());
        assertEquals(2, ret.getBody().getBoardId());
    }

    @Test
    void addColumnIdNotInColumn() {
        ResponseEntity<Column> ret = sut.addColumn("Todo", 5L);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void removeColumnFound() {
        ResponseEntity<String> ret = sut.removeColumn(1L);
        assertEquals(ResponseEntity.ok("Column deleted successfully"), ret);
    }

    @Test
    void removeColumnNotFound() {
        ResponseEntity<String> ret = sut.removeColumn(1000L);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void editColumnFound() {
        ResponseEntity<String> ret = sut.editColumn(2, "Test3");

        assertEquals(ret, ResponseEntity.ok("Card edited successfully"));
        assertEquals("Test3", sut.getColumnByID(2).getBody().getTitle());
    }

    @Test
    void editColumnNotFound() {
        ResponseEntity<String> ret = sut.editColumn(1000, "Test3");

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getColumnByIDFound() {
        ResponseEntity<Column> ret = sut.getColumnByID(1);

        Column expected = new Column("Test1", 5);
        expected.setId(1);
        assertEquals(ResponseEntity.ok(expected), ret);
    }

    @Test
    void getColumnByIDNotFound() {
        ResponseEntity<Column> ret = sut.getColumnByID(1000);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getAllColumns() {
        ResponseEntity<List<Column>> ret = sut.getAllColumns();

        Iterator<Column> it = sut.getAllColumns().getBody().iterator();

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
    void getByBoardIdAll() {
        List<Column> ret = sut.getColumnByBoardId(5).getBody();

        assertEquals(sut.getAllColumns().getBody(), ret);
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
}