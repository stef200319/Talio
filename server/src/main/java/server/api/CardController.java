package server.api;

import commons.Card;
import commons.Subtask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.SubtaskRepository;
import server.services.ColumnService;
import server.services.CardService;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;
    private final ColumnService columnService;
    private final SubtaskRepository subtaskRepository;


    /**
     * @param cardRepository the container storing all the data relating to cards
     * @param columnRepository the container storing all the data relating to columns (lists)
     * @param subtaskRepository the container storing all the subtasks
     */
    public CardController(CardService cardRepository, ColumnService columnRepository,
                                                    SubtaskRepository subtaskRepository) {
        this.cardService = cardRepository;
        this.columnService = columnRepository;
        this.subtaskRepository = subtaskRepository;
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

        if (subtaskTitle == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }
        Card card = cardService.getById(cardId);

        // Create a new subtask
        Subtask newSubtask = new Subtask(subtaskTitle);
        subtaskRepository.save(newSubtask);

        // Update card in the database
        card.getSubtasks().add(newSubtask);
        cardService.saveCard(card);

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
        if (title == null || !cardService.existsById(cardId)) {
            return ResponseEntity.badRequest().build();
        }

        Card card = cardService.update(title, cardId);
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
        Card cardToDelete = cardService.deleteCard(cardId);
        if(cardToDelete==null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(cardToDelete);
    }

}
