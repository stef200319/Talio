package server.api;

import commons.Board;
import commons.Card;
import commons.CardTag;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.services.BoardService;
import server.services.CardService;
import server.services.CardTagService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cardTag")
public class CardTagController {

    private CardTagService cardTagService;

    private BoardService boardService;

    private CardService cardService;

    /**
     * Constructor of the CardTagController
     * @param cardTagService
     * @param boardService
     * @param cardService
     */
    public CardTagController(CardTagService cardTagService, BoardService boardService, CardService cardService)
    {

        this.cardTagService = cardTagService;
        this.boardService = boardService;
        this.cardService = cardService;

    }

    /**
     * Adds a cardTag to a board so that cardTag can be attached to cards contained in the board
     * @param title title of the new cardTag
     * @param color color of the new cardTag
     * @param boardId boardId the card belongs to
     * @return the created cardTag
     */
    @PostMapping("/addCardTagToBoard/{title}/{color}/{boardId}")
    @ResponseBody public ResponseEntity<CardTag> addCardTagToBoard(@PathVariable("title") String title,
                                                                  @PathVariable("color") String color,
                                                                  @PathVariable("boardId") long boardId) {
        if (!boardService.existsById(boardId) || color == null || title == null) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        CardTag cardTag = cardTagService.add(title, color, board);
        return ResponseEntity.ok(cardTag);
    }

//    @MessageMapping("/cardTag/addCardTagToBoard")
//    @SendTo("/topic/cardTag")
//    public void addCardTagToBoardMessage(String title) {
//    }

    /**
     * Deletes a cardTag from a board
     * @param cardTagId cardTagId of the corresponding cardTag
     * @return the deleted cardTag
     */
    @DeleteMapping("/deleteCardTagFromBoard/{cardTagId}")
    @ResponseBody public ResponseEntity<CardTag> deleteCardTagFromBoard(@PathVariable("cardTagId") long cardTagId) {
        if (!cardTagService.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagService.getById(cardTagId);
        deleteCardTagFromCards(cardTag);
        cardTagService.delete(cardTagId);
        return ResponseEntity.ok(cardTag);
    }

//    @MessageMapping("/cardTag/deleteCardTagFromBoard")
//    @SendTo("/topic/cardTag")
//    public void deleteCardTagFromBoardMessage(long cardTagId) {
//        deleteCardTagFromBoard(cardTagId);
//    }

    /**
     * adds a cardTag to a card
     * @param cardTagId cardTagId of the cardTag
     * @param cardId cardId of the Card
     * @return the added cardTag
     */
    @PostMapping("/addCardTagToCard/{cardTagId}/{cardId}")
    @ResponseBody public ResponseEntity<CardTag> addCardTagToCard(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("cardId") long cardId) {
        if (!cardService.existsById(cardId) || !cardTagService.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.getById(cardId);
        CardTag cardTag = cardTagService.getById(cardTagId);

        card.addCardTag(cardTag);
        cardService.saveCard(card);
        return ResponseEntity.ok(cardTag);
    }

//    @MessageMapping("/cardTag/addCardTagToCard")
//    @SendTo("/topic/cardTag")
//    public void addCardTagToCardMessage(long cardTagId) {
//    }

    /**
     * gets the card tags of a specified board
     * @param boardId the id of the board
     * @return a list of the card tags
     */
    @GetMapping("/getCardTagsByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<List<CardTag>> getCardTagsByBoardId(@PathVariable("boardId") long boardId){
        if(!boardService.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }
        Board board = boardService.getByBoardId(boardId);
        List<CardTag> cardTags = cardTagService.getAllByBoard(board);

        if (cardTags == null) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(cardTags);
    }
    /**
     * deletes a cardTag from a card
     * @param cardTagId cardTagId of the cardTag
     * @param cardId cardId of the Card
     * @return the deleted cardTag
     */
    @DeleteMapping("/deleteCardTagFromCard/{cardTagId}/{cardId}")
    @ResponseBody public ResponseEntity<CardTag> deleteCardTagFromCard(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("cardId") long cardId) {
        if (!cardService.existsById(cardId) || !cardTagService.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagService.getById(cardTagId);
        Card card = cardService.getById(cardId);
        card.deleteCardTag(cardTag);
        cardService.saveCard(card);
        return ResponseEntity.ok(cardTag);
    }

//    @MessageMapping("/cardTag/deleteCardTagFromCard")
//    @SendTo("/topic/cardTag")
//    public void deleteCardTagFromCardMessage(long cardTagId) {
//    }

    /**
     * edits the cardTag color
     * @param cardTagId cardTagId of the corresponding cardTag
     * @param color the new color
     * @return the edited cardTag
     */
    @PutMapping("/editCardTagColor/{cardTagId}/{color}")
    @ResponseBody public ResponseEntity<CardTag> editCardTagColor(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("color") String color) {
        if (!cardTagService.existsById((cardTagId))) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagService.editColor(cardTagId, color);
        return ResponseEntity.ok(cardTag);
    }

    /**
     * edits the cardTag title
     * @param cardTagId cardTagId of the corresponding cardTag
     * @param title the new title
     * @return the edited cardTag
     */
    @PutMapping("/editCardTagTitle/{cardTagId}/{title}")
    @ResponseBody public ResponseEntity<CardTag> editCardTagTitle(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("title") String title) {
        if (!cardTagService.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagService.editTitle(cardTagId, title);
        return ResponseEntity.ok(cardTag);
    }

    /**
     * Deletes all the entries of relation between card and cardTag
     * @param cardTag
     */
    public void deleteCardTagFromCards(CardTag cardTag) {
        List<Card> cards = cardService.getAll();
        for (Card card : cards) {
            if (card != null && card.getCardTags().contains(cardTag)) {
                card.deleteCardTag(cardTag);
                cardService.saveCard(card);
            }
        }
    }

    /**
     * Get card tag by id
     * @param cardTagId id of the card
     * @return card with specified id
     */
    @GetMapping("/getCardTagById/{cardTagId}")
    public ResponseEntity<CardTag> getCardTagById(@PathVariable("{cardTagId}") long cardTagId) {
        if(cardTagService.getById(cardTagId)==null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(cardTagService.getById(cardTagId));
    }

    /**
     * Message mapping for websockets
     * @param cardTag cardTag which was changed
     * @return card
     */
    @MessageMapping("/updateCardTag")
    @SendTo("/topic/updateCardTag")
    public CardTag updateCardTag(CardTag cardTag) {
        return cardTag;
    }

}
