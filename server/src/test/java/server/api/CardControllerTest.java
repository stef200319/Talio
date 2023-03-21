package server.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commons.Card;


class CardControllerTest {

    private TestCardRepository cardRepository;
    private TestColumnRepository columnRepository;
    private CardController cardController;

    @BeforeEach
    void setUp() {
        this.columnRepository = new TestColumnRepository();
        this.cardRepository = new TestCardRepository();
        cardController = new CardController(cardRepository, columnRepository);
    }

    @Test
    void testAddCard_responseStatusIsOk() {

        ResponseEntity<Card> response = cardController.addCard("Test1", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddCard_responseBodyIsNotNull() {
        ResponseEntity<Card> response = cardController.addCard("Test1", 1L);
        assertNotNull(response.getBody());
    }

    @Test
    void testAddCard_responseTitleEqualsExpected() {
        ResponseEntity<Card> response = cardController.addCard("Test1", 1L);
        assertEquals("Test1", response.getBody().getTitle());
    }

    @Test
    void testAddCard_responseListIdEqualsExpected() {
        ResponseEntity<Card> response = cardController.addCard("Test1", 1L);
        assertEquals(1, response.getBody().getColumnId());
    }

    @Test
    void testEditCardTitle_responseStatusIsOk() {
        ResponseEntity<String> response = cardController.editCardTitle(1, "editTitle");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardTitle_responseBodyEqualsExpected() {
        ResponseEntity<String> response = cardController.editCardTitle(1, "editTitle");
        assertEquals("Card title updated successfully", response.getBody());
    }

    @Test
    void testEditCardTitle_cardTitleEqualsExpected() {
        ResponseEntity<String> response = cardController.editCardTitle(1, "editTitle");
        Card card = cardRepository.findById(1L).get();
        assertEquals("editTitle", card.getTitle());
    }

    @Test
    void testEditCardTitle_cardNotFound() {
        ResponseEntity<String> response = cardController.editCardTitle(123, "editTitle");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testEditCardList_responseStatusIsOk() {
        ResponseEntity<String> response = cardController.editCardColumn(1, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardList_responseBodyEqualsExpected() {
        ResponseEntity<String> response = cardController.editCardColumn(1, 2);
        assertEquals("Card column updated successfully", response.getBody());
    }

    @Test
    void testEditCardList_cardListIdEqualsExpected() {
        List<Card> cards = cardRepository.findAll();
        ResponseEntity<String> response = cardController.editCardColumn(1, 2);
        List<Card> cardss = cardRepository.findAll();
        Card card = cardRepository.findById(1L).get();
        assertEquals(2, card.getColumnId());
    }

    @Test
    void testEditCardList_cardNotFound() {
        ResponseEntity<String> response = cardController.editCardColumn(100, 2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDeleteCard_CardDoesNotExist() {
        ResponseEntity<String> response = cardController.deleteCard(0L);
        assertEquals(cardController.getCardByCardId(1L).getTitle(), "Test2");
    }

    @Test
    void testDeleteCard_HTTPStatusIsOk() {
        ResponseEntity<String> response = cardController.deleteCard(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCard_ResponseBodyIsCorrect() {
        ResponseEntity<String> response = cardController.deleteCard(1L);
        assertEquals("Card deleted successfully", response.getBody());
    }


    @Test
    void testDeleteCardNotFound() {
        ResponseEntity<String> response = cardController.deleteCard(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testGetCardByCardId_ReturnedCardIsNotNull() {
        Card card = cardController.getCardByCardId(1);
        assertNotNull(card);
    }

    @Test
    void testGetCardByCardId_ReturnedCardTitleIsCorrect() {
        Card card = cardController.getCardByCardId(0);
        assertEquals("Test1", card.getTitle());
    }

    @Test
    void testGetCardByCardId_ReturnedCardListIdIsCorrect() {
        Card card = cardController.getCardByCardId(1);
        assertEquals(1, card.getColumnId());
    }

    @Test
    void testGetCardByCardId_NotFound() {
        Card card = cardController.getCardByCardId(100);
        assertNull(card);
    }

    @Test
    void testGetAllCards() {
        List<Card> cards = cardController.getAllCards();
        assertEquals(2, cards.size());
    }

}