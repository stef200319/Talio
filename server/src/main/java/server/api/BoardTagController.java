package server.api;

import commons.Board;
import commons.BoardTag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.BoardService;
import server.services.BoardTagService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/boardTag")
public class BoardTagController {

    private final BoardService boardService;
    private final BoardTagService boardTagService;
    //for long polling
    private Map<Object, Consumer<BoardTag>> listeners = new HashMap<>();

    /**
     * Constructor for BoardTagController
     * @param boardTagService
     * @param boardService
     */
    public BoardTagController(BoardTagService boardTagService, BoardService boardService) {
        this.boardTagService = boardTagService;
        this.boardService = boardService;
    }

    /**
     * Adds a boardTag to the available boardTags
     * @param title title of the new boardTag
     * @param color color of the new boardTag
     * @return the created boardTag
     */
    @PostMapping("/addBoardTag/{title}/{color}")
    @ResponseBody
    public ResponseEntity<BoardTag> addBoardTag(@PathVariable("title") String title,
                                                @PathVariable("color") String color) {
        if (color == null || title == null) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagService.add(title, color);
        return ResponseEntity.ok(boardTag);
    }

    /**
     * deletes a boardTag from the available boardTags
     * @param boardTagId the boardTagId to delete
     * @return the deleted boardTag
     */
    @DeleteMapping("deleteBoardTag/{boardTagId}")
    @ResponseBody public ResponseEntity<BoardTag> deleteBoardTag(@PathVariable("boardTagId") long boardTagId) {
        if (!boardTagService.existsById(boardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagService.delete(boardTagId);
        deleteBoardTagFromBoards(boardTag);
        return ResponseEntity.ok(boardTag);
    }

    /**
     * gets all the board tags
     * @return a list of all the board tags
     */
    @GetMapping("/getAllBoardTags")
    @ResponseBody public ResponseEntity<List<BoardTag>> getAllBoardTags(){
        return ResponseEntity.ok(boardTagService.getAll());
    }
    /**
     * adds a boardTag to a board
     * @param boardTagId
     * @param boardId
     * @return the added boardTag
     */
    @PostMapping("/addBoardTagToBoard/{boardTagId}/{boardId}")
    @ResponseBody public ResponseEntity<BoardTag> addBoardTagToBoard(@PathVariable("boardTagId") long boardTagId,
                                                                  @PathVariable("boardId") long boardId) {
        if (!boardTagService.existsById(boardTagId) || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagService.getById(boardTagId);
        Board board = boardService.getByBoardId(boardId);
        board.addBoardTag(boardTag);
        boardService.save(board);

        listeners.forEach((k, l) -> l.accept(boardTag));

        return ResponseEntity.ok(boardTag);
    }

    /**
     * deletes a boardTag from a board
     * @param boardTagId
     * @param boardId
     * @return the deleted boardTag
     */
    @DeleteMapping("/deleteBoardTagFromBoard/{boardTagId}/{boardId}")
    @ResponseBody public ResponseEntity<BoardTag> deleteBoardTagFromBoard(@PathVariable("boardTagId") long boardTagId,
                                                                       @PathVariable("boardId") long boardId) {
        if (!boardTagService.existsById(boardTagId) || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagService.getById(boardTagId);
        Board board = boardService.getByBoardId(boardId);
        board.deleteBoardTag(boardTag);
        boardService.save(board);

        listeners.forEach((k, l) -> l.accept(boardTag));

        return ResponseEntity.ok(boardTag);
    }

    /**
     * Edits the color of a boardTag
     * @param boardTagId
     * @param color
     * @return the edited boardTag
     */
    @PutMapping("/editBoardTagColor/{boardTagId}/{color}")
    @ResponseBody public ResponseEntity<BoardTag> editBoardTagColor(@PathVariable("boardTagId") long boardTagId,
                                                                  @PathVariable("color") String color) {
        if (!boardTagService.existsById((boardTagId))) {
            return ResponseEntity.badRequest().build();
        }

        listeners.forEach((k, l) -> l.accept(boardTagService.getById(boardTagId)));

        BoardTag boardTag = boardTagService.editColor(boardTagId, color);
        return ResponseEntity.ok(boardTag);
    }

    /**
     * Edits the title of a boardTag
     * @param boardTagId
     * @param title
     * @return the edited boardTag
     */
    @PutMapping("/editBoardTagTitle/{boardTagId}/{title}")
    @ResponseBody public ResponseEntity<BoardTag> editBoardTagTitle(@PathVariable("boardTagId") long boardTagId,
                                                                  @PathVariable("title") String title) {
        if (!boardTagService.existsById(boardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagService.editTitle(boardTagId, title);
        return ResponseEntity.ok(boardTag);
    }


    /**
     * deletes the boardTags from boards given a specific boardTag
     * @param boardTag
     */
    public void deleteBoardTagFromBoards(BoardTag boardTag) {
        List<Board> boards = boardService.getAll();
        for (Board board : boards) {
            if (board.getBoardTags().contains(boardTag)) {
                board.deleteBoardTag(boardTag);
                boardService.save(board);
            }
        }
    }

    /**
     * Retrieves updates for the board.
     *
     * @return a DeferredResult containing a ResponseEntity with the board updates
     * or a NO_CONTENT response if no updates are available
     *
     * @throws Exception if an error occurs while processing the request
     */
    @GetMapping("/updates")
    @ResponseBody public DeferredResult<ResponseEntity<BoardTag>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<BoardTag>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, b -> {
            res.setResult(ResponseEntity.ok(b));
        });

        res.onCompletion(() -> {
            listeners.remove(key);
        });

        return res;
    }

    /**
     * Updates the given board tag and sends it to the "/topic/updateBoardTag" topic.
     *
     * @param boardTag the board tag to be updated
     *
     * @return the updated board tag
     */
    @MessageMapping("/updateBoardTag")
    @SendTo("/topic/updateBoardTag")
    public BoardTag updateBoardTag(BoardTag boardTag) {
        return boardTag;
    }

}
