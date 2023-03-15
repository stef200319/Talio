package server.database;

import commons.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    /**
     * @param boardId the id of the board for which we want to find all columns
     * @return a list of all the columns on the board
     */
    @Query("SELECT c From Column c WHERE c.boardId=:boardId")
    List<Column> findColumnsByBoardId(long boardId);
}
