package server.api;

import commons.Board;
import commons.CardTag;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.BoardService;
import server.services.CardService;
import server.services.CardTagService;
import server.services.ColumnService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ColumnService columnService;


    private final CardTagService cardTagService;
//

    private final CardService cardService;

    private final CardTagController cardTagController;


    /**
     * @param boardService the service used for the operations which use the board data access object
     * @param columnService the service used for the operations which use the column data access object
     * @param cardTagService
     * @param cardService
     * @param cardTagController
     */
    public BoardController(BoardService boardService, ColumnService columnService, CardTagService cardTagService,
                           CardService cardService, CardTagController cardTagController) {

        this.boardService = boardService;
        this.columnService = columnService;
        this.cardTagService = cardTagService;
        this.cardService = cardService;
        this.cardTagController = cardTagController;
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


    /**Change the Background colour of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param bgColour Background colour which should replace the old Background colour of the Board
     * @return receive a message indicating the Background colour has change, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardCenterColour/{boardId}/{bgColour}")
    @ResponseBody public ResponseEntity<Board> editBoardCenterColour(@PathVariable("boardId") long boardId,
                                                                           @PathVariable("bgColour") String bgColour){
        if (bgColour == null || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setCenterColour(bgColour);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }


    /**Change the Border colour of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param borderColour Border colour which should replace the old Border colour of the Board
     * @return receive a message indicating the Border colour has changed, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardSideColour/{boardId}/{borderColour}")
    @ResponseBody public ResponseEntity<Board> editBoardSideColour
    (@PathVariable("boardId") long boardId, @PathVariable("borderColour") String borderColour){
        if (borderColour == null || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setSideColour(borderColour);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }


    /**Change the Font-type of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param fontType Font Type which should replace the old Font Type of the Board
     * @return receive a message indicating the Font Type has changed, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardFontType/{boardId}/{fontType}")
    @ResponseBody public ResponseEntity<Board> editBoardFontType
    (@PathVariable("boardId") long boardId, @PathVariable("fontType") String fontType){
        if (fontType == null || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setFontType(fontType);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }

    /**Change the Boldness of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param bold Boldness which should replace the old Boldness of the Board
     * @return receive a message indicating the Boldness has changed, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardFontStyleBold/{boardId}/{bold}")
    @ResponseBody public ResponseEntity<Board> editBoardFontStyleBold
    (@PathVariable("boardId") long boardId, @PathVariable("bold") boolean bold){
        if (!boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setFontStyleBold(bold);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }


    /**Change the Italicness of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param italic Italicness which should replace the old Boldness of the Board
     * @return receive a message indicating the Italicness has changed, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardFontStyleItalic/{boardId}/{italic}")
    @ResponseBody public ResponseEntity<Board> editBoardFontStyleItalic
    (@PathVariable("boardId") long boardId, @PathVariable("italic") boolean italic){
        if (!boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setFontStyleItalic(italic);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }


    /**Change the Font Colour of a Board, if it exists. Receive a message on the success of the edit
     * @param boardId The ID of the Board whose title should be changed
     * @param fontColour Font Colour which should replace the old Font Colour of the Board
     * @return receive a message indicating the Font Colour has changed, if the Board exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editBoardFontColour/{boardId}/{fontColour}")
    @ResponseBody public ResponseEntity<Board> editBoardFontColour
    (@PathVariable("boardId") long boardId, @PathVariable("fontColour") String fontColour){
        if (!boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);
        board.setFontColour(fontColour);
        boardService.save(board);
        return ResponseEntity.ok(board);
    }

    /**
     * @param boardId the board id of the board which needs to be deleted
     * @return whether the deletion was successful
     */
    @DeleteMapping("/deleteBoard/{boardId}")
    @ResponseBody public ResponseEntity<Board> deleteBoard(@PathVariable("boardId") long boardId) {
        if (!boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Board board = boardService.getByBoardId(boardId);

        // Delete the corresponding boardTags and cardTags
        board.setBoardTags(new HashSet<>());
        List<CardTag> tags = cardTagService.findCardTagsByBoard(board);

        if (tags != null) {
            for (CardTag cardTag : tags) {
                cardTagController.deleteCardTagFromBoard(cardTag.getId());
            }
        }

        // Delete corresponding columns
        List<Column> columnsToDelete = columnService.getByBoardId(boardId);

        for (Column column : columnsToDelete) {
            columnService.delete(column);
        }

        Board deletedBoard = boardService.delete(boardId);


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

    /**
     * Gets a board by a CardId
     * @param cardId
     * @return the board
     */
    @GetMapping("getBoardByCardId/{cardId}")
    @ResponseBody public ResponseEntity<Board> getBoardByCardId(@PathVariable("cardId") long cardId) {
        if (!cardService.existsById(cardId)) return ResponseEntity.badRequest().build();

        Long columnId = cardService.getById(cardId).getColumnId();
        Long boardId = columnService.getById(columnId).getBoardId();
        Board board = boardService.getByBoardId(boardId);
        return ResponseEntity.ok(board);
    }


}
