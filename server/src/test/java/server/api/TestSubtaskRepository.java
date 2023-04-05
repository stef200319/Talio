package server.api;


import commons.Subtask;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.SubtaskRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestSubtaskRepository implements SubtaskRepository {

    private final List<Subtask> subtasks;
    long lastUsedId;

    public TestSubtaskRepository() {
        List<Subtask> subtasks = new ArrayList<>();
        Subtask s1 = new Subtask("test1");
        Subtask s2 = new Subtask("test2");
        subtasks.add(s1);
        subtasks.add(s2);
        lastUsedId=1;
        this.subtasks = subtasks;
    }

    /**
     * TODO
     */
    @Override
    public List<Subtask> findAll() {
        return subtasks;
    }
    /**
     * TODO
     */

    @Override
    public List<Subtask> findAll(Sort sort) {
        return subtasks;
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Override
    public Page<Subtask> findAll(Pageable pageable) {
        return null;
    }
    /**
     * TODO
     */

    @Override
    public List<Subtask> findAllById(Iterable<Long> longs) {
        return null;
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    @Override
    public long count() {
        return subtasks.size();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param aLong must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    @Override
    public void deleteById(Long aLong) {
        for (int i=0;i<subtasks.size();i++)
            if(subtasks.get(i)!=null && subtasks.get(i).getId()==aLong) {
                subtasks.remove(i);
                break;
            }
    }

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    public void delete(Subtask entity) {
        subtasks.remove(entity);
    }

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     *
     * @param longs must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        for(Subtask s : subtasks) {
            if(s!=null) {
                for(long i : longs)
                    if(s.getId()==i)
                        subtasks.remove(s);
            }
        }
    }

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    @Override
    public void deleteAll(Iterable<? extends Subtask> entities) {
        for(Subtask s : entities)
            subtasks.remove(s);
    }

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAll() {
        while (subtasks.size()>0)
            subtasks.remove(0);
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed
     * the entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public <S extends Subtask> S save(S entity) {
        for (Subtask s : subtasks) {
            if(s!=null && s.getId()==entity.getId()) {
                subtasks.remove(s);
                subtasks.add(entity);
                return entity;
            }
        }
        entity.setId(++lastUsedId);
        subtasks.add(entity);
        return entity;
    }

    @Override
    public <S extends Subtask> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param aLong must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public Optional<Subtask> findById(Long aLong) {
        for(Subtask s : subtasks) {
            if(s!=null && s.getId()==aLong)
                return Optional.of(s);
        }
        return Optional.empty();
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param aLong must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    @Override
    public boolean existsById(Long aLong) {
        for (Subtask s : subtasks) {
            if(s!=null && s.getId()==aLong)
                return true;
        }
        return false;
    }

    /**
     * Flushes all pending changes to the database.
     */
    @Override
    public void flush() {

    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity entity to be saved. Must not be {@literal null}.
     * @return the saved entity
     */
    @Override
    public <S extends Subtask> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * Saves all entities and flushes changes instantly.
     *
     * @param entities entities to be saved. Must not be {@literal null}.
     * @return the saved entities
     * @since 2.5
     */
    @Override
    public <S extends Subtask> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * Deletes the given entities in a batch which means it will create a single query. This kind of operation leaves
     * JPAs first level cache and the database out of sync. Consider flushing the before calling this
     * method.
     *
     * @param entities entities to be deleted. Must not be {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllInBatch(Iterable<Subtask> entities) {

    }

    /**
     * Deletes the entities identified by the given ids using a single query. This kind of operation leaves JPAs first
     * level cache and the database out of sync. Consider flushing the before calling this method.
     *
     * @param longs the ids of the entities to be deleted. Must not be {@literal null}.
     * @since 2.5
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     * Deletes all entities in a batch call.
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param aLong must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    @Override
    public Subtask getOne(Long aLong) {
        return null;
    }

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param aLong must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @since 2.5
     */
    @Override
    public Subtask getById(Long aLong) {
        for (Subtask s : subtasks) {
            if(s!=null && s.getId()==aLong)
                return s;
        }
        return null;
    }

    /**
     * Returns a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
     *
     * @param example must not be {@literal null}.
     * @return a single entity matching the given {@link Example} or {@link Optional#empty()} if none was found.
     * @throws IncorrectResultSizeDataAccessException if the Example yields more than one result.
     */

    @Override
    public <S extends Subtask> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * TODO
     */
    @Override
    public <S extends Subtask> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public <S extends Subtask> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * Returns a {@link Page} of entities matching the given {@link Example}. In case no match could be found, an empty
     * {@link Page} is returned.
     *
     * @param example  must not be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return a {@link Page} of entities matching the given {@link Example}.
     */
    @Override
    public <S extends Subtask> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * Returns the number of instances matching the given {@link Example}.
     *
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @return the number of instances matching the {@link Example}.
     */
    @Override
    public <S extends Subtask> long count(Example<S> example) {
        return 0;
    }

    /**
     * Checks whether the data store contains elements that match the given {@link Example}.
     *
     * @param example the {@link Example} to use for the existence check. Must not be {@literal null}.
     * @return {@literal true} if the data store contains elements that match the given {@link Example}.
     */
    @Override
    public <S extends Subtask> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * Returns entities matching the given {@link Example} applying the {@link Function queryFunction} that defines the
     * query and its result type.
     *
     * @param example       must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return all entities matching the given {@link Example}.
     * @since 2.6
     */
    @Override
    public <S extends Subtask, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }
}
