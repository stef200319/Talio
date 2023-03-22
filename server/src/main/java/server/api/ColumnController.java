package server.api;

import commons.Card;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ColumnRepository;

import java.util.List;

@Controller
@RequestMapping("/column")
public class ColumnController {

    private final CardController cardController;

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    /**
     *
     * @param columnRepository the data container which includes all the columns
     * @param boardRepository the repository of the board -> used for checking whether boardId exists
     * @param cardRepository the reopository of the cards
     * @param cardController the controller which controls all card crud operations
     */
    public ColumnController(ColumnRepository columnRepository, BoardRepository boardRepository,
                            CardRepository cardRepository, CardController cardController) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
        this.cardController = cardController;
    }

    // I think this should just return List<Column>
    /**
     * @return all columns in the database
     */
//    public List<Column> getAllColumns() {
//        return columnRepository.findAll();
//    }
    @GetMapping("/getAllColumns")
    @ResponseBody public ResponseEntity<List<Column>> getAllColumns() {
        List<Column> columns = columnRepository.findAll();

        if (columns.size() > 0) {
            return ResponseEntity.ok(columns);
        }

        return ResponseEntity.notFound().build();
    }


    /**
     * @param columnId the id of the column which will be retrieved
     * @return the column according to the input id
     */
    @GetMapping("/getColumnByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<Column> getColumnByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.notFound().build();
        }

        Column column = columnRepository.findById(columnId).get();
        return ResponseEntity.ok(column);

    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable("title") String title,
                                                        @PathVariable("boardId") Long boardId) {
        if (title == null || !boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Integer maxPosition = columnRepository.findMaxPositionByBoardId(boardId);
        int newPosition = maxPosition == null ? 1 : maxPosition + 1;

        Column newColumn = new Column(title, boardId);
        newColumn.setPosition(newPosition);

        Column saved = columnRepository.save(newColumn);
        return ResponseEntity.ok(saved);
    }

    /**
     * @param columnId the id of the column which should be updated
     * @param title the new title of the column
     * @return whether the column was successfully updated
     */
    @PutMapping("/editColumnTitle/{columnId}/{title}")
    @ResponseBody public ResponseEntity<Column> editColumnTitle(@PathVariable("columnId") long columnId,
                                                                @PathVariable("title") String title) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setTitle(title);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }

    /**
     * @param columnId the id of the column that needs to be removed
     * @return a response which says that the column was removed from the database or not.
     */
    @DeleteMapping("/deleteColumn/{columnId}")
    @ResponseBody public ResponseEntity<Column> deleteColumn(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column columnToDelete = columnRepository.findById(columnId).get();
        long boardId = columnToDelete.getBoardId();
        Integer position = columnToDelete.getPosition();

        // Delete corresponding cards
        List<Card> cards = getCardsByColumnId(columnId).getBody();

        for (Card card : cards) {
            cardController.deleteCard(card.getId());
        }

        // Delete the Column
        columnRepository.deleteById(columnId);

        // Decrement the positions of all Columns in front of the deleted Column
        if (position != null) {
            List<Column> columnsToUpdate = columnRepository.findByBoardIdAndPositionGreaterThan(boardId, position);
            for (Column columnToUpdate : columnsToUpdate) {
                int currentPosition = columnToUpdate.getPosition();
                columnToUpdate.setPosition(currentPosition - 1);
                columnRepository.save(columnToUpdate);
            }
        }
        return ResponseEntity.ok(columnToDelete);
    }

    /**
     * Gives all the cards given a certain columnId
     * @param columnId the columnId you want the cards from
     * @return a List with all the corresponding cards
     */
    @GetMapping("/getCardsByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<List<Card>> getCardsByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(cardRepository.findCardsByColumnId(columnId));
    }
}
