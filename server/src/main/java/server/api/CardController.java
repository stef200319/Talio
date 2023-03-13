package server.api;

import commons.Card;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/card")
public class CardController {
    private final CardRepository repo;

    /**
     * @param repo the created card
     */
    public CardController(CardRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/")
    @ResponseBody public String index() {return "Card here!";}

    /**
     * @param title Of the card
     * @param listId the list on which the card needs to be
     * @return if successful, the method returns an ok
     */
    @PostMapping("/addCard/{title}/{listId}")
    public ResponseEntity<Card> addCard(@PathVariable("title") String title, @PathVariable("listId") long listId) {
        Integer maxPosition = repo.findMaxPositionByListId(listId);

        int newPosition = maxPosition == null ? 1 : maxPosition + 1;

        Card newCard = new Card(title, listId);
        newCard.setPosition(newPosition);

        Card saved = repo.save(newCard);
        return ResponseEntity.ok(saved);
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
        Optional<Card> optionalCard = repo.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setTitle(title);
            repo.save(card);
            return ResponseEntity.ok("Card title updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**Change the listId of a card, if it exists. Receive a message on the success of the edit
     * @param cardId The ID of the card whose title should be changed
     * @param listId listId which should replace the old listId of the card
     * @return receive a message indicating the listId has change, if the card exists. If it doesn't, receive an
     * appropriate response to the client.
     */
    @PostMapping("/editList/{cardId}/{listId}")
    public ResponseEntity<String> editCardList(@PathVariable("cardId") long cardId, @PathVariable("listId") long listId)
    {
        Optional<Card> optionalCard = repo.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setListId(listId);
            repo.save(card);
            return ResponseEntity.ok("Card list updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
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
        Optional<Card> cardToDeleteOptional = repo.findById(cardId);

        if (cardToDeleteOptional.isPresent()) {
            Card cardToDelete = cardToDeleteOptional.get();
            long listId = cardToDelete.getListId();
            Integer position = cardToDelete.getPosition();

            // Delete the card
            repo.deleteById(cardId);

            // Decrement the positions of all cards in front of the deleted card
            if(position!=null){
                List<Card> cardsToUpdate = repo.findByListIdAndPositionGreaterThan(listId, position);
                for (Card cardToUpdate : cardsToUpdate) {
                    int currentPosition = cardToUpdate.getPosition();
                    cardToUpdate.setPosition(currentPosition - 1);
                    repo.save(cardToUpdate);
                }
            }
            return ResponseEntity.ok("Card deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param listId id of the list of which all cards should be retrieved
     * @return a list of cards which all have the same listId corresponding to the input, ordered by position
     */
    @GetMapping("/getByListId/{listId}")
    @ResponseBody public List<Card> getCardByListId(@PathVariable("listId") long listId) {
        List<Card> cards = repo.findAll(Sort.by(Sort.Direction.ASC, "position"));
        List<Card> cardsOnList = new LinkedList<>();

        if(cards!=null){
            for (Card c : cards) {
                if (c.getListId() == listId) {
                    cardsOnList.add(c);
                }
            }
        }
        return cardsOnList;
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
        Optional<Card> optionalCard = repo.findById(cardId);

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
        return repo.findAll();
    }


}
