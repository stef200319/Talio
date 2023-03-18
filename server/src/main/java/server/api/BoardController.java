package server.api;

import commons.Board;

import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ColumnRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    private final ColumnController columnController;


    /**
     * @param boardRepository the repository which contains all the data in the database
     */
    public BoardController(BoardRepository boardRepository, ColumnRepository columnRepository,
                           ColumnController columnController) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
        this.columnController = columnController;
    }

    /**
     * @return all boards which are currently in the database
     */
    @GetMapping("/getAllBoards")
    @ResponseBody public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    /**
     * @param boardId the id of the board that the user is searching for
     * @return the board which corresponds to the id which was provided.
     */
    @GetMapping("/getByBoardId/{boardId}")
    @ResponseBody public Board getBoardById(@PathVariable Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        return optionalBoard.orElse(null);
    }

    /**
     * @param title of the new board
     * @return responseEntity which contains information whether and what was added to the db
     */
    @PostMapping("/addBoard/{title}")
    @ResponseBody public ResponseEntity<Board> addBoard(@PathVariable String title) {
        if (title != null && !title.equals("")) {
            Board newBoard = new Board(title);
            Board b = boardRepository.save(newBoard);

            return ResponseEntity.ok(b);
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * @param title the new title of the board
     * @param boardId the board id of the board which needs editing
     * @return whether the board was updated correctly
     */
    @PutMapping("/putBoard/{title}/{boardId}")
    @ResponseBody public ResponseEntity<String> putBoard(@PathVariable String title,
                                                        @PathVariable long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setTitle(title);
            boardRepository.save(board);
            return ResponseEntity.ok("Board title updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * @param boardId the board id of the board which needs to be deleted
     * @return whether the deletion was successful
     */
    @DeleteMapping("/deleteBoard/{boardId}")
    @ResponseBody public ResponseEntity<String> deleteBoard(@PathVariable long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            boardRepository.delete(optionalBoard.get());

            List<Column> columnsToDelete = getColumnsByBoardId(boardId);
            for (Column column : columnsToDelete) {
                columnController.removeColumn(column.getId());
            }

            return ResponseEntity.ok("Board deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Returns all the columns given a certain boardId
     * @param boardId the Id of the board you want the columns from
     * @return ResponseEntity with either
     */
    @GetMapping("/getColumnsByBoardId/{boardId}")
    @ResponseBody public List<Column> getColumnsByBoardId(@PathVariable long boardId) {
        List<Column> columns = columnRepository.findColumnsByBoardId(boardId);
        return columns;
    }

}
