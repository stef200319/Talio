package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    private TestBoardRepository repo;
    private BoardController sut;

    @BeforeEach
    void setUp() {
        repo = new TestBoardRepository();
        sut = new BoardController(repo);
    }

    @Test
    void getAllBoards() {
        List<Board> boards = new ArrayList<>();
        boards.add(new Board("Test1"));
        boards.add(new Board("Test2"));

        List<Board> ret = sut.getAllBoards();

        assertEquals(boards, ret);
    }

    @Test
    void getBoardById() {
        Board board = new Board("Test1");
        Board ret = sut.getBoardById(1L);

        assertEquals(board, ret);
    }

    @Test
    void addBoardSuccessful() {
        ResponseEntity ret = sut.addBoard("Test3");

        Board b = (Board) ret.getBody();
        assertEquals("Test3", b.getTitle());
    }

    @Test
    void addBoardEmptyString() {
        ResponseEntity ret = sut.addBoard("");
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void addBoardNullString() {
        ResponseEntity ret = sut.addBoard(null);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void putBoardSuccessfull() {
        ResponseEntity ret = sut.putBoard("Test3", 1L);

        List<Board> allBoards = sut.getAllBoards();

        for (Board b : allBoards) {
            assertNotEquals(b.getTitle(), "Test1");
        }

        assertEquals(ret, ResponseEntity.ok("Board title updated successfully"));
    }

    @Test
    void putBoardNotFound() {
        ResponseEntity ret = sut.putBoard("Test3", 3L);

        List<Board> allBoards = sut.getAllBoards();
        for (Board b : allBoards) {
            assertNotEquals("Test3", b.getTitle());
        }

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void deleteBoardSuccessful() {
        ResponseEntity ret = sut.putBoard("Test3", 3L);

        List<Board> allBoards = sut.getAllBoards();
        for (Board b : allBoards) {
            assertNotEquals("Test3", b.getTitle());
        }

        assertEquals(ResponseEntity.notFound().build(), ret);
    }

    @Test
    void deleteBoardNotFound() {
        ResponseEntity ret = sut.deleteBoard(3L);

        assertEquals(ResponseEntity.notFound().build(), ret);
    }
}