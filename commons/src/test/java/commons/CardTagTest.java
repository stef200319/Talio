package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTagTest {

    Board board1;
    Board board2;
    CardTag cardTag1;
    CardTag cardTag2;
    CardTag cardTag3;

    @BeforeEach
    void setUp() {
        board1 = new Board("board1");
        board2 = new Board("board2");
        board1.setId(1L);
        board2.setId(2L);
        cardTag1 = new CardTag("cardTag1", "ffaaff", board1);
        cardTag2 = new CardTag("cardTag2", "ffaa11", board1);
        cardTag3 = new CardTag("cardTag3", "aabbcc", board2);
    }

    // I wil not be testing all the getters and setters from the Tag class, because they are already tested in the
    // BoardTagTest class.

    @Test
    void getBoardTest() {
        assertEquals(cardTag1.getBoard(), cardTag2.getBoard());
        assertNotEquals(cardTag1.getBoard(), cardTag3.getBoard());
    }

    @Test
    void equalsTest() {
        assertEquals(cardTag1, cardTag1);
        assertNotEquals(cardTag1, cardTag2);
        CardTag cardTag4 = new CardTag("cardTag1", "ffaaff", board1);
        assertEquals(cardTag4, cardTag1);
    }

    @Test
    void hashCodeTest() {
        assertEquals(cardTag1.hashCode(), cardTag1.hashCode());
        assertEquals(cardTag2.hashCode(), cardTag2.hashCode());
        assertEquals(cardTag3.hashCode(), cardTag3.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals(cardTag1.toString(), "CardTag{board=This board is called: board1}");
    }



}