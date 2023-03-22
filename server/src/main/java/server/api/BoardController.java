package server.api;

import commons.Board;

import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ColumnRepository;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    private final ColumnController columnController;

    /**
     *
     * @param boardRepository the repository which contains all the data in the database of the boards
     * @param columnRepository the repository which contains all the data in the database of the columns
     * @param columnController the controller which handles all the logic for the columns in the db
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
//    public List<Board> getAllBoards() {
//        return boardRepository.findAll();
//    }
    @GetMapping("/getAllBoards")
    @ResponseBody public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }


    /**
     * @param boardId the id of the board that the user is searching for
     * @return the board which corresponds to the id which was provided.
     */
    @GetMapping("/getBoardByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<Board> getBoardByBoardId(@PathVariable("boardId") Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.notFound().build();
        }

        Board board = boardRepository.findById(boardId).get();
        return ResponseEntity.ok(board);
    }

    /**
     * @param title of the new board
     * @return responseEntity which contains information whether and what was added to the db
     */
    @PostMapping("/addBoard/{title}")
    @ResponseBody public ResponseEntity<Board> addBoard(@PathVariable("title") String title) {
        if (title == null) {
            return ResponseEntity.badRequest().build();

        }

        Board newBoard = new Board(title);
        boardRepository.save(newBoard);

        return ResponseEntity.ok(newBoard);
    }

    /**
     * @param title the new title of the board
     * @param boardId the board id of the board which needs editing
     * @return whether the board was updated correctly
     */
    @PutMapping("/editBoardTitle/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Board> editBoardTitle(@PathVariable("title") String title,
                                                              @PathVariable("boardId") long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardRepository.findById(boardId).get();
        board.setTitle(title);
        boardRepository.save(board);
        return ResponseEntity.ok(board);
    }

    /**
     * @param boardId the board id of the board which needs to be deleted
     * @return whether the deletion was successful
     */
    @DeleteMapping("/deleteBoard/{boardId}")
    @ResponseBody public ResponseEntity<Board> deleteBoard(@PathVariable("boardId") long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardRepository.findById(boardId).get();

        // Delete corresponding columns
        List<Column> columnsToDelete = getColumnsByBoardId(boardId).getBody();

        for (Column column : columnsToDelete) {
            columnController.deleteColumn(column.getId());
        }

        boardRepository.delete(board);

        return ResponseEntity.ok(board);
    }

    /**
     * Gives all the columns given a certain boardId
     * @param boardId the boardId you want the columns from
     * @return a List with all the corresponding columns
     */
    @GetMapping("/getColumnsByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<List<Column>> getColumnsByBoardId(@PathVariable("boardId") long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(columnRepository.findColumnsByBoardId(boardId));
    }

}
