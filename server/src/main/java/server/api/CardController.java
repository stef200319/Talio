package server.api;

import commons.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.services.ColumnService;
import server.services.CardService;
import server.services.SubtaskService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;
    private final ColumnService columnService;
    private final SubtaskService subtaskService;



    /**
     * @param cardService the service for card operations
     * @param columnService the service for column (list) operations
     * @param subtaskService the service for subtask operations
     */
    public CardController(CardService cardService, ColumnService columnService,
                                                    SubtaskService subtaskService) {
        this.cardService = cardService;
        this.columnService = columnService;
        this.subtaskService = subtaskService;
    }

    /**
     * Return all the cards which are stored in the database
     * @return all the cards in the database
     */

    @GetMapping("/getAllCards")
    @ResponseBody
    public List<Card> getAllCards() {
        return cardService.getAll();
    }

//    @MessageMapping("/card/addCard")
//    @SendTo("/topic/card")
//    public Card addMessage(Card c) {
//        addCard(c.getTitle(), c.getColumnId());
//        return c;
//    }

    /**
     * Get a single card whose id matches the input cardId, if a card with the input id exists.
     * @param cardId id of the card that is to be retrieved.
     * @return The card that is requested using its ID. Return null if a card with the given id
     * does not exist.
     */
    @GetMapping("/getCardByCardId/{cardId}")
    @ResponseBody public ResponseEntity<Card> getCardByCardId(@PathVariable("cardId") long cardId) {
        Card card = cardService.getById(cardId);
        if(card==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(card);
    }



        /**
         * @param title Of the card
         * @param columnId the columnId on which the card needs to be
         * @return if successful, the method returns an ok
         */
    @PostMapping("/addCard/{title}/{columnId}")
    @ResponseBody public ResponseEntity<Card> addCard(@PathVariable("title") String title,
                                                      @PathVariable("columnId") Long columnId) {
        if (title == null || !columnService.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Card saved = cardService.addCard(title, columnId);
        return ResponseEntity.ok(saved);
    }


    /**
     * Creates a new subtask and associates it with the specified card.
     *
     * @param cardId the ID of the card to which the subtask should be added
     * @param subtaskTitle the subtask to be added to the card
     * @return a ResponseEntity containing the updated Card object if the card was found, or a 404 Not Found
     * response if the card was not found
     */

    @PostMapping("/addSubtask/{cardId}/{subtaskTitle}")
    public ResponseEntity<Card> createSubtask(@PathVariable(value = "cardId") long cardId,
                                              @PathVariable(value = "subtaskTitle") String subtaskTitle) {

        if (subtaskTitle == null) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.createSubtask(cardId, subtaskTitle);
        if(card==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(card);
    }


    /**Change the title of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param title Title which should replace the old title of the card
     * @return receive a message indicating the title has change, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PutMapping("/editCardTitle/{cardId}/{title}")
    @ResponseBody public ResponseEntity<Card> editCardTitle(@PathVariable("cardId") long cardId,
                                                @PathVariable("title") String title){
        if (title == null) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.update(title, cardId);
        if(card == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(card);
    }

    /**Change the description of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param description Description which should replace the old title of the card
     * @return receive a message indicating the description has change, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PutMapping("/editCardDescription/{cardId}/{description}")
    @ResponseBody public ResponseEntity<Card> editCardDescription(@PathVariable("cardId") long cardId,
                                                            @PathVariable("description") String description){
        if (description == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editDescription(cardId, description);
        return ResponseEntity.ok(card);
    }

    /**Change the Background colour of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param bgColour Background colour which should replace the old Background colour of the card
     * @return receive a message indicating the Background colour has change, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardBackgroundColour/{cardId}/{bgColour}")
    @ResponseBody public ResponseEntity<Card> editCardBackgroundColour(@PathVariable("cardId") long cardId,
                                                                  @PathVariable("bgColour") String bgColour){
        if (bgColour == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editBackgroundColour(cardId, bgColour);
        return ResponseEntity.ok(card);
    }


    /**Change the Border colour of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param borderColour Border colour which should replace the old Border colour of the card
     * @return receive a message indicating the Border colour has changed, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardBorderColour/{cardId}/{borderColour}")
    @ResponseBody public ResponseEntity<Card> editCardBorderColour
    (@PathVariable("cardId") long cardId, @PathVariable("borderColour") String borderColour){
        if (borderColour == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editBorderColour(cardId, borderColour);
        return ResponseEntity.ok(card);
    }


    /**Change the Font-type of a Card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param fontType Font Type which should replace the old Font Type of the card
     * @return receive a message indicating the Font Type has changed, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardFontType/{cardId}/{fontType}")
    @ResponseBody public ResponseEntity<Card> editCardFontType
    (@PathVariable("cardId") long cardId, @PathVariable("fontType") String fontType){
        if (fontType == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editFontType(cardId, fontType);
        return ResponseEntity.ok(card);
    }

    /**Change the Boldness of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param bold Boldness which should replace the old Boldness of the card
     * @return receive a message indicating the Boldness has changed, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardFontStyleBold/{cardId}/{bold}")
    @ResponseBody public ResponseEntity<Card> editCardFontStyleBold
    (@PathVariable("cardId") long cardId, @PathVariable("bold") boolean bold){
        if (!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editFontStyleBold(cardId, bold);
        return ResponseEntity.ok(card);
    }


    /**Change the Italicness of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param italic Italicness which should replace the old Boldness of the card
     * @return receive a message indicating the Italicness has changed, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardFontStyleItalic/{cardId}/{italic}")
    @ResponseBody public ResponseEntity<Card> editCardFontStyleItalic
    (@PathVariable("cardId") long cardId, @PathVariable("italic") boolean italic){
        if (!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editFontStyleItalic(cardId, italic);
        return ResponseEntity.ok(card);
    }


    /**Change the Font Colour of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param fontColour Font Colour which should replace the old Font Colour of the card
     * @return receive a message indicating the Font Colour has changed, if the card exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editCardFontColour/{cardId}/{fontColour}")
    @ResponseBody public ResponseEntity<Card> editCardFontColour
    (@PathVariable("cardId") long cardId, @PathVariable("fontColour") String fontColour){
        if (!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editFontColour(cardId, fontColour);
        return ResponseEntity.ok(card);
    }





    /**Change the columnId of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param columnId columnId which should replace the old columnId of the card
     * @return receive a message indicating the columnId has changed, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PutMapping("/editCardColumn/{cardId}/{columnId}")
    @ResponseBody public ResponseEntity<Card> editCardColumn(@PathVariable("cardId") long cardId,
                                                 @PathVariable("columnId") long columnId) {
        if (!columnService.existsById(columnId) || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editColumn(cardId, columnId);
        return ResponseEntity.ok(card);
    }

    /**
     * Change a position of a Card in the Column it is in, and change the positions of other Cards in the Column which
     * have been affected by the position change. Store all the position changes in the database
     * @param cardId id of the Card whose position needs to be changed
     * @param newPosition
     * @return Conformation that the positions of the Card and all other Cards have that been affected have been
     * changed
     */
    @PutMapping("/editCardPosition/{cardId}/{newPosition}")
    @ResponseBody public ResponseEntity<Card> editCardPosition(@PathVariable("cardId") long cardId,
                                                   @PathVariable("newPosition") int newPosition) {
        if (!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.editCardPosition(cardId,newPosition);
        if(card==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(card);
    }

    /**
     * Changes the positions of the Cards whose positions will be changed by the change of a position of another
     * Card in the List
     * @param newPosition the new position of the Card which caused the change
     * @param oldPosition the old position of the Card which caused the change
     * @param columnId the id of column in which changes of positions occur
     * @return a List containing all the Cards whose positions have been changed
     */
    private List<Card> changePositionsOfAffectedCards(int oldPosition, int newPosition, long columnId) {
        List<Card> cards = cardService.changePositionsOfAffectedCards(oldPosition, newPosition, columnId);
        return cards;
    }



    /**
     * Delete a card from the database and receive a conformation using the card's id, if the card exists.
     * @param cardId id of the card that is to be deleted
     * @return Returns a conformation message if the card is found and deleted. Else, receive an
     * appropriate response to the client.
     */
    @DeleteMapping("/deleteCard/{cardId}")
    @ResponseBody public ResponseEntity<Card> deleteCard(@PathVariable("cardId") long cardId){
        if(!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card cardToDelete = cardService.getById(cardId);


        // Delete all the cardTags
        cardToDelete.setCardTags(new HashSet<>());

        // Delete the card
        cardService.deleteCard(cardId);

        return ResponseEntity.ok(cardToDelete);
    }

    /**
     * Get all the subtasks from a card
     * @param cardId id of the card to get the subtasks from
     * @return a list with all the corresponding subtasks
     */
    @GetMapping("/getSubtasks/{cardId}")
    @ResponseBody public ResponseEntity<List<Subtask>> getSubtasksByCardId(@PathVariable("cardId") long cardId) {
        List<Subtask> subtasks = cardService.getAllSubtasksByCardId(cardId);
        if(subtasks == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(subtasks);
    }

    /**
<<<<<<< HEAD
     * Gets the cardTags given a cardId
     * @param cardId
     * @return list of cardTags
     */
    @GetMapping("/getCardTagsByCardId/{cardId}")
    @ResponseBody public ResponseEntity<List<CardTag>> getCardTagsByCardId(@PathVariable("cardId") long cardId) {
        if (!cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cardService.getCardTagsByCardId(cardId));
    }

    /**
     * Changes the position of a subtask in a card
     * @param cardId the card the subtask is in
     * @param oldPos the old position of the subtask
     * @param newPos the new position of the subtask
     * @return the new card with updated subtask positions
     */
    @PutMapping("/changeSubtaskPosition/{cardId}/{oldPos}/{newPos}")
    @ResponseBody public ResponseEntity<Card> changeSubtaskPosition(@PathVariable("cardId") long cardId,
                                                                            @PathVariable("oldPos") int oldPos,
                                                                            @PathVariable("newPos") int newPos) {
        Card c = cardService.changeSubtaskPosition(cardId, oldPos, newPos);
        if(c == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(c);
    }

    /**
     * Deletes a subtask from the card
     * @param cardId id of the card the subtask is in
     * @param subtaskId id of the subtask to be deleted
     * @return the new updated card
     */
    @DeleteMapping("/deleteSubtask/{cardId}/{subtaskId}")
    @ResponseBody public ResponseEntity<Card> deleteSubtask(@PathVariable("cardId") long cardId,
                                                            @PathVariable("subtaskId") int subtaskId) {
        Card c = cardService.deleteSubtask(cardId, subtaskId);
        if(c==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(c);
    }

    /**
     * Checks if card with specified id exists
     * @param cardId id of the card
     * @return true if it does else otherwise
     */
    @GetMapping("/existsById/{cardId}")
    @ResponseBody public ResponseEntity<Boolean> existsById(@PathVariable("cardId") long cardId) {
        Card c = cardService.getById(cardId);
        if(c==null)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    /**
     * Updates the given card and sends it to the "/topic/updateCard" topic.
     *
     * @param card the card to be updated
     *
     * @return the updated card
     */
    @MessageMapping("/updateCard")
    @SendTo("/topic/updateCard")
    public Card updateCard(Card card) {
        return card;
    }

    /**
     * Message for deleting a card
     * @param card card
     * @return same card
     */
    @MessageMapping("/deleteCard")
    @SendTo("/topic/deleteCard")
    public Card deleteCard(Card card) { return card; }

}
