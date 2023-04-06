package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class SubtaskTest {
    private Subtask subtask;

    @BeforeEach
    public void setUp() {
        subtask = new Subtask("test");
    }

    @Test
    public void testGetId() {
        assertEquals(0, subtask.getId());
    }

    @Test
    public void testSetId() {
        subtask.setId(1);
        assertEquals(1, subtask.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("test", subtask.getTitle());
    }

    @Test
    public void testSetTitle() {
        subtask.setTitle("new");
        assertEquals("new", subtask.getTitle());
    }

    @Test
    public void testGetDone() {
        assertEquals(false, subtask.getDone());
    }

    @Test
    public void testSetDone() {
        subtask.setDone(true);
        assertEquals(true, subtask.getDone());
    }

    @Test
    public void testEqualsSame() {
        assertTrue(subtask.equals(subtask));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(subtask.equals(null));
    }

    @Test
    public void testEqualTrue() {
        Subtask same = new Subtask();
        same.setTitle("test");
        same.setId(0);
        assertTrue(subtask.equals(same));
    }

    @Test
    public void testHash() {
        assertEquals(Objects.hash(subtask.getId(), subtask.getTitle(), subtask.getDone()), subtask.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Subtask with id: 0 has title: test and status: false", subtask.toString());
    }
}
