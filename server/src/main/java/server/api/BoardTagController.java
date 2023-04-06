package server.api;

import commons.Board;
import commons.BoardTag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.BoardTagRepository;

import java.util.List;

@RestController
@RequestMapping("/boardTag")
public class BoardTagController {

    private BoardTagRepository boardTagRepository;
    private BoardRepository boardRepository;

    /**
     * Constructor for BoardTagController
     * @param boardTagRepository
     * @param boardRepository
     */
    public BoardTagController(BoardTagRepository boardTagRepository, BoardRepository boardRepository) {
        this.boardTagRepository = boardTagRepository;
        this.boardRepository = boardRepository;
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

        BoardTag boardTag = new BoardTag(title, color);
        boardTagRepository.save(boardTag);
        return ResponseEntity.ok(boardTag);
    }

    /**
     * deletes a boardTag from the available boardTags
     * @param boardTagId the boardTagId to delete
     * @return the deleted boardTag
     */
    @DeleteMapping("deleteBoardTag/{boardTagId}")
    @ResponseBody public ResponseEntity<BoardTag> deleteBoardTag(@PathVariable("boardTagId") long boardTagId) {
        if (!boardTagRepository.existsById(boardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagRepository.findById(boardTagId).get();

        deleteBoardTagFromBoards(boardTag);

        boardTagRepository.deleteById(boardTagId);
        return ResponseEntity.ok(boardTag);
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
        if (!boardTagRepository.existsById(boardTagId) || !boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagRepository.findById(boardTagId).get();
        Board board = boardRepository.findById(boardId).get();
        board.addBoardTag(boardTag);
        boardRepository.save(board);
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
        if (!boardTagRepository.existsById(boardTagId) || !boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagRepository.findById(boardTagId).get();
        Board board = boardRepository.findById(boardId).get();
        board.deleteBoardTag(boardTag);
        boardRepository.save(board);
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
        if (!boardTagRepository.existsById((boardTagId))) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagRepository.findById(boardTagId).get();
        boardTag.setColor(color);
        boardTagRepository.save(boardTag);
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
        if (!boardTagRepository.existsById(boardTagId)) {
            return ResponseEntity.badRequest().build();
        }

        BoardTag boardTag = boardTagRepository.findById(boardTagId).get();
        boardTag.setTitle(title);
        boardTagRepository.save(boardTag);
        return ResponseEntity.ok(boardTag);
    }

    /**
     * Gets all the boardTags
     * @return list of boardTags
     */
    @GetMapping("/getAllBoardTags")
    @ResponseBody public ResponseEntity<List<BoardTag>> getAllBoardTags() {
        return ResponseEntity.ok(boardTagRepository.findAll());
    }


    /**
     * deletes the boardTags from boards given a specific boardTag
     * @param boardTag
     */
    public void deleteBoardTagFromBoards(BoardTag boardTag) {
        List<Board> boards = boardRepository.findAll();
        for (Board board : boards) {
            if (board.getBoardTags().contains(boardTag)) {
                board.deleteBoardTag(boardTag);
                boardRepository.save(board);
            }
        }
    }

}
