
package server.api;

import commons.Column;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import server.database.ColumnRepository;

public class TestColumnRepository implements ColumnRepository {
    private final java.util.List<Column> columns;
    private final java.util.List<String> calledMethods = new ArrayList<>();
    private long lastUsedId;
    /**
     * @param name of the method which was executed
     */
    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     *
     */
    public TestColumnRepository() {
        columns = new ArrayList<>();
        columns.add(new Column("Test1", 0));
        columns.get(0).setId(0);
        columns.get(0).setPosition(0);

        columns.add(new Column("Test2", 0));
        columns.get(1).setId(1);
        columns.get(1).setPosition(1);

        lastUsedId = 1;
    }

    /**
     * @param entity must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> S save(S entity) {
        call("save");

        for (Column c :
                columns) {
            if (c.getId() == entity.getId()) {
                columns.remove(c);
                columns.add(entity);
                return entity;
            }
        }

        entity.setId(++lastUsedId);
        columns.add(entity);
        return entity;
    }

    /**
     * @param id must not be {@literal null}.
     * @return
     */
    @Override
    public Optional<Column> findById(Long id) {
        call("findById");


        for (Column column : columns) {
            if (column != null && column.getId() == id) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }

    /**
     * @return
     */
    @Override
    public List<Column> findAll() {
        return columns;
    }

    /**
     * @param sort
     * @return
     */
    @Override
    public List<Column> findAll(Sort sort) {
        return null;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Column> findAll(Pageable pageable) {
        return null;
    }

    /**
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public List<Column> findAllById(Iterable<Long> longs) {
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
    public <S extends Column> List<S> saveAll(Iterable<S> entities) {
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
    public <S extends Column> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> java.util.List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Column> entities) {

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
    public Column getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    public Column getById(Long aLong) {

        for (Column column : columns) {
            if (column != null && column.getId() == aLong) {
                return column;
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
    public <S extends Column> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> java.util.List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> java.util.List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Column> boolean exists(Example<S> example) {
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
    public <S extends Column, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }

    /**
     * @param id must not be {@literal null}.
     */
    @Override
    public void deleteById(Long id) {

        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i) != null && columns.get(i).getId() == id) {
                columns.remove(i);
                return;
            }
        }

    }

    /**
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(Column entity) {
        columns.remove(entity);
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
    public void deleteAll(Iterable<? extends Column> entities) {

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

        for (Column column : columns) {
            if (column != null && column.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param boardId must not be {@literal null}.
     * @return
     */
    @Override
    public Integer findMaxPositionByBoardId(Long boardId) {
        return (int) columns.stream().mapToLong(Column::getBoardId).max().getAsLong();
    }

    /**
     * @param boardId must not be {@literal null}.
     * @return
     */
    @Override
    public List<Column> findByBoardIdAndPositionGreaterThan(Long boardId, Integer position) {
        return columns.stream().filter((o1) -> o1.getBoardId()==boardId && o1.getPosition() > position).
                collect(Collectors.toList());
    }

    /**
     * @param boardId the id of the board for which we want to find all columns
     * @return The list of columns which correspond to the input boardId
     */
    @Override
    public List<Column> findColumnsByBoardId(Long boardId) {
        List<Column> returnList = new LinkedList<>();

        for (Column col : columns) {
            if (col != null && col.getBoardId() == boardId)
                returnList.add(col);
        }

        return returnList;
    }
}
