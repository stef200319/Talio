package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;
    private CardTag cardTag;
    private Board board;

    @BeforeEach
    public void setUp() {
        card = new Card("Test", 1L);
        board = new Board("Test Board");
        cardTag = new CardTag("Test Card", "#FFFFFF", board);
    }

    @Test
    public void testDefaultConstructor() {
        Card defaultCard = new Card();
        assertNotNull(defaultCard);
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

    @Test
    public void testEquals() {
        Card otherCard = new Card("Test", 1L);
        assertEquals(card, otherCard);
    }

    @Test
    public void testNotEquals() {
        Card otherCard = new Card("Test2", 1L);
        assertNotEquals(card, otherCard);
    }

    @Test
    public void testEqualsNull() {
        assertNotNull(card);
    }

    @Test
    public void testHashCode() {
        Card otherCard = new Card("Test", 1L);
        assertEquals(card.hashCode(), otherCard.hashCode());
    }

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


    @Test
    public void testAddCardTag() {
        card.addCardTag(cardTag);
        List<CardTag> tags = card.getCardTags();
        assertEquals(cardTag, tags.get(0));
    }

    @Test
    public void testAddCardTagNull() {
        card.addCardTag(null);
        List<CardTag> tags = card.getCardTags();
        assertTrue(tags.isEmpty());
    }

    @Test
    public void testDeleteCardTag() {
        card.addCardTag(cardTag);
        assertEquals(cardTag, card.deleteCardTag(cardTag));
    }

    @Test
    public void testDeleteCardTagNull() {
        assertNull(card.deleteCardTag(null));
    }
    @Test
    public void testDeleteCardTagDoenstExist() {
        CardTag notExist = new CardTag("Test Card 2", "#FFFFFF", board);
        assertNull(card.deleteCardTag(notExist));
    }

    @Test
    public void testGetCardTags(){
        List<CardTag> test = new ArrayList<CardTag>();
        test.add(cardTag);
        card.addCardTag(cardTag);
        assertEquals(test, card.getCardTags());
    }

    @Test
    public void testSetCardTags() {
        CardTag new1 = new CardTag("New1", "#FFFFFF", board);
        CardTag new2 = new CardTag("New2", "#FFFFFF", board);
        CardTag new3 = new CardTag("New3", "#FFFFFF", board);

        Set<CardTag> test = new HashSet<CardTag>();
        test.add(new1);
        test.add(new2);
        test.add(new3);

        card.setCardTags(test);
        ArrayList<CardTag> testList = new ArrayList<>(test);

        assertEquals(testList, card.getCardTags());
    }

    @Test
    public void testGetBgColour() {
        assertEquals("#F2F3F4", card.getBgColour());
    }

    @Test
    public void testSetBgColour() {
        card.setBgColour("#FFFFFF");
        assertEquals("#FFFFFF", card.getBgColour());
    }

    @Test
    public void testGetBorderColour() {
        assertEquals("#000000", card.getBorderColour());
    }

    @Test
    public void testSetBorderColour() {
        card.setBorderColour("#FFFFFF");
        assertEquals("#FFFFFF", card.getBorderColour());
    }

    @Test
    public void testGetFontType() {
        assertEquals("Segoe UI", card.getFontType());
    }

    @Test
    public void testSetFontType() {
        card.setFontType("Arial");
        assertEquals("Arial", card.getFontType());
    }

    @Test
    public void testIsFontStyleBold() {
        assertEquals(false, card.isFontStyleBold());
    }

    @Test
    public void testSetFontStyleBold() {
        card.setFontStyleBold(true);
        assertEquals(true, card.isFontStyleBold());
    }

    @Test
    public void testIsFontStyleItalic() {
        assertEquals(false, card.isFontStyleItalic());
    }

    @Test
    public void testSetFontStyleItalic() {
        card.setFontStyleItalic(true);
        assertEquals(true, card.isFontStyleItalic());
    }

    @Test
    public void testGetFontColour() {
        assertEquals("#000000", card.getFontColour());
    }

    @Test
    public void testSetFontColour() {
        card.setFontColour("#FFFFFF");
        assertEquals("#FFFFFF", card.getFontColour());
    }

    @Test
    public void testGetDefaultBgColour() {
        assertEquals("#F2F3F4", card.getDefaultBgColour());
    }

    @Test
    public void testGetDefaultBorderColour() {
        assertEquals("#000000", card.getDefaultBorderColour());
    }
    @Test
    public void testGetDefaultFontType() {
        assertEquals("Segoe UI", card.getDefaultFontType());
    }

    @Test
    public void testGetDefaultFontColour() {
        assertEquals("#000000", card.getDefaultFontColour());
    }

}
