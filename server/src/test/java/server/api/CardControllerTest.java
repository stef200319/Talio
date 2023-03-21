package server.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        ResponseEntity<Card> response = cardController.editCardTitle(1, "editTitle");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardTitle_responseBodyEqualsExpected() {
        ResponseEntity<Card> response = cardController.editCardTitle(1, "editTitle");
        assertEquals(new Card("editTitle", 1).toString(),
                Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    void testEditCardTitle_cardTitleEqualsExpected() {
        cardController.editCardTitle(1, "editTitle");
        Optional<Card> card = cardRepository.findById(1L);
        assertEquals("editTitle", card.get().getTitle());
    }

    @Test
    void testEditCardTitle_cardNotFound() {
        ResponseEntity<Card> response = cardController.editCardTitle(123, "editTitle");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testEditCardList_responseStatusIsOk() {
        ResponseEntity<Card> response = cardController.editCardColumn(1, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardList_responseBodyEqualsExpected() {
        ResponseEntity<Card> response = cardController.editCardColumn(1, 2);
        assertEquals("Card column updated successfully", response.getBody());
    }

    @Test
    void testEditCardList_cardListIdEqualsExpected() {
        cardController.editCardColumn(1, 1);
        Card card = cardRepository.findById(1L).get();
        assertEquals(2, card.getColumnId());
    }

    @Test
    void testEditCardList_cardNotFound() {
        ResponseEntity<Card> response = cardController.editCardColumn(100, 2);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }


    @Test
    void testDeleteCard_CardDoesNotExist() {
        ResponseEntity<Card> response = cardController.deleteCard(0L);
        assertEquals(cardController.getCardByCardId(1L).getBody().getTitle(), "Test2");
    }

    @Test
    void testDeleteCard_HTTPStatusIsOk() {
        ResponseEntity<Card> response = cardController.deleteCard(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCard_ResponseBodyIsCorrect() {
        ResponseEntity<Card> response = cardController.deleteCard(1L);

        Card expected = new Card("Test2", 1L);
        assertEquals(expected.toString(), Objects.requireNonNull(response.getBody()).toString());
    }


    @Test
    void testDeleteCardNotFound() {
        ResponseEntity<Card> response = cardController.deleteCard(99);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }


    @Test
    void testGetCardByCardId_ReturnedCardIsNotNull() {
        ResponseEntity<Card> card = cardController.getCardByCardId(1);
        assertNotNull(card);
    }

    @Test
    void testGetCardByCardId_ReturnedCardTitleIsCorrect() {
        ResponseEntity<Card> card = cardController.getCardByCardId(0);
        assertEquals("Test1", card.getBody().getTitle());
    }

    @Test
    void testGetCardByCardId_ReturnedCardListIdIsCorrect() {
        ResponseEntity<Card> card = cardController.getCardByCardId(1);
        assertEquals(1, card.getBody().getColumnId());
    }

    @Test
    void testGetCardByCardId_NotFound() {
        ResponseEntity<Card> card = cardController.getCardByCardId(100);
        assertEquals(ResponseEntity.notFound().build(), card);
    }

    @Test
    void testGetAllCards() {
        List<Card> cards = cardController.getAllCards();
        assertEquals(2, cards.size());
    }

}