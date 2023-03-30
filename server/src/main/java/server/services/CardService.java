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
     * @param columnId The id of the column we're checking the existence of
     * @return true if it exists, false otherwise
     */
    public boolean existsById(long columnId) {
        return cardRepository.existsById(columnId);
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

    /**
     * Finds all the cards that exist in the database
     * @return a list of all the cards
     */
    public List<Card> getAll() {
        if(cardRepository==null)
            return null;
        return cardRepository.findAll();
    }

    /**
     * Finds the card with the specified id
     * @param cardId id of the card to search for
     * @return The card with id cardId
     */
    public Card getById(long cardId) {
        if(!existsById(cardId))
            return null;
        return cardRepository.findById(cardId).get();
    }

    /**
     * Creates and adds a new card to the repository
     * @param title title of the new card
     * @param columnId column in which to add teh card
     * @return the newly created card
     */
    public Card addCard(String title, long columnId) {
        Integer maxPos = cardRepository.findMaxPositionByColumnId(columnId);
        int newPosition = maxPos == null ? 1 : maxPos+1;

        Card card = new Card(title,columnId);
        card.setPosition(newPosition);

        return cardRepository.save(card);
    }

    /**
     * Saves a card to the database
     * @param c card to save
     * @return newly saved card
     */
    public Card saveCard(Card c) {
        return cardRepository.save(c);
    }

    /**
     * Updates the title of the card
     * @param title New title of the card
     * @param cardId id of the card to be updated
     * @return the new card in the database
     */
    public Card update(String title, long cardId) {
        Card card = getById(cardId);
        card.setTitle(title);
        return cardRepository.save(card);
    }

    /**
     * Change the description of a card
     * @param cardId the id of the card to be changed
     * @param description new description for the card
     * @return the new card saved to the database
     */
    public Card editDescription(long cardId, String description) {
        Card card = getById(cardId);
        card.setTitle(description);
        return cardRepository.save(card);
    }

    /**
     * Updates the column of a card and sets its position to last on the column
     * @param cardId The id of the card to be changed
     * @param columnId The new column of the card
     * @return The new card in the database
     */
    public Card editColumn(long cardId, long columnId) {
        if(!cardRepository.existsById(cardId))
            return null;
        Card card = getById(cardId);

        List<Card> cards = cardRepository.findByColumnIdAndPositionGreaterThan(card.getColumnId(),card.getPosition());
        for(Card c : cards) {
            int pos = c.getPosition();
            c.setPosition(pos - 1);
        }

        Integer maxPosition = cardRepository.findMaxPositionByColumnId(columnId);
        card.setPosition(maxPosition+1);

        card.setColumnId(columnId);
        return cardRepository.save(card);
    }

    /**
     * Helper method for changing the position of a card. It updates all the positions
     * of the affected cards
     * @param oldPos Old position of the card that was changed
     * @param newPos New position of the card that was changed
     * @param columnId id of the column where this happens
     * @return The new list with all the updated cards
     */
    public List<Card> changePositionsOfAffectedCards(int oldPos, int newPos, long columnId) {
        List <Card> cards;
        if(oldPos < newPos) {
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, oldPos);
            for(Card c : cards) {
                int pos = c.getPosition();
                if(pos <= newPos && pos > oldPos)
                    c.setPosition(pos-1);
            }
        }
        else {
            cards = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, newPos-1);
            for(Card c : cards) {
                int pos = c.getPosition();
                if(pos >= newPos && pos < oldPos)
                    c.setPosition(pos+1);
            }
        }
        return cards;
    }

    /**
     * Change a position of a Card in the Column it is in, and change the positions of other Cards in the Column which
     * have been affected by the position change. Store all the position changes in the database
     * @param cardId id of the Card whose position needs to be changed
     * @param newPos the new intended position for the card
     * @return the new updated card
     */
    public Card editCardPosition(long cardId, int newPos) {
        Card card = getById(cardId);

        int oldPos = card.getPosition();
        long columnId = card.getColumnId();

        if(newPos > cardRepository.findMaxPositionByColumnId(columnId) || newPos <= 0) {
            return null;
        }

        List<Card> cards = changePositionsOfAffectedCards(oldPos, newPos, columnId);

        card.setPosition(newPos);
        cards.add(card);
        cardRepository.saveAll(cards);

        return card;
    }

    /**
     * Delete a card from the database and updates the positions of affected cards.
     * @param cardId id of the card that is to be deleted
     * @return the card that was deleted
     */
    public Card deleteCard(long cardId) {
        if(!cardRepository.existsById(cardId))
            return null;

        Card card = getById(cardId);
        long columnId = card.getColumnId();
        Integer pos = card.getPosition();

        cardRepository.deleteById(cardId);

        if(pos != null) {
            List<Card> cardsToUpdate = cardRepository.findByColumnIdAndPositionGreaterThan(columnId, pos);
            for(Card cardToUpdate : cardsToUpdate) {
                int posToUpdate = cardToUpdate.getPosition();
                cardToUpdate.setPosition(posToUpdate-1);
                cardRepository.save(cardToUpdate);
            }
        }
        return card;
    }

}
