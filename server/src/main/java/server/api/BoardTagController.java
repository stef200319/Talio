package server.api;

import commons.Board;
import commons.BoardTag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.BoardTagRepository;
import server.services.BoardService;
import server.services.BoardTagService;

import java.util.List;

@RestController
@RequestMapping("/boardTag")
public class BoardTagController {

    private final BoardService boardService;
    private final BoardTagService boardTagService;

    /**
     * Constructor for BoardTagController
     * @param boardTagService
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

}
