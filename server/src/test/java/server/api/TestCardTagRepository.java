package server.api;

import commons.Board;
import commons.CardTag;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CardTagRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestCardTagRepository implements CardTagRepository {
    /**
     * finds all CardTags
     * @return list of CardTags
     */
    @Override
    public List<CardTag> findAll() {
        return null;
    }

    /**
     * finds all CardTags
     * @param sort
     * @return
     */
    @Override
    public List<CardTag> findAll(Sort sort) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public Page<CardTag> findAll(Pageable pageable) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public List<CardTag> findAllById(Iterable<Long> longs) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public long count() {
        return 0;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteById(Long aLong) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void delete(CardTag entity) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAll(Iterable<? extends CardTag> entities) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAll() {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> S save(S entity) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public Optional<CardTag> findById(Long aLong) {
        return Optional.empty();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void flush() {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> S saveAndFlush(S entity) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAllInBatch(Iterable<CardTag> entities) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public void deleteAllInBatch() {

    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public CardTag getOne(Long aLong) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public CardTag getById(Long aLong) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> List<S> findAll(Example<S> example) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> long count(Example<S> example) {
        return 0;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag> boolean exists(Example<S> example) {
        return false;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public <S extends CardTag, R> R findBy(Example<S> example,
                                           Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    @Override
    public List<CardTag> findCardTagsByBoard(Board board) {
        return null;
    }
}
