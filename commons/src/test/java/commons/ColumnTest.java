package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnTest {

    private Column column;

    @BeforeEach
    public void setUp() {
        column = new Column("Test", 1L);
    }

    @Test
    public void testGetId() {
        column.setId(1L);
        assertEquals(1L, column.getId());
    }

    @Test
    public void testSetId() {
        column.setId(1L);
        assertEquals(column.getId(), 1L);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test", column.getTitle());
    }

    @Test
    public void testSetTitle() {
        column.setTitle("newTitle");
        assertEquals("newTitle", column.getTitle());
    }

    @Test
    public void testGetBoardId() {
        assertEquals(1L, column.getBoardId());
    }

    @Test
    public void testSetBoardId() {
        column.setBoardId(2L);
        assertEquals(2L, column.getBoardId());
    }

    @Test
    public void testGetPosition() {
        column.setPosition(1);
        assertEquals(1, column.getPosition());
    }

    @Test
    public void testSetPosition() {
        column.setPosition(2);
        assertEquals(column.getPosition(),2);
    }

    @Test
    public void testEquals() {
        Column otherColumn = new Column("Test", 1L);
        assertEquals(column, otherColumn);
    }

    @Test
    public void testNotEquals() {
        Column otherColumn = new Column("Test2", 1L);
        assertNotEquals(column, otherColumn);
    }

    @Test
    public void testEqualsNull() {
        assertNotNull(column);
    }

    @Test
    public void testHashCode() {
        Column otherColumn = new Column("Test", 1L);
        assertEquals(column.hashCode(), otherColumn.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "The title of this List is: Test, and the ID of the Board this List belongs to is: 1";
        assertEquals(expectedToString, column.toString());
    }
}
