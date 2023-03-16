package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    /**Find the current position of the last Card in a given List
     * @param listId id of the List from which the last position of its Cards is wanted
     * @return an Integer of the position of the last Card in the List
     */
    @Query("SELECT MAX(c.position) FROM Card c WHERE c.columnId = ?1")
    Integer findMaxPositionByColumnId(Long listId);

    /**Fetch all the Cards whose position is larger that the position of a given Card in a List
     * @param listId List of the Card in which it is located in
     * @param position Current position of the Card, where you want to fetch all the Cards whose positions are larger
     * that this postion
     * @return A list of Cards, where all the positions of the Cards are larger than the input position
     */
    @Query("SELECT c FROM Card c WHERE c.columnId = :listId AND c.position > :position")
    List<Card> findByColumnIdAndPositionGreaterThan(long listId, int position);

}
