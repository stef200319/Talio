package server.api;

import commons.Board;
import commons.BoardTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.BoardRepository;
import server.database.BoardTagRepository;

import static junit.framework.TestCase.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTagControllerTest {

    BoardTagController boardTagController;
    BoardTagRepository boardTagRepository;
    BoardRepository boardRepository;

    Board board1;
    BoardTag boardTag1;



    @BeforeEach
    void setUp() {
        boardTagRepository = new TestBoardTagRepository();
        boardRepository = new TestBoardRepository();
        boardTagController = new BoardTagController(boardTagRepository, boardRepository);

        board1 = new Board("board1");
        board1.setId(69L);
        boardRepository.save(board1);

        boardTag1 = new BoardTag("boardTag1", "aaffaa");
        boardTagRepository.save(boardTag1);
    }

    @Test
    void addBoardTagFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.addBoardTag(null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addBoardTagSuccessTest() {
        ResponseEntity<BoardTag> response = boardTagController.addBoardTag("test", "ff11bb");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(boardTagRepository.findAll().contains(response.getBody()));
    }

    @Test
    void deleteBoardTagFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.deleteBoardTag(230948209345L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteBoardTagSuccessTest() {
        ResponseEntity<BoardTag> responseAdd = boardTagController.addBoardTag("test", "ff11bb");
        ResponseEntity<BoardTag> responseDelete = boardTagController.deleteBoardTag(responseAdd.getBody().getId());
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertEquals(responseAdd.getBody(), responseDelete.getBody());
        assertFalse(boardTagRepository.findAll().contains(responseDelete.getBody()));
    }

    @Test
    void addBoardTagToBoardFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.addBoardTagToBoard(2348724L, 2304720L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addBoardTagToBoardSuccessTest() {
        ResponseEntity<BoardTag> response = boardTagController.addBoardTagToBoard(boardTag1.getId(), board1.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(board1.getBoardTags().contains(boardTag1));
    }

    @Test
    void deleteBoardTagFromBoardFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.deleteBoardTagFromBoard(89534753984L,
                290384705L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteBoardTagFromBoardSuccessTest() {
        ResponseEntity<BoardTag> responseAdd = boardTagController.addBoardTagToBoard(boardTag1.getId(), board1.getId());
        ResponseEntity<BoardTag> responseDelete = boardTagController.deleteBoardTagFromBoard(boardTag1.getId(),
                board1.getId());
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertEquals(responseAdd.getBody(), responseDelete.getBody());
        assertFalse(board1.getBoardTags().contains(boardTag1));
    }

    @Test
    void editBoardTagColorFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.editBoardTagColor(2359487592L, "ffaaee");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editBoardTagColorSuccessTest() {
        ResponseEntity<BoardTag> response = boardTagController.editBoardTagColor(boardTag1.getId(), "ffaaee");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ffaaee", response.getBody().getColor());
    }

    @Test
    void editBoardTagTitleFailTest() {
        ResponseEntity<BoardTag> response = boardTagController.editBoardTagTitle(2359487592L, "newTitle");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editBoardTagTitleSuccessTest() {
        ResponseEntity<BoardTag> response = boardTagController.editBoardTagTitle(boardTag1.getId(), "newTitle");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("newTitle", response.getBody().getTitle());
    }
}