package server.database;

import commons.Board;
import commons.CardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardTagRepository extends JpaRepository<CardTag, Long> {

    /**
     * query that finds the cardTags by board
     * @param board
     * @return the list of the cardTags
     */
    @Query("SELECT c FROM CardTag c WHERE c.board = :board")
    List<CardTag> findCardTagsByBoard(Board board);
}
