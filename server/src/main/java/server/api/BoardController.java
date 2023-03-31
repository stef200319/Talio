package server.api;

import commons.Board;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.BoardService;
import server.services.ColumnService;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ColumnService columnService;


    /**
     * @param boardService the service used for the operations which use the board data access object
     * @param columnService the service used for the operations which use the column data access object
     */
    public BoardController(BoardService boardService, ColumnService columnService) {
        this.boardService = boardService;
        this.columnService = columnService;
    }

    /**
     * @return all boards which are currently in the database
     */
    @GetMapping("/getAllBoards")
    @ResponseBody public List<Board> getAllBoards() {
        return boardService.getAll();
    }


    /**
     * @param boardId the id of the board that the user is searching for
     * @return the board which corresponds to the id which was provided.
     */
    @GetMapping("/getBoardByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<Board> getBoardByBoardId(@PathVariable("boardId") Long boardId) {


        Board board = boardService.getByBoardId(boardId);
        return board == null? ResponseEntity.notFound().build() : ResponseEntity.ok(board);
    }

    /**
     * @param title of the new board
     * @return responseEntity which contains information whether and what was added to the db
     */
    @PostMapping("/addBoard/{title}")
    @ResponseBody public ResponseEntity<Board> addBoard(@PathVariable("title") String title) {
        Board board = boardService.add(title);
        return board == null?ResponseEntity.badRequest().build() : ResponseEntity.ok(board);
    }

    /**
     * @param title the new title of the board
     * @param boardId the board id of the board which needs editing
     * @return whether the board was updated correctly
     */
    @PutMapping("/editBoardTitle/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Board> editBoardTitle(@PathVariable("title") String title,
                                                              @PathVariable("boardId") long boardId) {
        Board board = boardService.editTitle(title, boardId);
        return board == null ? ResponseEntity.badRequest().build():ResponseEntity.ok(board);

    }

    /**
     * @param boardId the board id of the board which needs to be deleted
     * @return whether the deletion was successful
     */
    @DeleteMapping("/deleteBoard/{boardId}")
    @ResponseBody public ResponseEntity<Board> deleteBoard(@PathVariable("boardId") long boardId) {

        Board deletedBoard = boardService.delete(boardId);

        if (deletedBoard == null) {
            return ResponseEntity.badRequest().build();
        }

        // Delete corresponding columns
        List<Column> columnsToDelete = columnService.getByBoardId(boardId);

        for (Column column : columnsToDelete) {
            columnService.delete(column);
        }

        return ResponseEntity.ok(deletedBoard);
    }

    /**
     * Gives all the columns given a certain boardId
     * @param boardId the boardId you want the columns from
     * @return a List with all the corresponding columns
     */
    @GetMapping("/getColumnsByBoardId/{boardId}")
    @ResponseBody public ResponseEntity<List<Column>> getColumnsByBoardId(@PathVariable("boardId") long boardId) {
        if (!boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(columnService.getByBoardId(boardId));
    }

}
