package server.api;

import commons.Board;
import commons.Card;
import commons.CardTag;
import commons.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.CardTagRepository;
import server.database.ColumnRepository;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTagControllerTest {

    CardTag cardTag1;
    CardTag cardTag2;
    Board board1;
    Card card1;
    Card card2;
    Column column1;

    CardTagController cardTagController;
    CardTagRepository cardTagRepository;
    BoardRepository boardRepository;
    CardRepository cardRepository;
    ColumnRepository columnRepository;



    @BeforeEach
    void setUp() {
        cardTagRepository = new TestCardTagRepository();
        boardRepository = new TestBoardRepository();
        cardRepository = new TestCardRepository();
        columnRepository = new TestColumnRepository();

        cardTagController = new CardTagController(cardTagRepository, boardRepository, cardRepository);

        board1 = new Board("board1");
        board1.setId(69L);
        boardRepository.save(board1);

        cardTag1 = new CardTag("cardTag1", "ffaaff", board1);
        cardTag2 = new CardTag("cardTag2", "ffaa11", board1);
        cardTagRepository.save(cardTag1);
        cardTagRepository.save(cardTag2);

        column1 = new Column("column1", board1.getId());
        column1.setId(420L);
        columnRepository.save(column1);

        card1 = new Card("card1", column1.getId());
        card2 = new Card("card2", column1.getId());
        card1.setId(69L);
        card2.setId(420L);
        cardRepository.save(card1);
        cardRepository.save(card2);
    }

    @Test
    void addCardTagToBoardNonExistingBoardIdTest() {
        HttpStatus statuscode = cardTagController.addCardTagToBoard("bla", "ffaaff", 98692368L)
                .getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statuscode);
    }

    @Test
    void addCardTagToBoardSuccessTest() {
        ResponseEntity<CardTag> response = cardTagController
                .addCardTagToBoard("cardTag", "ffaaff", board1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(board1, response.getBody().getBoard());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteCardTagFromBoardNonExistingCardTagIdTest() {
        HttpStatus statuscode = cardTagController.deleteCardTagFromBoard(2349285952L).getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statuscode);
    }

    @Test
    void deleteCardTagFromBoardSuccessTest() {
        ResponseEntity<CardTag> addResponse = cardTagController
                .addCardTagToBoard("cardTag", "ffaaff", board1.getId());
        CardTag c = addResponse.getBody();
        long id = c.getId();
        ResponseEntity<CardTag> response = cardTagController.deleteCardTagFromBoard(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(cardTagRepository.existsById(response.getBody().getId()));
    }

    @Test
    void addCardTagToCardFailTest() {
        ResponseEntity<CardTag> response = cardTagController.addCardTagToCard(28934924L, 1203947L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addCardTagToCardSuccessTest() {
        ResponseEntity<CardTag> response = cardTagController.addCardTagToCard(cardTag1.getId(), card1.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(card1.getCardTags().contains(cardTag1));
    }

    @Test
    void deleteCardTagFromCardFailTest() {
        ResponseEntity<CardTag> response = cardTagController.deleteCardTagFromCard(234928572L, 2304702L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteCardTagFromCardSuccessTest() {
        ResponseEntity<CardTag> responseAdd = cardTagController.addCardTagToCard(cardTag1.getId(), card1.getId());
        ResponseEntity<CardTag> responseDelete = cardTagController.deleteCardTagFromCard(cardTag1.getId(), card1.getId());
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertFalse(card1.getCardTags().contains(cardTag1));
    }

    @Test
    void editCardTagColorFailTest() {
        ResponseEntity<CardTag> response = cardTagController.editCardTagColor(20359874582L, "ffaa33");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editCardColorSuccessTest() {
        ResponseEntity<CardTag> response = cardTagController.editCardTagColor(cardTag1.getId(), "af00aa");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("af00aa", cardTag1.getColor());
    }

    @Test
    void editCardTagTitleFailTest() {
        ResponseEntity<CardTag> response = cardTagController.editCardTagTitle(20359874582L, "newTitle");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editCardTitleSuccessTest() {
        ResponseEntity<CardTag> response = cardTagController.editCardTagTitle(cardTag1.getId(), "newTitle");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("newTitle", cardTag1.getTitle());
    }
}