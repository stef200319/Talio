package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;

    @BeforeEach
    public void setUp() {
        card = new Card("Test", 1L);
    }

    @Test
    public void testGetId() {
        card.setId(1L);
        assertEquals(1L, card.getId());
    }

    @Test
    public void testSetId() {
        card.setId(1L);
        assertEquals( card.getId(),1L);
    }


    @Test
    public void testGetTitle() {
        assertEquals("Test", card.getTitle());
    }

    @Test
    public void testSetTitle() {
        card.setTitle("newTitle");
        assertEquals("newTitle", card.getTitle());
    }

    @Test
    public void testGetListId() {
        assertEquals(1L, card.getColumnId());
    }

    @Test
    public void testSetListId() {
        card.setColumnId(2L);
        assertEquals(2L, card.getColumnId());
    }

    @Test
    public void testGetPosition() {
        card.setPosition(1);
        assertEquals(1, card.getPosition());
    }

    @Test
    public void testSetPosition() {
        card.setPosition(2);
        assertEquals(card.getPosition(),2);
    }

//    @Test
//    public void testEquals() {
//        Card otherCard = new Card("Test", 1L);
//        assertEquals(card, otherCard);
//    }

    @Test
    public void testNotEquals() {
        Card otherCard = new Card("Test2", 1L);
        assertNotEquals(card, otherCard);
    }

    @Test
    public void testEqualsNull() {
        assertNotNull(card);
    }

//    @Test
//    public void testHashCode() {
//        Card otherCard = new Card("Test", 1L);
//        assertEquals(card.hashCode(), otherCard.hashCode());
//    }

    @Test
    public void testToString() {
        String expectedToString = "The title of this Card is: Test, and the ID of the Column this Card belongs to is: 1";
        assertEquals(expectedToString, card.toString());
    }

    @Test
    public void testGetSubtasks() {
        assertEquals(new ArrayList<Subtask>(), card.getSubtasks());
    }

    @Test
    public void testSetSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        Subtask s = new Subtask("s1");
        subtasks.add(s);
        card.setSubtasks(subtasks);
        assertEquals(subtasks, card.getSubtasks());
    }

    @Test
    public void testGetDescription() {
        assertEquals(null, card.getDescription());
    }

    @Test
    public void testSetDescription() {
        card.setDescription("d1");
        assertEquals("d1", card.getDescription());
    }
}
