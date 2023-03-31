package server.services;

import commons.Card;
import org.springframework.stereotype.Service;
import server.database.CardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardRepository cardRepository;

    /**
     * @param cardRepository the table which contains all the cards
     */
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAll() {return cardRepository.findAll();}

    /**
     * @param columnId the id of the column for which we want to match all the cards
     * @return list of cards which correspond to the columnId
     */
    public List<Card> getByColumnId(long columnId) {
        return cardRepository.findCardsByColumnId(columnId);
    }

    /**
     * @param columnId the id of the column for which the cards will be matched
     * @return all the cards that are deleted
     */
    public List<Card> deleteByColumnId(long columnId) {
        List<Card> cards = cardRepository.findCardsByColumnId(columnId);
        cardRepository.deleteAll(cards);

        return cards;
    }

    public boolean existsById(long cardId) {
        return cardRepository.existsById(cardId);
    }

    public Card getByCardId(long cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        return card.orElse(null);
    }


}
