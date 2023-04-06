package server.api;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.services.CardService;
import server.services.ColumnService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import commons.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import commons.Card;
import server.services.CardService;
import server.services.ColumnService;
import server.services.SubtaskService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CardControllerTest {
    private TestCardRepository cardRepository;
    private TestColumnRepository columnRepository;
    private TestSubtaskRepository subtaskRepository;

    private CardService cardService;
    private ColumnService columnService;
    private SubtaskService subtaskService;
    private CardController cardController;

    @BeforeEach
    void setUp() {
        this.columnRepository = new TestColumnRepository();
        this.cardRepository = new TestCardRepository();
        this.subtaskRepository = new TestSubtaskRepository();

        cardService = new CardService(cardRepository, subtaskRepository);
        columnService = new ColumnService(columnRepository);
        subtaskService = new SubtaskService(subtaskRepository);

        cardController = new CardController(cardService, columnService, subtaskService, cardRepository);
    }


    @Test
    void testAddCard_responseStatusIsOk() {

        ResponseEntity<Card> response = cardController.addCard("Test1", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddCard_columnDoesntExist() {
        ResponseEntity<Card> response = cardController.addCard("Test2", 10L);

        assertEquals(ResponseEntity.badRequest().build(), response);
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
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testEditCardList_responseStatusIsOk() {
        ResponseEntity<Card> response = cardController.editCardColumn(1, 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditCardList_responseBodyEqualsExpected() {
        ResponseEntity<Card> response = cardController.editCardColumn(1, 0);
        Card expected = new Card("Test2", 0);
        expected.setPosition(2);
        expected.setId(1);
        assertEquals(expected.toString(), response.getBody().toString());
    }

    @Test
    void testEditCardList_cardListIdEqualsExpected() {
        cardController.editCardColumn(1, 1);
        Card card = cardRepository.findById(1L).get();
        assertEquals(1, card.getColumnId());
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

    private Method changePositionsOfAffectedCards() throws NoSuchMethodException {
        Method method = CardController.class.getDeclaredMethod("changePositionsOfAffectedCards",
                Integer.class, Integer.class, Long.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    void changePositionsOfAffectedCards_movingUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = CardController.class.getDeclaredMethod("changePositionsOfAffectedCards",
                int.class, int.class, long.class);
        method.setAccessible(true);

        List<Card> ret = (List<Card>) method.invoke(cardController, 1, 2, 1L);

        assertEquals(1, ret.get(0).getPosition());
    }

    @Test
    void changePositionOfAffectedCards_movingDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = CardController.class.getDeclaredMethod("changePositionsOfAffectedCards",
                int.class, int.class, long.class);
        method.setAccessible(true);

        List<Card> ret = (List<Card>) method.invoke(cardController, 2, 1, 1L);

        assertEquals(2, ret.get(0).getPosition());

    }

    @Test
    void editCardPosition_doesntExist() {
        ResponseEntity<Card> ret = cardController.editCardPosition(10, 1);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void editCardPosition_positionToBig() {
        ResponseEntity<Card> ret = cardController.editCardPosition(1, 3);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void editCardPosition_successful() {
        ResponseEntity<Card> ret = cardController.editCardPosition(1, 1);

        Card expected = new Card("Test2", 1);
        expected.setId(1);
        expected.setPosition(1);
        assertEquals(expected.toString(), ret.getBody().toString());
//        assertEquals(2, cardController.getAllCards().get(0).getPosition());
//        assertEquals(1, cardController.getAllCards().get(1).getPosition());
    }

    @Test
    void createSubtask_nullTitle() {
        ResponseEntity<Card> ret = cardController.createSubtask(1, null);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void createSubtask_unsuccessful() {
        ResponseEntity<Card> ret = cardController.createSubtask(10, "title");
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void createSubtask_successful() {
        ResponseEntity<Card> ret = cardController.createSubtask(1, "subtask");

        Card expected = new Card("Test2", 1);
        expected.setId(1);
        expected.setPosition(1);
        List<Subtask> subtasks = expected.getSubtasks();
        subtasks.add(new Subtask("s1"));
        subtasks.add(new Subtask("s2"));
        subtasks.add(new Subtask("subtask"));
        expected.setSubtasks(subtasks);
        assertEquals(expected.toString(), ret.getBody().toString());
    }

    @Test
    void getSubtasksByCardId_doesntExist() {
        ResponseEntity<List<Subtask>> ret = cardController.getSubtasksByCardId(10);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void getSubtasksByCardId_successful() {
        ResponseEntity<List<Subtask>> ret = cardController.getSubtasksByCardId(0);
        System.out.println(ret.getBody().toString());
        assertEquals(2, ret.getBody().size());
    }

    @Test
    void changeSubtaskPosition_outOfBounds() {
        ResponseEntity<Card> ret = cardController.changeSubtaskPosition(0, 3, 5);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void changeSubtaskPosition_shiftDown() {
        ResponseEntity<Card> ret = cardController.changeSubtaskPosition(0, 0, 1);
        List<Subtask> expected = new ArrayList<>();
        Subtask s1 = new Subtask("s1");
        Subtask s2 = new Subtask("s2");
        expected.add(s2);
        expected.add(s1);
        assertEquals(expected, ret.getBody().getSubtasks());
    }

    @Test
    void changeSubtaskPosition_shiftUp() {
        ResponseEntity<Card> ret = cardController.changeSubtaskPosition(0, 1, 0);
        List<Subtask> expected = new ArrayList<>();
        Subtask s1 = new Subtask("s1");
        Subtask s2 = new Subtask("s2");
        expected.add(s2);
        expected.add(s1);
        assertEquals(expected, ret.getBody().getSubtasks());
    }

    @Test
    void deleteSubtask_doesntExist() {
        ResponseEntity<Card> ret = cardController.deleteSubtask(10,5);
        assertEquals(ResponseEntity.badRequest().build(), ret);
    }

    @Test
    void deleteSubtask_successful() {
        ResponseEntity<Card> ret = cardController.deleteSubtask(0,0);
        assertEquals(1, ret.getBody().getSubtasks().size());
    }
}
