package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    /**Find the current position of the last Card in a given columnId
     * @param columnId id of the Column from which the last position of its Cards is wanted
     * @return an Integer of the position of the last Card in the Column
     */
    @Query("SELECT MAX(c.position) FROM Card c WHERE c.columnId = :columnId")
    Integer findMaxPositionByColumnId(Long columnId);


    /**Fetch all the Cards whose position is larger that the position of a given Card in the Column they are in.
     * @param columnId columnId of the Card in which it is located in
     * @param position Current position of the Card, where you want to fetch all the Cards whose positions are larger
     * that this position
     * @return A List of Cards, where all the positions of the Cards are larger than the input position
     */
    @Query("SELECT c FROM Card c WHERE c.columnId = :columnId AND c.position > :position")
    List<Card> findByColumnIdAndPositionGreaterThan(Long columnId, Integer position);

    /**
     * Returns all the cards given some columnId
     * @param columnId the columnId you want the cards from
     * @return a list with all the cards with the given columnId
     */
    @Query("SELECT c FROM Card c WHERE c.columnId = :columnId SORT BY c.position ASC")
    List<Card> findCardsByColumnId(Long columnId);
}
