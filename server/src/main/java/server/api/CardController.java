package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;
import server.database.ColumnRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/card")
public class CardController {
    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;


    /**
     * @param cardRepository the container storing all the data relating to cards
     * @param columnRepository the container storing all the data relating to columns (lists)
     */
    public CardController(CardRepository cardRepository, ColumnRepository columnRepository) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
    }
    @GetMapping("/")
    @ResponseBody public String index() {return "Card here!";}

    /**
     * @param title Of the card
     * @param columnId the columnId on which the card needs to be
     * @return if successful, the method returns an ok
     */
    @PostMapping("/addCard/{title}/{columnId}")
    public ResponseEntity<Card> addCard(@PathVariable("title") String title, @PathVariable("columnId") Long columnId) {
        if(columnId!=null) {

            Integer maxPosition = cardRepository.findMaxPositionByColumnId(columnId);

            int newPosition = maxPosition == null ? 1 : maxPosition + 1;

            Card newCard = new Card(title, columnId);
            newCard.setPosition(newPosition);

            Card saved = cardRepository.save(newCard);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }


    /**Change the title of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param title Title which should replace the old title of the card
     * @return receive a message indicating the title has change, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PostMapping("/editTitle/{cardId}/{title}")
    public ResponseEntity<String> editCardTitle(@PathVariable("cardId") long cardId,
                                                @PathVariable("title") String title){
        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setTitle(title);
            cardRepository.save(card);
            return ResponseEntity.ok("Card title updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**Change the columnId of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param columnId columnId which should replace the old columnId of the card
     * @return receive a message indicating the columnId has change, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PostMapping("/editColumn/{cardId}/{columnId}")
    public ResponseEntity<String> editCardColumn(@PathVariable("cardId") long cardId,
                                                 @PathVariable("columnId") long columnId)
    {
        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setColumnId(columnId);
            cardRepository.save(card);
            return ResponseEntity.ok("Card column updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Change a position of a Card in the Column it is in, and change the positions of other Cards in the Column which
     * have been affected by the position change. Store all the position changes in the database
     * @param cardId id of the Card whose position needs to be changed
     * @param position
     * @return Conformation that the positions of the Card and all other Cards have that been affected have been
     * changed
     */
    @PostMapping("/editPosition/{cardId}/{position}")
    public ResponseEntity<String> editCardPosition(@PathVariable("cardId") long cardId,
                                                   @PathVariable("position") int position)
    {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (!optionalCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Card card = optionalCard.get();

        int newPosition = position;
        int oldPosition = card.getPosition();
        long columnId = card.getColumnId();

        List<Card> cards = changePositionsOfAffectedCards(newPosition, oldPosition, columnId);

        card.setPosition(newPosition);
        cards.add(card);
        cardRepository.saveAll(cards);

        return ResponseEntity.ok("Card position edited successfully");
    }

    /**
     * Changes the positions of the Cards whose positions will be changed by the change of a position of another
     * Card in the List
     * @param newPosition the new position of the Card which caused the change
     * @param oldPosition the old position of the Card which caused the change
     * @param columnId the id of column in which changes of positions occur
     * @return a List containing all the Cards whose positions have been changed
     */
    private List<Card> changePositionsOfAffectedCards(int newPosition, int oldPosition, long columnId){
        List<Card> cards;
        if (oldPosition < newPosition) { // Moving the card down
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, oldPosition);
            for (Card c : cards) {
                if (c.getPosition() <= newPosition && c.getPosition()>oldPosition) {
                    c.setPosition(c.getPosition() - 1);
                }
            }
        } else { // Moving the card up
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, newPosition-1);
            for (Card c : cards) {
                if (c.getPosition() >= newPosition && c.getPosition()< oldPosition) {
                    c.setPosition(c.getPosition() + 1);
                }
            }
        }
        return cards;
    }



    /**
     * Delete a card from the database and receive a conformation using the card's id, if the card exists.
     * Else, receive a message stating the card couldn't be found.
     * @param cardId id of the card that is to be deleted
     * @return Returns a conformation message if the card is found and deleted. Else, receive an
     * appropriate response to the client.
     */
    @DeleteMapping("/deleteCard/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardId") long cardId){
        Optional<Card> cardToDeleteOptional = cardRepository.findById(cardId);

        if (cardToDeleteOptional.isPresent()) {
            Card cardToDelete = cardToDeleteOptional.get();
            long columnId = cardToDelete.getColumnId();
            Integer position = cardToDelete.getPosition();

            // Delete the card
            cardRepository.deleteById(cardId);

            // Decrement the positions of all cards in front of the deleted card
            if(position!=null){
                List<Card> cardsToUpdate = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, position);
                for (Card cardToUpdate : cardsToUpdate) {
                    int currentPosition = cardToUpdate.getPosition();
                    cardToUpdate.setPosition(currentPosition - 1);
                    cardRepository.save(cardToUpdate);
                }
            }
            return ResponseEntity.ok("Card deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a single card whose id matches the input cardId, if a card with the input id exists.
     * @param cardId id of the card that is to be retrieved.
     * @return The card that is requested using its ID. Return null if a card with the given id
     * does not exist.
     */
    @GetMapping("/getByCardId/{cardId}")
    @ResponseBody
    public Card getCardByCardId(@PathVariable("cardId") long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            return card;
        } else {
            return null;
        }
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


}
