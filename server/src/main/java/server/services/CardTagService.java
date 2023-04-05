package server.services;

import commons.Board;
import commons.CardTag;
import org.springframework.stereotype.Service;
import server.database.CardTagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardTagService {
    private final CardTagRepository cardTagRepository;


    public CardTagService(CardTagRepository cardTagRepository){

        this.cardTagRepository = cardTagRepository;

    }

    public List<CardTag> getAll(){
        return cardTagRepository.findAll();
    }

    public boolean existsById(long id)
    {
        return cardTagRepository.existsById(id);
    }

    public CardTag getById(long id){
        Optional<CardTag> cardTag = cardTagRepository.findById(id);
        return cardTag.orElse(null);
    }

    public CardTag add(String title, String color, Board board)
    {
        CardTag cardTag = new CardTag(title, color, board);
        return save(cardTag);

    }
    public CardTag editTitle(long cardTagId, String title)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTag.setTitle(title);
        return save(cardTag);
    }
    public CardTag editColor(long cardTagId, String color)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTag.setColor(color);
        return save(cardTag);
    }
    public CardTag delete(long cardTagId)
    {
        if(!cardTagRepository.existsById(cardTagId))
            return null;
        CardTag cardTag = getById(cardTagId);
        cardTagRepository.deleteById(cardTagId);
        return cardTag;
    }
    public CardTag save(CardTag cardTag)
    {
        return cardTagRepository.save(cardTag);
    }
}