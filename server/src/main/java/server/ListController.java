package server;

import commons.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.ListRepository;

@Controller
@RequestMapping("/list")
public class ListController {

    private final ListRepository listRepository;

    /**
     * @param listRepository the data container which includes all the lists
     */
    public ListController(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the list belongs
     * @return A response entity of the saved list
     */
    @GetMapping("/add/{title}/{boardId}")
    @ResponseBody public ResponseEntity<List> addList(@PathVariable String title,
                                                      @PathVariable long boardId) {
        List newList = new List(title, boardId);

        // todo check whether boardId is in the database -> Need the Board-controller
        // todo check max 10 entries

        List saved = listRepository.save(newList);
        return ResponseEntity.ok(saved);
    }
}
