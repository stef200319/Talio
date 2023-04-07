package server.services;

import commons.Board;
import commons.CardTag;
import org.springframework.stereotype.Service;
import server.database.CardTagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardTagService {
    private final CardTagRepository cardTagRepository;

    /**
     * @param cardTagRepository the table which contains all the card tags
     */
    public CardTagService(CardTagRepository cardTagRepository){

        this.cardTagRepository = cardTagRepository;

    }

    /**
     * @return a list of all the card tags
     */
    public List<CardTag> getAll(){
        return cardTagRepository.findAll();
    }

    /**
     * method that gets all the card tags of a board
     * @param board the specified board
     * @return  a list of all the card tags of that board
     */
    public List<CardTag> getAllByBoard(Board board)
    {
        return cardTagRepository.findCardTagsByBoard(board);
    }

    /**
     * method that checks if a card tag exists in the database
     * @param id of the card tag
     * @return true if it exists, false if it does not
     */
    public boolean existsById(long id)
    {
        return cardTagRepository.existsById(id);
    }

    /**
     * methods that gets a card tag by its id
     * @param id of the card tag
     * @return the card tag, or null if it is not found
     */
    public CardTag getById(long id){
        Optional<CardTag> cardTag = cardTagRepository.findById(id);
        return cardTag.orElse(null);
    }

    /**
     * add a card tag to the database
     * @param title of the card tag
     * @param color of the card tag
     * @param board where the card tag is added
     * @return the added card tag
     */
    public CardTag add(String title, String color, Board board)
    {
        CardTag cardTag = new CardTag(title, color, board);
        return save(cardTag);

    }

    /**
     * method that edits the title of a card tag
     * @param cardTagId of the card tag
     * @param title the new title
     * @return the updated card tag
     */
    public CardTag editTitle(long cardTagId, String title)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTag.setTitle(title);
        return save(cardTag);
    }

    /**
     * method that edits the color of a card tag
     * @param cardTagId of the card tag
     * @param color the new color
     * @return the updated card tag
     */
    public CardTag editColor(long cardTagId, String color)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTag.setColor(color);
        return save(cardTag);
    }

    /**
     * delete a card tag from the database
     * @param cardTagId of the card tag
     * @return the deleted card tag
     */
    public CardTag delete(long cardTagId)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTagRepository.deleteById(cardTagId);
        return cardTag;
    }

    /**
     * method that saves a card tag in the database
     * @param cardTag the card tag to be saved
     * @return the saved card tag
     */
    public CardTag save(CardTag cardTag)
    {
        return cardTagRepository.save(cardTag);
    }

    /**
     * finds all the cardTags given a board
     * @param board
     * @return list of cardtags
     */
    public List<CardTag> findCardTagsByBoard(Board board) {
        List<CardTag> cardTags = cardTagRepository.findCardTagsByBoard(board);
        if (cardTags == null) return new ArrayList<>();
        return cardTags;
    }

}