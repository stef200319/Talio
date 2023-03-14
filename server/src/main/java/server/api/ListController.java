package server.api;

import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ColumnRepository;
import java.util.List;

@Controller
@RequestMapping("/list")
public class ListController {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    /**
     * @param columnRepository the data container which includes all the lists
     * @param boardRepository the repository of the board -> used for checking whether boardId exists
     */
    public ListController(ColumnRepository columnRepository, BoardRepository boardRepository) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the list belongs
     * @return A response entity of the saved list
     */
    @PostMapping("/addList/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addList(@PathVariable String title,
                                                        @PathVariable long boardId) {
        Column newColumn = new Column(title, boardId);

        if (boardRepository.existsById(boardId)) {
            Column saved = columnRepository.save(newColumn);
            return ResponseEntity.ok(saved);
        }

        return ResponseEntity.notFound().build();

    }

    /**
     * @param id the id of the list that needs to be removed
     * @return a response which says that the list was removed from the database or not.
     */
    @DeleteMapping("/deleteList/{id}")
    @ResponseBody public ResponseEntity<String> removeList(@PathVariable long id) {
        if (columnRepository.existsById(id)) {
            Column l = columnRepository.getById(id);
            columnRepository.delete(l);
            return ResponseEntity.ok("List deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @param id the id of the list which should be updated
     * @param title the new title of the list
     * @return whether the list was successfully updated
     */
    @PutMapping("/editTitle/{id}/{title}")
    @ResponseBody public ResponseEntity<String> editList(@PathVariable long id,
                                                       @PathVariable String title) {
        if (columnRepository.existsById(id)) {
            Column l = columnRepository.getById(id);
            l.setTitle(title);
            columnRepository.save(l);
            return ResponseEntity.ok("Card edited successfully");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @param id the id of the list which will be retrieved
     * @return the list according to the input id
     */
    @GetMapping("/getByListId/{id}")
    @ResponseBody public ResponseEntity<Column> getListByID(@PathVariable long id) {
        if (columnRepository.existsById(id)) {
            Column l = columnRepository.getById(id);
            return ResponseEntity.ok(l);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @return all lists in the database
     */
    @GetMapping("/getAllLists")
    @ResponseBody public ResponseEntity<List<Column>> getAllLists() {
        List<Column> columns = columnRepository.findAll();

        if (columns.size() > 0) {
            return ResponseEntity.ok(columns);
        }

        return ResponseEntity.notFound().build();
    }
}
