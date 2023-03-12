package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card, Long> {
    /**Find the current position of the last Card in a given List
     * @param listId id of the List from which the last position of its Cards is wanted
     * @return an Integer of the position of the last Card in the List
     */
    @Query("SELECT MAX(c.position) FROM Card c WHERE c.listId = ?1")
    Integer findMaxPositionByListId(Long listId);
}
