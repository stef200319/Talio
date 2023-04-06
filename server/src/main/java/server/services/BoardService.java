package server.services;

import commons.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import server.component.RESTEvent;
import server.database.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
        return boardRepository.save(new Board(title));
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

        RESTEvent event = new RESTEvent(board, "board was edited");
        applicationEventPublisher.publishEvent(event);

        return save(board);
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

        RESTEvent event = new RESTEvent(board, "board was deleted");
        applicationEventPublisher.publishEvent(event);

        return board;
    }

    /**
     * @param applicationEventPublisher The new application event publisher
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * method that saves a board in the database
     * @param board the board to be saved
     * @return the saved board
     */
    public Board save(Board board) {
        return boardRepository.save(board);
    }
}
