package commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board("Test");
    }

    @Test
    public void testGetId() {
        board.setId(1L);
        assertEquals(board.getId(), 1L);
    }

    @Test
    public void testSetId() {
        board.setId(1L);
        assertEquals(1L, board.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test", board.getTitle());
    }

    @Test
    public void testSetTitle() {
        board.setTitle("newTitle");
        assertEquals("newTitle", board.getTitle());
    }

    @Test
    public void testEquals() {
        Board test2 = new Board("Test");
        assertEquals(board, test2);
    }

    @Test
    public void testNotEquals() {
        Board test2 = new Board("Test2");
        assertNotEquals(board, test2);
    }

    @Test
    public void testEqualsNull() {
        assertNotNull(board);
    }

    @Test
    public void testHashCode() {
        Board otherBoard = new Board("Test");
        assertEquals(board.hashCode(), otherBoard.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "This board is called: Test";
        assertEquals(expectedToString, board.toString());
    }
}
