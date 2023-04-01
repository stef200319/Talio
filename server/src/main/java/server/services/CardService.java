package server.services;

import commons.Card;
import org.springframework.stereotype.Service;
import server.database.CardRepository;

import java.util.List;

@Service
public class CardService {
    private CardRepository cardRepository;

    /**
     * @param cardRepository the table which contains all the cards
     */
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

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
}
