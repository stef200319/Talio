package server.api;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import commons.*;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.database.CardTagRepository;
import server.services.*;


class CardControllerTest {

    private TestBoardRepository boardRepository;
    private TestBoardTagRepository boardTagRepository;

    private TestColumnRepository columnRepository;
    private TestCardRepository cardRepository;
    private TestSubtaskRepository subtaskRepository;

    private BoardService boardService;

    private BoardTagService boardTagService;
    private ColumnService columnService;
    private CardService cardService;
    private SubtaskService subtaskService;

    private CardTagService cardTagService;
    private BoardController boardController;
    private BoardTagController boardTagController;
    private ColumnController columnController;
    private CardController cardController;
    private CardTagController cardTagController;
    private CardTagRepository cardTagRepository;


    @BeforeEach
    void setUp() {
        boardRepository = new TestBoardRepository();
        boardTagRepository = new TestBoardTagRepository();
        columnRepository = new TestColumnRepository();
        subtaskRepository = new TestSubtaskRepository();
        cardTagRepository = new TestCardTagRepository();
        cardRepository = new TestCardRepository();

        boardService = new BoardService(boardRepository);
        boardTagService = new BoardTagService(boardTagRepository);
        columnService = new ColumnService(columnRepository);
        cardService = new CardService(cardRepository, subtaskRepository);
        subtaskService = new SubtaskService(subtaskRepository);
        cardTagService = new CardTagService(cardTagRepository);

        cardController = new CardController(cardService, columnService, subtaskService);
        columnController = new ColumnController(columnRepository, columnService, boardService, cardService);
        cardTagController = new CardTagController(cardTagService, boardService, cardService);
        boardController = new BoardController(boardService, columnService, cardTagService, cardService, cardTagController);
        boardTagController = new BoardTagController(boardTagService, boardService);

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

    @Test
    void editCardDescriptionTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardDescription(retCard.getBody().getId(), "testing1212");

        assertEquals(retCard.getBody().getDescription(), "testing1212");

    }

    @Test
    void editCardBackgroundColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardBackgroundColour(retCard.getBody().getId(), "black");

        assertEquals(retCard.getBody().getBgColour(), "black");

    }

    @Test
    void editCardBorderColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardBorderColour(retCard.getBody().getId(), "yellow");

        assertEquals(retCard.getBody().getBorderColour(), "yellow");

    }

    @Test
    void editCardFontTypeTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardFontType(retCard.getBody().getId(), "Arial");

        assertEquals(retCard.getBody().getFontType(), "Arial");

    }

    @Test
    void editCardFontStyleBoldTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardFontStyleBold(retCard.getBody().getId(), true);

        assertTrue(retCard.getBody().getFontStyleBold());

    }

    @Test
    void editCardFontStyleItalicTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardFontStyleItalic(retCard.getBody().getId(), false);

        assertFalse(retCard.getBody().getFontStyleItalic());

    }

    @Test
    void editCardFontColourTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        cardController.editCardFontColour(retCard.getBody().getId(), "blue");

        assertEquals(retCard.getBody().getFontColour(), "blue");

    }

    @Test
    void getCardTagsByCardIdTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        ResponseEntity<CardTag> ret1 = cardTagController
            .addCardTagToBoard("1", "black", retBoard.getBody().getId());

        ResponseEntity<CardTag> ret2 = cardTagController
            .addCardTagToBoard("2", "black", retBoard.getBody().getId());

        List<CardTag> cardTagList = new ArrayList<>();
        cardTagList.add(ret1.getBody());
        cardTagList.add(ret2.getBody());

        cardTagController.addCardTagToCard(ret1.getBody().getId(), retCard.getBody().getId());
        cardTagController.addCardTagToCard(ret2.getBody().getId(), retCard.getBody().getId());

        assertEquals(retCard.getBody().getCardTags(), cardTagList);

    }

    @Test
    void existsByIdTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        boolean result = cardController.existsById(retCard.getBody().getId()).getBody();

        assertTrue(result);

    }

    @Test
    void updateCardTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        Card testCard = cardController.updateCard(retCard.getBody());

        assertTrue(retCard.getBody().equals(testCard));

    }

    @Test
    void deleteCardTest() {
        ResponseEntity<Board> retBoard = boardController.addBoard("testingBoard");

        ResponseEntity<Column> retColumn = columnController.addColumn("testingColumn", retBoard.getBody().getId());

        ResponseEntity<Card> retCard = cardController.addCard("testingCard", retColumn.getBody().getId());

        Card testCard = cardController.deleteCard(retCard.getBody());

        assertTrue(retCard.getBody().equals(testCard));

    }


}
