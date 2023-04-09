package commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private BoardTag boardTag;

    @BeforeEach
    public void setUp() {
        board = new Board("Test");
        boardTag = new BoardTag("Test Card", "#FFFFFF");
    }

    @Test
    public void testDefaultConstructor() {
        Board defaultCard = new Board();
        assertNotNull(defaultCard);
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

    @Test
    public void testAddBoardTag() {
        board.addBoardTag(boardTag);
        List<BoardTag> tags = board.getBoardTags();
        assertEquals(boardTag, tags.get(0));
    }

    @Test
    public void testAddBoardTagNull() {
        board.addBoardTag(null);
        List<BoardTag> tags = board.getBoardTags();
        assertTrue(tags.isEmpty());
    }

    @Test
    public void testDeleteBoardTag() {
        board.addBoardTag(boardTag);
        assertEquals(boardTag, board.deleteBoardTag(boardTag));
    }

    @Test
    public void testDeleteBoardTagNull() {
        assertNull(board.deleteBoardTag(null));
    }

    @Test
    public void testDeleteBoardTagDoenstExist() {
        BoardTag notExist = new BoardTag("Test Tag 2", "#FFFFFF");
        assertNull(board.deleteBoardTag(notExist));
    }

    @Test
    public void testGetBoardTags(){
        List<BoardTag> test = new ArrayList<BoardTag>();
        test.add(boardTag);
        board.addBoardTag(boardTag);
        assertEquals(test, board.getBoardTags());
    }

    @Test
    public void testSetBoardTags() {
        BoardTag new1 = new BoardTag("New1", "#FFFFFF");
        BoardTag new2 = new BoardTag("New2", "#FFFFFF");
        BoardTag new3 = new BoardTag("New3", "#FFFFFF");

        Set<BoardTag> test = new HashSet<BoardTag>();
        test.add(new1);
        test.add(new2);
        test.add(new3);

        board.setBoardTags(test);
        ArrayList<BoardTag> testList = new ArrayList<>(test);

        assertEquals(testList, board.getBoardTags());
    }


    @Test
    public void testGetCenterColour() {
        assertEquals("#FFFFFF", board.getCenterColour());
    }

    @Test
    public void testSetCenterColour() {
        board.setCenterColour("#F2F3F4");
        assertEquals("#F2F3F4", board.getCenterColour());
    }

    @Test
    public void testGetSideColour() {
        assertEquals("#F5DEB3", board.getSideColour());
    }

    @Test
    public void testSetSideColour() {
        board.setSideColour("#FFFFFF");
        assertEquals("#FFFFFF", board.getSideColour());
    }

    @Test
    public void testGetFontType() {
        assertEquals("Segoe UI", board.getFontType());
    }

    @Test
    public void testSetFontType() {
        board.setFontType("Arial");
        assertEquals("Arial", board.getFontType());
    }

    @Test
    public void testIsFontStyleBold() {
        assertEquals(false, board.isFontStyleBold());
    }

    @Test
    public void testSetFontStyleBold() {
        board.setFontStyleBold(true);
        assertEquals(true, board.isFontStyleBold());
    }

    @Test
    public void testIsFontStyleItalic() {
        assertEquals(false, board.isFontStyleItalic());
    }

    @Test
    public void testSetFontStyleItalic() {
        board.setFontStyleItalic(true);
        assertEquals(true, board.isFontStyleItalic());
    }

    @Test
    public void testGetFontColour() {
        assertEquals("#000000", board.getFontColour());
    }

    @Test
    public void testSetFontColour() {
        board.setFontColour("#FFFFFF");
        assertEquals("#FFFFFF", board.getFontColour());
    }

    @Test
    public void testGetDefaultCenterColour() {
        assertEquals("#FFFFFF", board.getDefaultCenterColour());
    }

    @Test
    public void testGetDefaultSideColour() {
        assertEquals("#F5DEB3", board.getDefaultSideColour());
    }
    @Test
    public void testGetDefaultFontType() {
        assertEquals("Segoe UI", board.getDefaultFontType());
    }

    @Test
    public void testGetDefaultFontColour() {
        assertEquals("#000000", board.getDefaultFontColour());
    }
}
