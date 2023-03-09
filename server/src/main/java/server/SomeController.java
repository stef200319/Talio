package server;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;

@Controller
@RequestMapping("/some")
public class SomeController {
    private final CardRepository repo;

    /**
     * @param repo the created card
     */
    public SomeController(CardRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/")
    @ResponseBody public String index() {return "Hello world!";}

    /**
     * @param title Of the card
     * @param listId the list on which the card needs to be
     * @return if succesfull, the method returns an ok
     */
    @PostMapping("/card/{title}/{listId}")
    public ResponseEntity<Card> addTitle(@PathVariable("title") String title, @PathVariable("listId") long listId) {
        Card newCard = new Card(title, listId);

        Card saved = repo.save(newCard);
        return ResponseEntity.ok(saved);
    }

    /**
     * @return all the cards in the database
     */
    @GetMapping("/allCards")
    @ResponseBody
    public List<Card> getAllCards() {
        return repo.findAll();
    }
}
