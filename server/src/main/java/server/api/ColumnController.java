package server.api;

import commons.Card;
import commons.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ColumnRepository;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/column")
public class ColumnController {

    private final CardController cardController;

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    /**
     * @param columnRepository the data container which includes all the columns
     * @param boardRepository the repository of the board -> used for checking whether boardId exists
     */
    public ColumnController(ColumnRepository columnRepository, BoardRepository boardRepository,
                            CardRepository cardRepository, CardController cardController) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
        this.cardController = cardController;
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable String title,
                                                        @PathVariable long boardId) {
        Column newColumn = new Column(title, boardId);

        if (boardRepository.existsById(boardId)) {
            Column saved = columnRepository.save(newColumn);
            return ResponseEntity.ok(saved);
        }

        return ResponseEntity.notFound().build();

    }

    /**
     * @param id the id of the column that needs to be removed
     * @return a response which says that the column was removed from the database or not.
     */
    @DeleteMapping("/deleteColumn/{id}")
    @ResponseBody public ResponseEntity<String> removeColumn(@PathVariable long id) {
        if (columnRepository.existsById(id)) {
            Column l = columnRepository.getById(id);
            columnRepository.delete(l);

            List<Card> cards = getCardsByColumnId(id);
            for (Card card : cards) {
                cardController.deleteCard(card.getId());
            }

            return ResponseEntity.ok("Column deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @param id the id of the column which should be updated
     * @param title the new title of the column
     * @return whether the column was successfully updated
     */
    @PutMapping("/editTitle/{id}/{title}")
    @ResponseBody public ResponseEntity<String> editColumn(@PathVariable long id,
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
     * @param id the id of the column which will be retrieved
     * @return the column according to the input id
     */
    @GetMapping("/getByColumnId/{id}")
    @ResponseBody public ResponseEntity<Column> getColumnByID(@PathVariable long id) {
        if (columnRepository.existsById(id)) {
            Column l = columnRepository.getById(id);
            return ResponseEntity.ok(l);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @return all columns in the database
     */
    @GetMapping("/getAllColumns")
    @ResponseBody public ResponseEntity<List<Column>> getAllColumns() {
        List<Column> columns = columnRepository.findAll();

        if (columns.size() > 0) {
            return ResponseEntity.ok(columns);
        }

        return ResponseEntity.notFound().build();
    }


    /**
     * @param columnId id of the column of which all cards should be retrieved
     * @return a list of cards which all have the same columnId corresponding to the input, ordered by position
     */
    @GetMapping("/getCardsByColumnId/{columnId}")
    @ResponseBody public List<Card> getCardsByColumnId(@PathVariable("columnId") long columnId) {
        List<Card> cards = cardRepository.findAll(Sort.by(Sort.Direction.ASC, "position"));
        List<Card> cardsOnColumn = new LinkedList<>();


        for (Card c : cards) {
            if (c.getColumnId() == columnId) {
                cardsOnColumn.add(c);
            }
        }

        return cardsOnColumn;
    }
}
