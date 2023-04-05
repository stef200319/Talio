package server.services;

import commons.BoardTag;
import org.springframework.stereotype.Service;
import server.database.BoardTagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardTagService {
    private final BoardTagRepository boardTagRepository;

    /**
     * @param boardTagRepository the table which contains all the board tags
     */
    public BoardTagService(BoardTagRepository boardTagRepository){

        this.boardTagRepository = boardTagRepository;

    }

    /**
     * @return a list of all the board tags
     */
    public List<BoardTag> getAll(){
        return boardTagRepository.findAll();
    }

    /**
     * method that checks if a board tag exists
     * @param id of the board tag
     * @return true if it exists, false if it does not exist
     */
    public boolean existsById(long id)
    {
        return boardTagRepository.existsById(id);
    }

    /**
     * gets the board tag that has the specified id
     * @param id of the board tag
     * @return the board tag or null if it does not exist
     */
    public BoardTag getById(long id){
        Optional<BoardTag> boardTag = boardTagRepository.findById(id);
        return boardTag.orElse(null);
    }

    /**
     * method that adds a board tag
     * @param title of the board tag
     * @param color of the board tag
     * @return the new board tag
     */
    public BoardTag add(String title, String color)
    {
        BoardTag boardTag = new BoardTag(title, color);
        return save(boardTag);

    }

    /**
     * method that edits the title of a board tag
     * @param id of the board tag
     * @param title the new title
     * @return the updated board tag
     */
    public BoardTag editTitle(long id, String title)
    {
        if(!boardTagRepository.existsById(id))
            return null;
        BoardTag boardTag = getById(id);
        boardTag.setTitle(title);
        return save(boardTag);
    }

    /**
     * method that edits the color of a board tag
     * @param id of the board tag
     * @param color the new color
     * @return the updated board tag
     */
    public BoardTag editColor(long id, String color)
    {
        if(!boardTagRepository.existsById(id))
            return null;
        BoardTag boardTag = getById(id);
        boardTag.setColor(color);
        return save(boardTag);
    }

    /**
     * deletes a board tag
     * @param id of the board tag
     * @return the board tag that was deleted
     */
    public BoardTag delete(long id)
    {
        if(!boardTagRepository.existsById(id))
            return null;
        BoardTag boardTag = getById(id);
        boardTagRepository.deleteById(id);
        return boardTag;
    }

    /**
     * method that saves a board tag to the database
     * @param boardTag to be saved
     * @return the saved board tag
     */
    public BoardTag save(BoardTag boardTag)
    {
        return boardTagRepository.save(boardTag);
    }
}