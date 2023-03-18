package server.api;

import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ColumnRepository;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/column")
public class ColumnController {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    /**
     * @param columnRepository the data container which includes all the columns
     * @param boardRepository the repository of the board -> used for checking whether boardId exists
     */
    public ColumnController(ColumnRepository columnRepository, BoardRepository boardRepository) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable String title,
                                                        @PathVariable Long boardId) {
        if (boardId!=null) {
            Integer maxPosition = columnRepository.findMaxPositionByBoardId(boardId);
            int newPosition = maxPosition == null ? 1 : maxPosition + 1;

            Column newColumn = new Column(title, boardId);
            newColumn.setPosition(newPosition);

            Column saved = columnRepository.save(newColumn);
            return ResponseEntity.ok(saved);
        }

        return ResponseEntity.notFound().build();

    }

    /**
     * @param columnId the id of the column that needs to be removed
     * @return a response which says that the column was removed from the database or not.
     */
    @DeleteMapping("/deleteColumn/{columnId}")
    @ResponseBody public ResponseEntity<String> removeColumn(@PathVariable("columnId") long columnId) {
        Optional<Column> columnToDeleteOptional = columnRepository.findById(columnId);

        if (columnToDeleteOptional.isPresent()) {
            Column columnToDelete = columnToDeleteOptional.get();
            long boardId = columnToDelete.getBoardId();
            Integer position = columnToDelete.getPosition();

            // Delete the Column
            columnRepository.deleteById(columnId);

            // Decrement the positions of all Columns in front of the deleted Column
            if(position!=null){
                List<Column> columnsToUpdate = columnRepository.findByBoardIdAndPositionGreaterThan(boardId, position);
                for (Column columnToUpdate : columnsToUpdate) {
                    int currentPosition = columnToUpdate.getPosition();
                    columnToUpdate.setPosition(currentPosition - 1);
                    columnRepository.save(columnToUpdate);
                }
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
     * @param boardId the id of the board for which all columns need to be fetched
     * @return a column of all the columns on the board
     */
    @GetMapping("/getByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<List<Column>> getColumnByBoardId(@PathVariable long boardId) {
        List<Column> columns = columnRepository.findColumnsByBoardId(boardId);

        if (columns != null) {
            return ResponseEntity.ok(columns);
        }

        return ResponseEntity.notFound().build();
    }
}
