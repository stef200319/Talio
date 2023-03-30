package server.services;

import commons.Column;
import org.springframework.stereotype.Service;
import server.database.ColumnRepository;

import java.util.List;

@Service
public class ColumnService {
    private ColumnRepository columnRepository;

    /**
     * @param columnRepository The data access model of the columns
     */
    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    /**
     * @param boardId the id of the board for which all columns have to be returned
     * @return all the columns with a board id which corresponds to the input
     */
    public List<Column> getByBoardId(long boardId) {
        return columnRepository.findColumnsByBoardId(boardId);
    }

    /**
     * @param column The column which the user wants to delete
     */
    public void delete(Column column) {
        columnRepository.delete(column);
    }
}
