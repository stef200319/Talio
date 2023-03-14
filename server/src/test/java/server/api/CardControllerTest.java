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
    private CardController controller;

    @BeforeEach
    void setUp() {
        this.columnRepository = new TestColumnRepository();
        this.cardRepository = new TestCardRepository();
        controller = new CardController(cardRepository, columnRepository);
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
        assertEquals("Test1", response.getBody().getTitle());
    }

    @Test
    void testAddCard_responseListIdEqualsExpected() {
        ResponseEntity<Card> response = controller.addCard("Test1", 1);
        assertEquals(1, response.getBody().getColumnId());
    }

//    @Test
//    void testAddCard_positionExpectedSameList() {
//        ResponseEntity<Card> response = controller.addCard("Test1", 1);
//        assertEquals(1, response.getBody().getPosition());
//    }
//    @Test
//    void testAddCard_positionExpectedSameList2() {
//        controller.addCard("Test1", 1);
//        controller.addCard("Test2", 1);
//        Card card = controller.getCardByCardId(2);
//        assertEquals(2, card.getPosition());
//    }


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
        Card card = cardRepository.findById(1L).get();
        assertEquals("editTitle", card.getTitle());
    }

    @Test
    void testEditCardTitle_cardNotFound() {
        ResponseEntity<String> response = controller.editCardTitle(123, "editTitle");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testEditCardList_responseStatusIsOk() {
        ResponseEntity<String> response = controller.editCardColumn(1, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardList_responseBodyEqualsExpected() {
        ResponseEntity<String> response = controller.editCardColumn(1, 2);
        assertEquals("Card list updated successfully", response.getBody());
    }

    @Test
    void testEditCardList_cardListIdEqualsExpected() {
        ResponseEntity<String> response = controller.editCardColumn(1, 2);
        Card card = cardRepository.findById(1L).get();
        assertEquals(2, card.getColumnId());
    }

    @Test
    void testEditCardList_cardNotFound() {
        ResponseEntity<String> response = controller.editCardColumn(100, 2);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDeleteCard_CardDoesNotExist() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals(controller.getCardByCardId(1L).getTitle(), "Test2");
    }

    @Test
    void testDeleteCard_HTTPStatusIsOk() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCard_ResponseBodyIsCorrect() {
        ResponseEntity<String> response = controller.deleteCard(1L);
        assertEquals("Card deleted successfully", response.getBody());
    }


    @Test
    void testDeleteCardNotFound() {
        ResponseEntity<String> response = controller.deleteCard(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testGetCardByCardId_ReturnedCardIsNotNull() {
        Card card = controller.getCardByCardId(1);
        assertNotNull(card);
    }

    @Test
    void testGetCardByCardId_ReturnedCardTitleIsCorrect() {
        Card card = controller.getCardByCardId(1);
        assertEquals("Test1", card.getTitle());
    }

    @Test
    void testGetCardByCardId_ReturnedCardListIdIsCorrect() {
        Card card = controller.getCardByCardId(1);
        assertEquals(1, card.getColumnId());
    }

    @Test
    void testGetCardByCardId_NotFound() {
        Card card = controller.getCardByCardId(100);
        assertNull(card);
    }

    @Test
    void testGetAllCards() {
        List<Card> cards = controller.getAllCards();
        assertEquals(2, cards.size());
    }

    @Test
    void testGetCardByListId_2entries() {
        List<Card> ret = controller.getCardByColumnId(1L);
        System.out.println(ret);
        List<Card> expected = new ArrayList<>();
        expected.add(new Card("Test1", 1));
        expected.add(new Card("Test2", 1));
        assertEquals(expected, ret);
    }

    @Test
    void testGetCardByListId_noMatch() {
        List<Card> ret = controller.getCardByColumnId(2);

        List<Card> expected = new ArrayList<>();

        assertEquals(expected, ret);
    }

    @Test
    void testGetCardByListId_empty() {
        controller.deleteCard(1L);
        controller.deleteCard(1L);

        List<Card> ret = controller.getCardByColumnId(1);
        List<Card> expected = new ArrayList<>();

        assertEquals(expected, ret);
    }
}