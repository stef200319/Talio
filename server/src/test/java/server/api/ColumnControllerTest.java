package server.api;

import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.ColumnRepository;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnControllerTest {

    private ListController sut;
    private ColumnRepository repo;

    @BeforeEach
    public void setup() {
        repo = new TestColumnRepository();
        sut = new ListController(repo);
    }

    @Test
    void addList() {
        ResponseEntity<Column> ret = sut.addList("TODO", 53);
        assertEquals("TODO", ret.getBody().getTitle());
        assertEquals(53, ret.getBody().getBoardId());
    }

    @Test
    void removeListFound() {
        ResponseEntity<String> ret = sut.removeList(1);
        assertEquals(ResponseEntity.ok("List deleted successfully"), ret);
    }

    @Test
    void removeListNotFound() {
        ResponseEntity<String> ret = sut.removeList(1000);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void editListFound() {
        ResponseEntity<String> ret = sut.editList(2, "Test3");

        assertEquals(ret, ResponseEntity.ok("Card edited successfully"));
        assertEquals("Test3", sut.getListByID(2).getBody().getTitle());
    }

    @Test
    void editListNotFound() {
        ResponseEntity<String> ret = sut.editList(1000, "Test3");

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getListByIDFound() {
        ResponseEntity<Column> ret = sut.getListByID(1);

        Column expected = new Column("Test1", 5);
        expected.setId(1);
        assertEquals(ResponseEntity.ok(expected), ret);
    }

    @Test
    void getListByIDNotFound() {
        ResponseEntity<Column> ret = sut.getListByID(1000);
        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void getAllLists() {
        ResponseEntity<List<Column>> ret = sut.getAllLists();

        Iterator<Column> it = sut.getAllLists().getBody().iterator();

        for (Column l : ret.getBody()) {
            assertEquals(l, it.next());
        }
    }

    @Test
    void getAllListsEmpty() {
        sut.removeList(1);
        sut.removeList(2);

        ResponseEntity<List<Column>> ret = sut.getAllLists();

        assertEquals(ResponseEntity.notFound().build(), ret);
    }
}