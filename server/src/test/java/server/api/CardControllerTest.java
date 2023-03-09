package server.api;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commons.Card;


class CardControllerTest {

    private TestCardRepository repo;
    private CardController controller;

    @BeforeEach
    void setUp() {
        repo = new TestCardRepository();
        controller = new CardController(repo);
    }

    @Test
    void testAddCard_responseStatusIsOk() {
        ResponseEntity<Card> response = controller.addCard("Test1", 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddCard_responseBodyIsNotNull() {
        ResponseEntity<Card> response = controller.addCard("Test1", 1);
        assertNotNull(response.getBody());
    }

    @Test
    void testAddCard_responseTitleEqualsExpected() {
        ResponseEntity<Card> response = controller.addCard("Test1", 1);
        assertEquals("Test1", response.getBody().title);
    }

    @Test
    void testAddCard_responseListIdEqualsExpected() {
        ResponseEntity<Card> response = controller.addCard("Test1", 1);
        assertEquals(1, response.getBody().listId);
    }


    @Test
    void testEditCardTitle_responseStatusIsOk() {
        ResponseEntity<String> response = controller.editCardTitle(1, "editTitle");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardTitle_responseBodyEqualsExpected() {
        ResponseEntity<String> response = controller.editCardTitle(1, "editTitle");
        assertEquals("Card title updated successfully", response.getBody());
    }

    @Test
    void testEditCardTitle_cardTitleEqualsExpected() {
        ResponseEntity<String> response = controller.editCardTitle(1, "editTitle");
        Card card = repo.findById(1L).get();
        assertEquals("editTitle", card.title);
    }

    @Test
    void testEditCardTitle_cardNotFound() {
        ResponseEntity<String> response = controller.editCardTitle(123, "editTitle");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testEditCardList_responseStatusIsOk() {
        ResponseEntity<String> response = controller.editCardList(1, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardList_responseBodyEqualsExpected() {
        ResponseEntity<String> response = controller.editCardList(1, 2);
        assertEquals("Card list updated successfully", response.getBody());
    }

    @Test
    void testEditCardList_cardListIdEqualsExpected() {
        ResponseEntity<String> response = controller.editCardList(1, 2);
        Card card = repo.findById(1L).get();
        assertEquals(2, card.listId);
    }

    @Test
    void testEditCardList_cardNotFound() {
        ResponseEntity<String> response = controller.editCardList(100, 2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDeleteCard_CardDoesNotExist() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals(controller.getCard(1L).title, "Test2");
    }

    @Test
    void testDeleteCard_HTTPStatusIsOk() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCard_ResponseBodyIsCorrect() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals("Card list deleted successfully", response.getBody());
    }


    @Test
    void testDeleteCardNotFound() {
        ResponseEntity<String> response = controller.deleteCard(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testGetCard_ReturnedCardIsNotNull() {
        Card card = controller.getCard(1);
        assertNotNull(card);
    }

    @Test
    void testGetCard_ReturnedCardTitleIsCorrect() {
        Card card = controller.getCard(1);
        assertEquals("Test1", card.title);
    }

    @Test
    void testGetCard_ReturnedCardListIdIsCorrect() {
        Card card = controller.getCard(1);
        assertEquals(1, card.listId);
    }

    @Test
    void testGetCard_NotFound() {
        Card card = controller.getCard(100);
        assertNull(card);
    }

    @Test
    void testGetAllCards() {
        List<Card> cards = controller.getAllCards();
        assertEquals(2, cards.size());
    }
}