package server.api;

import commons.Card;
import commons.Subtask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;
import server.database.ColumnRepository;
import server.database.SubtaskRepository;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    private final SubtaskRepository subtaskRepository;


    /**
     * @param cardRepository the container storing all the data relating to cards
     * @param columnRepository the container storing all the data relating to columns (lists)
     * @param subtaskRepository the container storing all the subtasks
     */
    public CardController(CardRepository cardRepository, ColumnRepository columnRepository,
                                                    SubtaskRepository subtaskRepository) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.subtaskRepository = subtaskRepository;
    }

    /**
     * Return all the cards which are stored in the database
     * @return all the cards in the database
     */

    @GetMapping("/getAllCards")
    @ResponseBody
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    /**
     * Get a single card whose id matches the input cardId, if a card with the input id exists.
     * @param cardId id of the card that is to be retrieved.
     * @return The card that is requested using its ID. Return null if a card with the given id
     * does not exist.
     */
    @GetMapping("/getCardByCardId/{cardId}")
    @ResponseBody public ResponseEntity<Card> getCardByCardId(@PathVariable("cardId") long cardId) {
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.notFound().build();
        }

        Card card = cardRepository.findById(cardId).get();
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
        if (title == null || !columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Integer maxPosition = cardRepository.findMaxPositionByColumnId(columnId);

        int newPosition = maxPosition == null ? 1 : maxPosition + 1;

        Card newCard = new Card(title, columnId);
        newCard.setPosition(newPosition);

        Card saved = cardRepository.save(newCard);
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

        if (subtaskTitle == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }
        Card card = cardRepository.findById(cardId).get();

        // Create a new subtask
        Subtask newSubtask = new Subtask(subtaskTitle);
        subtaskRepository.save(newSubtask);

        // Update card in the database
        card.getSubtasks().add(newSubtask);
        cardRepository.save(card);

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
        if (title == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setTitle(title);
        cardRepository.save(card);
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
        if (description == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setDescription(description);
        cardRepository.save(card);
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
        if (bgColour == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setBgColour(bgColour);
        cardRepository.save(card);
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
        if (borderColour == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setBorderColour(borderColour);
        cardRepository.save(card);
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
        if (fontType == null || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setFontType(fontType);
        cardRepository.save(card);
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
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setFontStyleBold(bold);
        cardRepository.save(card);
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
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setFontStyleItalic(italic);
        cardRepository.save(card);
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
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();
        card.setFontColour(fontColour);
        cardRepository.save(card);
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
        if (!columnRepository.existsById(columnId) || !cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();

        List<Card> cards = cardRepository.findByColumnIdAndPositionGreaterThan(card.getColumnId(),card.getPosition());
        for(Card c : cards) {
            int pos = c.getPosition();
            c.setPosition(pos - 1);
        }

        Integer maxPosition = cardRepository.findMaxPositionByColumnId(columnId);
        card.setPosition(maxPosition+1);

        card.setColumnId(columnId);
        cardRepository.save(card);
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
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardRepository.findById(cardId).get();

        int oldPosition = card.getPosition();
        long columnId = card.getColumnId();

        if (newPosition > cardRepository.findMaxPositionByColumnId(columnId) || newPosition <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = changePositionsOfAffectedCards(oldPosition, newPosition, columnId);

        card.setPosition(newPosition);
        cards.add(card);
        cardRepository.saveAll(cards);

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
        List<Card> cards;

        if (oldPosition < newPosition) { // Moving the card down
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, oldPosition);
            for (Card c : cards) {
                int position = c.getPosition();
                if (position <= newPosition && position > oldPosition) {
                    c.setPosition(position - 1);
                }
            }
        }
        else { // Moving the card up
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, newPosition - 1);
            for (Card c : cards) {
                int position = c.getPosition();
                if (position >= newPosition && position < oldPosition) {
                    c.setPosition(position + 1);
                }
            }
        }
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
        if (!cardRepository.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card cardToDelete = cardRepository.findById(cardId).get();
        long columnId = cardToDelete.getColumnId();
        Integer position = cardToDelete.getPosition();

        // Delete the card
        cardRepository.deleteById(cardId);

        // Decrement the positions of all cards in front of the deleted card
        if (position != null) {
            List<Card> cardsToUpdate = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, position);
            for (Card cardToUpdate : cardsToUpdate) {
                int currentPosition = cardToUpdate.getPosition();
                cardToUpdate.setPosition(currentPosition - 1);
                cardRepository.save(cardToUpdate);
            }
        }
        return ResponseEntity.ok(cardToDelete);
    }

}
