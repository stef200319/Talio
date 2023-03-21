
package server.api;

import commons.Board;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestBoardRepository implements BoardRepository {
    private final List<Board> boards;
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
    public TestBoardRepository() {
        boards = new ArrayList<>();
        Board b1 = new Board("Test1");
        Board b2 = new Board("Test2");
        Board b3 = new Board("Test3");
        b1.setId(0);
        b2.setId(1);
        b3.setId(2);
        boards.add(b1);
        boards.add(b2);
        boards.add(b3);

        lastUsedId = 2;
    }

    /**
     * @param entity must not be {@literal null}.
     * @param <S>
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public <S extends Board> S save(S entity) {
        call("save");
        entity.setId(++lastUsedId);
        boards.add(entity);
        return entity;
    }

    /**
     * @param id must not be {@literal null}.
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public Optional<Board> findById(Long id) {
        for (Board b : boards) {
            if (b.getId().equals(id)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    /**
     * @return
     */
    @SuppressWarnings("checkstyle.*")
    @Override
    public List<Board> findAll() {
        return boards;
    }

    /**
     * @param sort
     * @return
     */
    @Override
    public List<Board> findAll(Sort sort) {
        return boards;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Board> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public List<Board> findAllById(Iterable<Long> longs) {
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
    public <S extends Board> List<S> saveAll(Iterable<S> entities) {
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
    public <S extends Board> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Board> entities) {

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
    public Board getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    public Board getById(Long aLong) {
        for (Board b : boards) {
            if (b.getId().equals(aLong)) {
                return b;
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
    public <S extends Board> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Board> boolean exists(Example<S> example) {
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
    public <S extends Board, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }

    /**
     * @param id must not be {@literal null}.
     */
    @Override
    public void deleteById(Long id) {
//        if (id > 0 && id <= boards.size()) {
//            boards.remove(id.intValue() - 1);
//        }
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i) != null && boards.get(i).getId() == id) {
                boards.remove(i);
                return;
            }
        }
    }

    /**
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(Board entity) {

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
    public void deleteAll(Iterable<? extends Board> entities) {

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
//        if (id > 0 && id <= boards.size()) {
//            return true;
//        } else {
//            return false;
//        }
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i) != null && boards.get(i).getId() == id) {
                return true;
            }
        }
        return false;

    }

}
