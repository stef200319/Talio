package server;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;

import java.util.List;

@Controller
@RequestMapping("/")
public class SomeController {
    private final CardRepository repo;
    public SomeController(CardRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/")
    @ResponseBody public String index() {return "Hello world!";}

    /*/////////////////////////*/
    @PostMapping("/card/{title}/{listId}")
    public ResponseEntity<Card> addTitle(@PathVariable("title") String title, @PathVariable("listId") long listId) {
        Card newCard = new Card(title, listId);

        Card saved = repo.save(newCard);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/allCards")
    @ResponseBody
    public List<Card> getAllCards() {
        return repo.findAll();
    }

    //////////////////////////
}
