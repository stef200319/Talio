package server.services;

import commons.Card;
import commons.CardTag;
import commons.Subtask;
import org.springframework.stereotype.Service;
import server.database.CardRepository;
import server.database.SubtaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private CardRepository cardRepository;
    private SubtaskRepository subtaskRepository;

    /**
     * @param cardRepository the table which contains all the cards
     * @param subtaskRepository the table which contains all subtasks
     */
    public CardService(CardRepository cardRepository, SubtaskRepository subtaskRepository) {
        this.cardRepository = cardRepository;
        this.subtaskRepository = subtaskRepository;
    }

    /**
     * @param cardId The id of the card we're checking the existence of
     * @return true if it exists, false otherwise
     */
    public boolean existsById(long cardId) {
        return cardRepository.existsById(cardId);
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

        cardRepository.save(card);
        return card;
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
        if(!existsById(cardId))
            return null;
        Card card = getById(cardId);
        card.setTitle(title);
        cardRepository.save(card);
        return card;
    }

    /**
     * Change the description of a card
     * @param cardId the id of the card to be changed
     * @param description new description for the card
     * @return the new card saved to the database
     */
    public Card editDescription(long cardId, String description) {
        Card card = getById(cardId);
        card.setDescription(description);
        cardRepository.save(card);
        return card;
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

        Integer maxPosition = 0;
        cards = cardRepository.findCardsByColumnId(columnId);
        if(cards!=null && cards.size()>0)
            maxPosition = cardRepository.findMaxPositionByColumnId(columnId);
        card.setPosition(maxPosition+1);

        card.setColumnId(columnId);
        cardRepository.save(card);
        return card;
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

    /**
     * Method that creates and adds a subtask to a card
     * @param cardId The card the subtask will be added to
     * @param subtaskTitle Title of the new subtask
     * @return New card with updated subtasks
     */
    public Card createSubtask(long cardId, String subtaskTitle) {
        if(!existsById(cardId))
            return null;
        Card card = getById(cardId);

        Subtask newSubtask = new Subtask(subtaskTitle);
        subtaskRepository.save(newSubtask);

        card.getSubtasks().add(newSubtask);
        cardRepository.save(card);
        return card;
    }

    /**
     * Method that return all the subtasks of a card
     * @param cardId id of the card
     * @return List with all of its subtasks
     */
    public List<Subtask> getAllSubtasksByCardId(long cardId) {
        if(!existsById(cardId))
            return null;
        List<Subtask> subtasks = cardRepository.findSubtasksByCardId(cardId);
        if(subtasks == null)
            return new ArrayList<Subtask>();
        return subtasks;
    }

    /**
<<<<<<< HEAD
     * Gets the cardTags given a certain cardId
     * @param cardId
     * @return list of cardTags
     */
    public List<CardTag> getCardTagsByCardId(long cardId) {
        if (!existsById(cardId)) return null;
        return cardRepository.getById(cardId).getCardTags();
    }

    /**
     * Changes the position of a subtask and shifts all affected subtasks to the left
     * @param cardId id of the card the subtask is in
     * @param oldPos position of the subtask
     * @param newPos new position of the subtask
     * @return the updated card
     */
    public Card changeSubtaskPosition(long cardId, int oldPos, int newPos) {
        List<Subtask> subtasks = getAllSubtasksByCardId(cardId);
        if(subtasks==null || oldPos>subtasks.size() || newPos>subtasks.size() || oldPos<0 || newPos<0)
            return null;
        if(oldPos<newPos) {
            Subtask aux = subtasks.get(oldPos);
            for (int i = oldPos; i < newPos; i++) {
                Subtask s = subtasks.get(i + 1);
                subtasks.set(i, s);
            }
            subtasks.set(newPos, aux);
            Card card = getById(cardId);
            card.setSubtasks(subtasks);
            cardRepository.save(card);
            return card;
        }
        else {
            Subtask aux = subtasks.get(oldPos);
            for(int i = oldPos;i>newPos;i--) {
                Subtask s = subtasks.get(i-1);
                subtasks.set(i, s);
            }
            subtasks.set(newPos, aux);
            Card card = getById(cardId);
            card.setSubtasks(subtasks);
            cardRepository.save(card);
            return card;
        }
    }

    /**
     * Deletes a subtask from the card
     * @param cardId id of the card the subtask is in
     * @param subtaskId id of the subtask to be deleted
     * @return the updated card without that subtask
     */
    public Card deleteSubtask(long cardId, long subtaskId) {
        if(!existsById(cardId))
            return null;
        List<Subtask> subtasks = getAllSubtasksByCardId(cardId);
        for(int i=0;i<subtasks.size();i++)
            if(subtasks.get(i).getId()==subtaskId)
                subtasks.remove(i);
        Card card = getById(cardId);
        card.setSubtasks(subtasks);
        cardRepository.save(card);
        return card;
    }

}
