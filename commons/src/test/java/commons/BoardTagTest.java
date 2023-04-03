package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTagTest {

    private BoardTag boardTag1;
    private BoardTag boardTag2;

    @BeforeEach
    void setUp() {
        boardTag1 = new BoardTag("boardTag1", "ff11aa");
        boardTag2 = new BoardTag("boardTag2", "bb23af");
        boardTag1.setId(1L);
        boardTag2.setId(2L);
    }

    @Test
    void constructorTest() {
        BoardTag bt = new BoardTag("board", "aaff23");
        assertNotNull(bt);
    }

    @Test
    void getIdTest() {
        assertEquals(1L, boardTag1.getId());
        assertEquals(2L, boardTag2.getId());
        assertNotEquals(12345233L, boardTag1.getId());
    }

    @Test
    void getTitleTest() {
        assertEquals("boardTag1", boardTag1.getTitle());
        assertEquals("boardTag2", boardTag2.getTitle());
        assertNotEquals("ajbdskpwei", boardTag2.getTitle());
    }

    @Test
    void getColorTest() {
        assertEquals("ff11aa", boardTag1.getColor());
        assertEquals("bb23af", boardTag2.getColor());
        assertNotEquals("ajbdskasdfpwei", boardTag2.getColor());
    }

    @Test
    void setIdTest() {
        boardTag1.setId(3L);
        boardTag2.setId(4L);
        assertEquals(3L, boardTag1.getId());
        assertEquals(4L, boardTag2.getId());
        assertNotEquals(23948573295L, boardTag2.getId());
    }

    @Test
    void setColorTest() {
        boardTag1.setColor("ffaaff");
        boardTag2.setColor("11aacc");
        assertEquals("ffaaff", boardTag1.getColor());
        assertEquals("11aacc", boardTag2.getColor());
        assertNotEquals("kajsfiah", boardTag2.getColor());
    }

    @Test
    void setTitleTest() {
        boardTag1.setTitle("test1");
        boardTag2.setTitle("test2");
        assertEquals("test1", boardTag1.getTitle());
        assertEquals("test2", boardTag2.getTitle());
        assertNotEquals("titleee", boardTag2.getTitle());
    }

    @Test
    void equalsTest() {
        BoardTag boardTag3 = new BoardTag("boardTag1", "ff11aa");
        boardTag3.setId(1L);
        assertEquals(boardTag1, boardTag3);
        assertNotEquals(boardTag1, boardTag2);
    }

    @Test
    void hashCodeTest() {
        assertEquals(boardTag1.hashCode(), boardTag1.hashCode());
    }

    @Test
    void toStringTest() {
        String out = "Tag{id=1, title='boardTag1', color='ff11aa'}";
        assertEquals(boardTag1.toString(), out);
        assertNotEquals(boardTag2.toString(), out);
    }


}