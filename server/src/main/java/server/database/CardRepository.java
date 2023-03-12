package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT MAX(c.position) FROM Card c WHERE c.listId = ?1")
    int findMaxPositionByListId(Long listId);
}
