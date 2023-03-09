package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;
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
    @PostMapping("/add/{title}/{listId}")
    public ResponseEntity<Card> addCard(@PathVariable("title") String title, @PathVariable("listId") long listId) {
        Card newCard = new Card(title, listId);

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
    public ResponseEntity<String> editCardTitle(@PathVariable("cardId") long cardId, @PathVariable("title") String title) {
        Optional<Card> optionalCard = repo.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.title=title;
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
    public ResponseEntity<String> editCardList(@PathVariable("cardId") long cardId, @PathVariable("listId") long listId) {
        Optional<Card> optionalCard = repo.findById(cardId);

        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.listId=listId;
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
    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardId") long cardId){
        if (repo.existsById(cardId)) {
            repo.deleteById(cardId);
            return ResponseEntity.ok("Card list deleted successfully");
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
    @GetMapping("/get/{cardId}")
    @ResponseBody
    public Card getCard(@PathVariable("cardId") long cardId) {
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
    @GetMapping("/getAll")
    @ResponseBody
    public List<Card> getAllCards() {
        return repo.findAll();
    }



}
