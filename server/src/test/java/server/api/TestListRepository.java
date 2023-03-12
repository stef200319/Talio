
package server.api;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import commons.List;
import server.database.ListRepository;

public class TestListRepository implements ListRepository {
    private final java.util.List<List> lists;
    private final java.util.List<String> calledMethods = new ArrayList<>();

    /**
     * @param name of the method which was executed
     */
    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     *
     */
    public TestListRepository() {
        lists = new ArrayList<>();
        lists.add(new List("Test1", 5));
        lists.get(0).setId(1);

        lists.add(new List("Test2", 5));
        lists.get(1).setId(2);
    }

    /**
     * @param entity must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> S save(S entity) {
        call("save");

        entity.setId((long) lists.size());
        lists.add(entity);
        return entity;
    }

    /**
     * @param id must not be {@literal null}.
     * @return
     */
    @Override
    public Optional<List> findById(Long id) {
        call("findById");

        if (id > 0 && id <= lists.size()) {
            return Optional.of(lists.get(id.intValue() - 1));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return
     */
    @Override
    public java.util.List<List> findAll() {
        return lists;
    }

    /**
     * @param sort
     * @return
     */
    @Override
    public java.util.List<List> findAll(Sort sort) {
        return null;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<List> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public java.util.List<List> findAllById(Iterable<Long> longs) {
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
    public <S extends List> java.util.List<S> saveAll(Iterable<S> entities) {
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
     * @param <S> sut
     * @return null
     */
    @Override
    public <S extends List> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> java.util.List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<List> entities) {

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
    public List getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    public List getById(Long aLong) {
        for (List l : lists) {
            if (l.getId() == aLong) {
                return l;
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
    public <S extends List> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> java.util.List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> java.util.List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends List> boolean exists(Example<S> example) {
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
    public <S extends List, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }

    /**
     * @param id must not be {@literal null}.
     */
    @Override
    public void deleteById(Long id) {
        if (id > 0 && id <= lists.size()) {
            lists.remove(id.intValue() - 1);
        }
    }

    /**
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(List entity) {
        lists.remove(entity);
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
    public void deleteAll(Iterable<? extends List> entities) {

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
        for (List l : lists) {
            if (l.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
