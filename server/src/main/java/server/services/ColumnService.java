package server.services;

import commons.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import server.component.RESTEvent;
import server.database.ColumnRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ColumnService {
    private ColumnRepository columnRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * @param columnRepository The data access model of the columns
     */
    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }


    /**
     * @return all columns in the table
     */
    public List<Column> getAll() {
        RESTEvent event = new RESTEvent(columnRepository.findAll(), "everything was found");
        applicationEventPublisher.publishEvent(event);
        return columnRepository.findAll();
    }

    /**
     * @param id the id of the column for which the user is searching for
     * @return whether there exists a column in the table which matches the id
     */
    public boolean existsById(long id) {
        return columnRepository.existsById(id);
    }

    /**
     * @param id the id of the column for which the user is searching for
     * @return the column which matches with the id, else null
     */
    public Column getById(long id) {
        Optional<Column> column = columnRepository.findById(id);
        return column.orElse(null);
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

    /**
     * @param title the title of the new column
     * @param boardId the id of the board on which it will be situated
     * @return the new column which was created in the database
     */
    public Column add(String title, Long boardId) {
        Integer maxPosition = columnRepository.findMaxPositionByBoardId(boardId);
        int newPosition = maxPosition == null ? 1 : maxPosition + 1;

        Column newColumn = new Column(title, boardId);
        newColumn.setPosition(newPosition);

        RESTEvent event = new RESTEvent(newColumn, "column was created");
        applicationEventPublisher.publishEvent(event);

        return columnRepository.save(newColumn);
    }

    /**
     * @param title new title of the column
     * @param columnId specifies which column needs to be updated
     * @return the new column in the database
     */
    public Column update(String title, long columnId) {
        Column column = getById(columnId);
        column.setTitle(title);
        return columnRepository.save(column);
    }

    /**
     * Method which deletes a column and updates the position of the other columns.
     * @param columnId the id of the column
     * @return the column which was deleted
     */
    public Column deleteByColumnId(long columnId) {
        Column column = getById(columnId);
        columnRepository.deleteById(columnId);

        if (column != null && column.getPosition() != null)
            updateColumnPosition(column.getBoardId(), column.getPosition());

        return column;
    }

    /**
     * helper function of deleteByColumnId which updates the positions.
     * @param boardId the id of the board for which columns have to be updated
     * @param position position of the list which was removed
     */
    private void updateColumnPosition(long boardId, Integer position) {
        List<Column> columnsToUpdate = columnRepository.findByBoardIdAndPositionGreaterThan(boardId, position);
        for (Column columnToUpdate : columnsToUpdate) {
            int currentPosition = columnToUpdate.getPosition();
            columnToUpdate.setPosition(currentPosition - 1);
            columnRepository.save(columnToUpdate);
        }
    }

}
