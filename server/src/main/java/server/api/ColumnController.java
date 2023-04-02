package server.api;

import commons.Card;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.BoardService;
import server.services.CardService;
import server.services.ColumnService;

import java.util.List;

@RestController
@RequestMapping("/column")
public class ColumnController {

    private final ColumnService columnService;
    private final BoardService boardService;
    private final CardService cardService;


    /**
     * @param columnService the service used for the operations which use the column data access object
     * @param boardService the service used for the operations which use the board data access object
     * @param cardService the service used for the operations which use the card data access object
     */
    public ColumnController(ColumnService columnService, BoardService boardService, CardService cardService) {
        this.columnService = columnService;
        this.boardService = boardService;
        this.cardService = cardService;
    }


    /**
     * @return all columns in the database
     */
    @GetMapping("/getAllColumns")
    @ResponseBody public ResponseEntity<List<Column>> getAllColumns() {
        List<Column> columns = columnService.getAll();
        return columns.size() > 0? ResponseEntity.ok(columns) : ResponseEntity.notFound().build();
    }


    /**
     * @param columnId the id of the column which will be retrieved
     * @return the column according to the input id
     */
    @GetMapping("/getColumnByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<Column> getColumnByColumnId(@PathVariable("columnId") long columnId) {
        Column column = columnService.getById(columnId);
        return column != null?ResponseEntity.ok(column) : ResponseEntity.notFound().build();
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable("title") String title,
                                                        @PathVariable("boardId") Long boardId) {
        if (title == null || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnService.add(title, boardId);
        return ResponseEntity.ok(column);
    }

    /**
     * @param columnId the id of the column which should be updated
     * @param title the new title of the column
     * @return whether the column was successfully updated
     */
    @PutMapping("/editColumnTitle/{columnId}/{title}")
    @ResponseBody public ResponseEntity<Column> editColumnTitle(@PathVariable("columnId") long columnId,
                                                                @PathVariable("title") String title) {
        if (!columnService.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnService.update(title, columnId);
        return ResponseEntity.ok(column);
    }

    /**
     * @param columnId the id of the column that needs to be removed
     * @return a response which says that the column was removed from the database or not.
     */
    @DeleteMapping("/deleteColumn/{columnId}")
    @ResponseBody public ResponseEntity<Column> deleteColumn(@PathVariable("columnId") long columnId) {

        Column deletedColumn = columnService.deleteByColumnId(columnId);

        if (deletedColumn == null) {
            return ResponseEntity.badRequest().build();
        }

        // Delete corresponding cards
        cardService.deleteByColumnId(columnId);

        return ResponseEntity.ok(deletedColumn);
    }

    /**
     * Gives all the cards given a certain columnId
     * @param columnId the columnId you want the cards from
     * @return a List with all the corresponding cards
     */
    @GetMapping("/getCardsByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<List<Card>> getCardsByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnService.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = cardService.getByColumnId(columnId);
        return ResponseEntity.ok(cards);
    }
}
