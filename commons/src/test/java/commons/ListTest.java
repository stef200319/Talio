package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListTest {

    private List list;

    @BeforeEach
    public void setUp() {
        list = new List("Test", 1L);
    }

    @Test
    public void testGetId() {
        list.setId(1L);
        assertEquals(1L, list.getId());
    }

    @Test
    public void testSetId() {
        list.setId(1L);
        assertEquals(list.getId(), 1L);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test", list.getTitle());
    }

    @Test
    public void testSetTitle() {
        list.setTitle("newTitle");
        assertEquals("newTitle", list.getTitle());
    }

    @Test
    public void testGetBoardId() {
        assertEquals(1L, list.getBoardId());
    }

    @Test
    public void testSetBoardId() {
        list.setId(2L);
        assertEquals(1L, list.getBoardId());
    }

    @Test
    public void testEquals() {
        List otherList = new List("Test", 1L);
        assertEquals(list, otherList);
    }

    @Test
    public void testNotEquals() {
        List otherList = new List("Test2", 1L);
        assertNotEquals(list, otherList);
    }

    @Test
    public void testEqualsNull() {
        assertNotnull(list);
    }

    @Test
    public void testHashCode() {
        List otherList = new List("Test", 1L);
        assertEquals(list.hashCode(), otherList.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "The title of this List is: Test, and the ID of the Board this List belongs to is: 1";
        assertEquals(expectedToString, list.toString());
    }
}
