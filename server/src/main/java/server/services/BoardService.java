package server.services;

import commons.Board;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * @param boardRepository the data access model of the board
     */
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * @return all boards which are in the database
     */
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    /**
     * @param boardId the id of the board which we want to search
     * @return whether the board exists in the database
     */
    public boolean existsById(Long boardId) {
        return boardRepository.existsById(boardId);
    }

    /**
     * @param boardId the id of the board from which we want to retrieve the board
     * @return the board if it is in the database, else null.
     */
    public Board getByBoardId(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        return board.orElse(null);
    }

    /**
     * @param title the title of the new board, the string is not null or empty
     * @return the new board which is added to the database
     */
    public Board add(String title) {
        if (title == null || title.equals(""))
            return null;
        Board b = new Board(title);
        boardRepository.save(b);
        return b;
    }

    /**
     * @param title the new title of the board
     * @param boardId the board for which the title is updated
     * @return the new board, so how it is stored in the database
     */
    public Board editTitle(String title, long boardId) {
        if (!boardRepository.existsById(boardId))
            return null;

        Board board = boardRepository.findById(boardId).get();
        board.setTitle(title);
        return boardRepository.save(board);
    }

    /**
     * @param boardId the id of the board which needs to be deleted
     * @return the deleted board.
     */
    public Board delete(long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return null;
        }

        Board board = boardRepository.findById(boardId).get();
        boardRepository.delete(board);
        return board;
    }

    /**
     * Save board to repository
     * @param b board to save
     * @return board
     */
    public Board save(Board b) {
        return boardRepository.save(b);
    }




    /**Change the Center colour of a board
     * @param boardId The ID of the board whose title should be changed
     * @param bgColour Background colour which should replace the old Background colour of the board
     * @return the edited board saved to the database
     */

    public Board editCenterColour(long boardId, String bgColour){
        Board board = getByBoardId(boardId);
        board.setCenterColour(bgColour);
        boardRepository.save(board);
        return board;
    }


    /**Change the Side colour of a board.
     * @param boardId The ID of the board whose title should be changed
     * @param borderColour Border colour which should replace the old Border colour of the board
     * @return the edited board saved to the database
     */
    public Board editSideColour(long boardId, String borderColour){
        Board board = getByBoardId(boardId);
        board.setSideColour(borderColour);
        boardRepository.save(board);
        return board;
    }

    /**Change the Font-type of a board.
     * @param boardId The ID of the board whose title should be changed
     * @param fontType Font Type which should replace the old Font Type of the board
     * @return the edited board saved to the database
     */
    public Board editFontType(long boardId, String fontType){
        Board board = getByBoardId(boardId);
        board.setFontType(fontType);
        boardRepository.save(board);
        return board;
    }


    /**Change the Boldness of a board, if it exists.
     * @param boardId The ID of the board whose title should be changed
     * @param bold Boldness which should replace the old Boldness of the board
     * @return the edited board saved to the database
     */
    public Board editFontStyleBold(long boardId, boolean bold){
        Board board = getByBoardId(boardId);
        board.setFontStyleBold(bold);
        boardRepository.save(board);
        return board;
    }


    /**Change the Italicness of a board, if it exists.
     * @param boardId The ID of the board whose title should be changed
     * @param italic Italicness which should replace the old Boldness of the board
     * @return the edited board saved to the database
     */
    public Board editFontStyleItalic(long boardId, boolean italic){
        Board board = getByBoardId(boardId);
        board.setFontStyleItalic(italic);
        boardRepository.save(board);
        return board;
    }


    /**Change the Font Colour of a board, if it exists.
     * @param boardId The ID of the board whose title should be changed
     * @param fontColour Font Colour which should replace the old Font Colour of the board
     * @return the edited board saved to the database
     */
    public Board editFontColour(long boardId, String fontColour){
        Board board = getByBoardId(boardId);
        board.setFontColour(fontColour);
        boardRepository.save(board);
        return board;
    }

}
