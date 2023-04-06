package server.api;

import commons.Board;
import commons.Card;
import commons.CardTag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.CardTagRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cardTag")
public class CardTagController {

    private CardTagRepository cardTagRepository;
    private BoardRepository boardRepository;
    private CardRepository cardRepository;

    /**
     * Constructor of the CardTagController
     * @param cardTagRepository
     * @param boardRepository
     * @param cardRepository
     */
    public CardTagController(CardTagRepository cardTagRepository, BoardRepository boardRepository,
                             CardRepository cardRepository) {
        this.cardTagRepository = cardTagRepository;
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
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
        if (!boardRepository.existsById(boardId) || color == null || title == null) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardRepository.findById(boardId).get();
        CardTag cardTag = new CardTag(title, color, board);
        cardTagRepository.save(cardTag);
        return ResponseEntity.ok(cardTag);
    }

    /**
     * Deletes a cardTag from a board
     * @param cardTagId cardTagId of the corresponding cardTag
     * @return the deleted cardTag
     */
    @DeleteMapping("deleteCardTagFromBoard/{cardTagId}")
    @ResponseBody public ResponseEntity<CardTag> deleteCardTagFromBoard(@PathVariable("cardTagId") long cardTagId) {
        if (!cardTagRepository.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagRepository.findById(cardTagId).get();

        deleteCardTagFromCards(cardTag);

        cardTagRepository.deleteById(cardTagId);
        return ResponseEntity.ok(cardTag);
    }


    /**
     * adds a cardTag to a card
     * @param cardTagId cardTagId of the cardTag
     * @param cardId cardId of the Card
     * @return the added cardTag
     */
    @PostMapping("/addCardTagToCard/{cardTagId}/{cardId}")
    @ResponseBody public ResponseEntity<CardTag> addCardTagToCard(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("cardId") long cardId) {
        if (!cardRepository.existsById(cardId) || !cardTagRepository.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        CardTag cardTag = cardTagRepository.findById(cardTagId).get();
        card.addCardTag(cardTag);
        cardRepository.save(card);
        return ResponseEntity.ok(cardTag);
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
        if (!cardRepository.existsById(cardId) || !cardTagRepository.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        CardTag cardTag = cardTagRepository.findById(cardTagId).get();
        card.deleteCardTag(cardTag);
        cardRepository.save(card);
        return ResponseEntity.ok(cardTag);
    }

    /**
     * edits the cardTag color
     * @param cardTagId cardTagId of the corresponding cardTag
     * @param color the new color
     * @return the edited cardTag
     */
    @PutMapping("/editCardTagColor/{cardTagId}/{color}")
    @ResponseBody public ResponseEntity<CardTag> editCardTagColor(@PathVariable("cardTagId") long cardTagId,
                                                                  @PathVariable("color") String color) {
        if (!cardTagRepository.existsById((cardTagId))) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagRepository.findById(cardTagId).get();
        cardTag.setColor(color);
        cardTagRepository.save(cardTag);
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
        if (!cardTagRepository.existsById(cardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        CardTag cardTag = cardTagRepository.findById(cardTagId).get();
        cardTag.setTitle(title);
        cardTagRepository.save(cardTag);
        return ResponseEntity.ok(cardTag);
    }

    /**
     * Gets the cardTags given a certain boardId
     * @param boardId
     * @return list of cardTags
     */
    @GetMapping("/getCardTagsByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<List<CardTag>> getCardTagsByBoardId(@PathVariable("boardId") long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardRepository.findById(boardId).get();
        List<CardTag> cardTags = cardTagRepository.findCardTagsByBoard(board);

        if (cardTags == null) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(cardTags);
    }

    /**
     * Deletes all the entries of relation between card and cardTag
     * @param cardTag
     */
    public void deleteCardTagFromCards(CardTag cardTag) {
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            if (card != null && card.getCardTags().contains(cardTag)) {
                card.deleteCardTag(cardTag);
                cardRepository.save(card);
            }
        }
    }


}
