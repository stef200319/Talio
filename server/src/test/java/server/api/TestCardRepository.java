package server.api;

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

@SuppressWarnings("checkstyle:*")
public class TestCardRepository implements CardRepository {

    private final List<Card> cards;

    public final List<String> calledMethods = new ArrayList<>();

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    private void call(String name) {
        calledMethods.add(name);
    }

    public TestCardRepository() {
        cards = new ArrayList<>();
        cards.add(new Card("Test1", 1));
        cards.add(new Card("Test2", 1));
    }

    @Override
    public <S extends Card> S save(S entity) {
        call("save");
        entity.id = (long) cards.size();
        cards.add(entity);
        return entity;
    }

    @Override
    public Optional<Card> findById(Long id) {
        if (id > 0 && id <= cards.size()) {
            return Optional.of(cards.get(id.intValue() - 1));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Card> findAll() {
        return cards;
    }

    @Override
    public List<Card> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Card> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends Card> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Card> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Card> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Card> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Card getOne(Long aLong) {
        return null;
    }

    @Override
    public Card getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Card> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Card> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Card> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Card> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Card> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Card, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (id > 0 && id <= cards.size()) {
            cards.remove(id.intValue() - 1);
        }
    }

    @Override
    public void delete(Card entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Card> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Long id) {
        if (id > 0 && id <= cards.size()) {
            return true;
        } else {
            return false;
        }
    }
}