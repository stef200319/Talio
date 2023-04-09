package server.services;

import commons.Card;
import commons.Column;
import org.springframework.stereotype.Service;
import server.database.ColumnRepository;

import java.util.List;
import java.util.Optional;

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
     * @return all columns in the table
     */
    public List<Column> getAll() {
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


    /**Change the Background colour of a column
     * @param columnId The ID of the column whose title should be changed
     * @param bgColour Background colour which should replace the old Background colour of the column
     * @return the edited column saved to the database
     */

    public Column editBackgroundColour(long columnId, String bgColour){
        Column column = getById(columnId);
        column.setBgColour(bgColour);
        columnRepository.save(column);
        return column;
    }


    /**Change the Border colour of a column.
     * @param columnId The ID of the column whose title should be changed
     * @param borderColour Border colour which should replace the old Border colour of the column
     * @return the edited column saved to the database
     */
    public Column editBorderColour(long columnId, String borderColour){
        Column column = getById(columnId);
        column.setBorderColour(borderColour);
        columnRepository.save(column);
        return column;
    }

    /**Change the Font-type of a column.
     * @param columnId The ID of the column whose title should be changed
     * @param fontType Font Type which should replace the old Font Type of the column
     * @return the edited column saved to the database
     */
    public Column editFontType(long columnId, String fontType){
        Column column = getById(columnId);
        column.setFontType(fontType);
        columnRepository.save(column);
        return column;
    }


    /**Change the Boldness of a column, if it exists.
     * @param columnId The ID of the column whose title should be changed
     * @param bold Boldness which should replace the old Boldness of the column
     * @return the edited column saved to the database
     */
    public Column editFontStyleBold(long columnId, boolean bold){
        Column column = getById(columnId);
        column.setFontStyleBold(bold);
        columnRepository.save(column);
        return column;
    }


    /**Change the Italicness of a column, if it exists.
     * @param columnId The ID of the column whose title should be changed
     * @param italic Italicness which should replace the old Boldness of the column
     * @return the edited column saved to the database
     */
    public Column editFontStyleItalic(long columnId, boolean italic){
        Column column = getById(columnId);
        column.setFontStyleItalic(italic);
        columnRepository.save(column);
        return column;
    }


    /**Change the Font Colour of a column, if it exists.
     * @param columnId The ID of the column whose title should be changed
     * @param fontColour Font Colour which should replace the old Font Colour of the column
     * @return the edited column saved to the database
     */
    public Column editFontColour(long columnId, String fontColour){
        Column column = getById(columnId);
        column.setFontColour(fontColour);
        columnRepository.save(column);
        return column;
    }

}
