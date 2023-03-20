package server.database;

import commons.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    /**Find the current position of the last Column in a given Board
     * @param boardId id of the Column from which the last position of its Columns is wanted
     * @return an Integer of the position of the last Column in the Board
     */
    @Query("SELECT MAX(c.position) FROM Column c WHERE c.boardId = ?1")
    Integer findMaxPositionByBoardId(Long boardId);


    /**Fetch all the Columns whose position is larger that the position of a given Column in the Board they are in.
     * @param boardId boardId of the Column in which it is located in
     * @param position Current position of the Column, where you want to fetch all the Column whose positions are larger
     * that this position
     * @return A List of Columns, where all the positions of the Columns are larger than the input position
     */
    @Query("SELECT c FROM Column c WHERE c.boardId = :boardId AND c.position > :position")
    List<Column> findByBoardIdAndPositionGreaterThan(Long boardId, Integer position);


    /**
     * @param boardId the id of the board for which we want to find all columns
     * @return a Column of all the columns on the board
     */
    @Query("SELECT c From Column c WHERE c.boardId = :boardId SORT BY c.position ASC")
    List<Column> findColumnsByBoardId(Long boardId);
}
