
package server.api;

import commons.Subtask;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardRepository;
import commons.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestCardRepository implements CardRepository {
    private final List<Card> cards;
    private final List<String> calledMethods = new ArrayList<>();
    private long lastUsedId;
    /**
     * @param name
     */
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     *
     */
    @SuppressWarnings("checkstyle.*")
    public TestCardRepository() {
        cards = new ArrayList<>();
        Card c1 = new Card("Test1", 1);
        Card c2 = new Card("Test2", 1);
        c1.setId(0);
        c2.setId(1);

        c1.setPosition(1);
        c2.setPosition(2);

        List<Subtask> subtasks = new ArrayList<>();
        Subtask s1 = new Subtask("s1");
        Subtask s2 = new Subtask("s2");
        subtasks.add(s1);
        subtasks.add(s2);
        c1.setSubtasks(subtasks);

        cards.add(c1);
        cards.add(c2);

        lastUsedId = 1;
    }

    /**
     * @param entity must not be {@literal null}.
     * @param <S>
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public <S extends Card> S save(S entity) {
        call("save");

        for (Card c :
                cards) {
            if (c.getId() == entity.getId()) {
                cards.remove(c);
                cards.add(entity);
                return entity;
            }
        }

        entity.setId(++lastUsedId);
        cards.add(entity);
        return entity;
    }

    /**
     * @param id must not be {@literal null}.
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public Optional<Card> findById(Long id) {
        for (Card card : cards) {
            if (card != null && card.getId() == id) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    /**
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public List<Card> findAll() {
        return cards;
    }

    /**
     * @param sort
     * @return
     */
    @Override
    public List<Card> findAll(Sort sort) {
        return cards;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Card> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public List<Card> findAllById(Iterable<Long> longs) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     *
     */
    @Override
    public void flush() {

    }

    /**
     * @param entity entity to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Card> entities) {

    }

    /**
     * @param longs the ids of the entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     *
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    public Card getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    public Card getById(Long aLong) {
        for (Card c :
                cards) {
            if (c != null && c.getId() == aLong) {
                return c;
            }
        }
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Card> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * @param example       must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @param <S>
     * @param <R>
     * @return
     */
    @Override
    public <S extends Card, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }

    /**
     * @param id must not be {@literal null}.
     */
    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null && cards.get(i).getId() == id) {
                cards.remove(i);
                break;
            }
        }
    }

    /**
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(Card entity) {
        cards.remove(entity);
    }

    /**
     * @param longs must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAll(Iterable<? extends Card> entities) {
        for (Card card: entities) {
            cards.remove(card);
        }
    }

    /**
     *
     */
    @Override
    public void deleteAll() {

    }

    /**
     * @param id must not be {@literal null}. 
     * @return
     */
    @Override

    public boolean existsById(Long id) {
//        if (id > 0 && id <= cards.size()) {
//            return true;
//        } else {
//            return false;
//        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null && cards.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param listId to be filled
     * @return to be filled
     */
    @Override
    public Integer findMaxPositionByColumnId(Long listId) {
        return cards.stream().mapToInt(Card::getPosition).max().getAsInt();
    }

    /**
     * @param columnId Column of the Card in which it is located in
     * @param position Current position of the Card, where you want to fetch all the Cards whose positions are larger
     *                 that this postion
     * @return to be filled
     */
    @Override
    public List<Card> findByColumnIdAndPositionGreaterThan(Long columnId, Integer position) {
        return cards.stream().
                filter(o1 -> o1.getColumnId() == columnId && o1.getPosition() > position).
                collect(Collectors.toList());
    }

    /**
     * @param columnId the columnId you want the cards from 
     * @return the list with the cards
     */
    @Override
    public List<Card> findCardsByColumnId(Long columnId) {
        List<Card> cardList = new ArrayList<>();
        for (Card c :
                cards) {
            if (c.getColumnId() == columnId) {
                cardList.add(c);
            }
        }

        return cardList;
    }

    /**
     * @param cardId id of the card to get the subtasks from
     * @return list of subtasks
     */
    @Override
    public List<Subtask> findSubtasksByCardId(Long cardId) {
        for(int i=0;i<cards.size();i++) {
            if(cards.get(i)!=null && cards.get(i).getId()==cardId)
                return cards.get(i).getSubtasks();
        }
        return null;
    }

}
