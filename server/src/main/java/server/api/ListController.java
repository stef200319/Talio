package server.api;

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
    @PostMapping("/post/{boardId}/{title}")
    @ResponseBody public ResponseEntity<List> addList(@PathVariable String title,
                                                      @PathVariable long boardId) {
        List newList = new List(title, boardId);

        // todo check whether boardId is in the database -> Need the Board-controller
        // todo check max 10 entries

        List saved = listRepository.save(newList);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{ID}")
    @ResponseBody public ResponseEntity<String> removeList(@PathVariable long ID) {
        if (listRepository.existsById(ID)) {
            List l = listRepository.getById(ID);
            listRepository.delete(l);
            return ResponseEntity.ok("List deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/put/{ID}/{title}")
    @ResponseBody public ResponseEntity<String> editList(@PathVariable long ID,
                                                       @PathVariable String title) {
        if (listRepository.existsById(ID)) {
            List l = listRepository.getById(ID);
            l.setTitle(title);
            listRepository.save(l);
            return ResponseEntity.ok("Card edited successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/{ID}")
    @ResponseBody public ResponseEntity<List> getListByID(@PathVariable long ID) {
        if (listRepository.existsById(ID)) {
            List l = listRepository.getById(ID);
            return ResponseEntity.ok(l);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
    @ResponseBody public ResponseEntity<java.util.List<List>> getAllLists() {
        java.util.List<List> lists = listRepository.findAll();

        if (lists.size() > 0) {
            return ResponseEntity.ok(lists);
        }

        return ResponseEntity.notFound().build();
    }
}
