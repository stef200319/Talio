package server.api;

import commons.Card;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ColumnRepository;

import java.util.List;

@RestController
@RequestMapping("/column")
public class ColumnController {

    private final CardController cardController;

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    /**
     *
     * @param columnRepository the data container which includes all the columns
     * @param boardRepository the repository of the board -> used for checking whether boardId exists
     * @param cardRepository the repository of the cards
     * @param cardController the controller which controls all card crud operations
     */
    public ColumnController(ColumnRepository columnRepository, BoardRepository boardRepository,
                            CardRepository cardRepository, CardController cardController) {
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
        this.cardController = cardController;
    }

    // I think this should just return List<Column>
    /**
     * @return all columns in the database
     */
//    public List<Column> getAllColumns() {
//        return columnRepository.findAll();
//    }
    @GetMapping("/getAllColumns")
    @ResponseBody public ResponseEntity<List<Column>> getAllColumns() {
        List<Column> columns = columnRepository.findAll();

        if (columns.size() > 0) {
            return ResponseEntity.ok(columns);
        }

        return ResponseEntity.notFound().build();
    }


    /**
     * @param columnId the id of the column which will be retrieved
     * @return the column according to the input id
     */
    @GetMapping("/getColumnByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<Column> getColumnByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.notFound().build();
        }

        Column column = columnRepository.findById(columnId).get();
        return ResponseEntity.ok(column);

    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable("title") String title,
                                                        @PathVariable("boardId") Long boardId) {
        if (title == null || !boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Integer maxPosition = columnRepository.findMaxPositionByBoardId(boardId);
        int newPosition = maxPosition == null ? 1 : maxPosition + 1;

        Column newColumn = new Column(title, boardId);
        newColumn.setPosition(newPosition);

        Column saved = columnRepository.save(newColumn);
        return ResponseEntity.ok(saved);
    }

    /**
     * @param columnId the id of the column which should be updated
     * @param title the new title of the column
     * @return whether the column was successfully updated
     */
    @PutMapping("/editColumnTitle/{columnId}/{title}")
    @ResponseBody public ResponseEntity<Column> editColumnTitle(@PathVariable("columnId") long columnId,
                                                                @PathVariable("title") String title) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setTitle(title);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }



    /**Change the Background colour of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param bgColour Background colour which should replace the old Background colour of the Column
     * @return receive a message indicating the Background colour has change, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnBackgroundColour/{columnId}/{bgColour}")
    @ResponseBody public ResponseEntity<Column> editColumnBackgroundColour(@PathVariable("columnId") long columnId,
                                                                       @PathVariable("bgColour") String bgColour){
        if (bgColour == null || !columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setBgColour(bgColour);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }


    /**Change the Border colour of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param borderColour Border colour which should replace the old Border colour of the Column
     * @return receive a message indicating the Border colour has changed, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnBorderColour/{columnId}/{borderColour}")
    @ResponseBody public ResponseEntity<Column> editColumnBorderColour
    (@PathVariable("columnId") long columnId, @PathVariable("borderColour") String borderColour){
        if (borderColour == null || !columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setBorderColour(borderColour);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }


    /**Change the Font-type of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param fontType Font Type which should replace the old Font Type of the Column
     * @return receive a message indicating the Font Type has changed, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnFontType/{columnId}/{fontType}")
    @ResponseBody public ResponseEntity<Column> editColumnFontType
    (@PathVariable("columnId") long columnId, @PathVariable("fontType") String fontType){
        if (fontType == null || !columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setFontType(fontType);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }

    /**Change the Boldness of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param bold Boldness which should replace the old Boldness of the Column
     * @return receive a message indicating the Boldness has changed, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnFontStyleBold/{columnId}/{bold}")
    @ResponseBody public ResponseEntity<Column> editColumnFontStyleBold
    (@PathVariable("columnId") long columnId, @PathVariable("bold") boolean bold){
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setFontStyleBold(bold);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }


    /**Change the Italicness of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param italic Italicness which should replace the old Boldness of the Column
     * @return receive a message indicating the Italicness has changed, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnFontStyleItalic/{columnId}/{italic}")
    @ResponseBody public ResponseEntity<Column> editColumnFontStyleItalic
    (@PathVariable("columnId") long columnId, @PathVariable("italic") boolean italic){
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setFontStyleItalic(italic);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }


    /**Change the Font Colour of a Column, if it exists. Receive a message on the success of the edit
     * @param columnId The ID of the Column whose title should be changed
     * @param fontColour Font Colour which should replace the old Font Colour of the Column
     * @return receive a message indicating the Font Colour has changed, if the Column exists. If it doesn't,
     * receive an appropriate response to the client.
     */
    @PutMapping("/editColumnFontColour/{columnId}/{fontColour}")
    @ResponseBody public ResponseEntity<Column> editColumnFontColour
    (@PathVariable("columnId") long columnId, @PathVariable("fontColour") String fontColour){
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnRepository.findById(columnId).get();
        column.setFontColour(fontColour);
        columnRepository.save(column);
        return ResponseEntity.ok(column);
    }











    /**
     * @param columnId the id of the column that needs to be removed
     * @return a response which says that the column was removed from the database or not.
     */
    @DeleteMapping("/deleteColumn/{columnId}")
    @ResponseBody public ResponseEntity<Column> deleteColumn(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column columnToDelete = columnRepository.findById(columnId).get();
        long boardId = columnToDelete.getBoardId();
        Integer position = columnToDelete.getPosition();

        // Delete corresponding cards
        List<Card> cards = getCardsByColumnId(columnId).getBody();

        for (Card card : cards) {
            cardController.deleteCard(card.getId());
        }

        // Delete the Column
        columnRepository.deleteById(columnId);

        // Decrement the positions of all Columns in front of the deleted Column
        if (position != null) {
            List<Column> columnsToUpdate = columnRepository.findByBoardIdAndPositionGreaterThan(boardId, position);
            for (Column columnToUpdate : columnsToUpdate) {
                int currentPosition = columnToUpdate.getPosition();
                columnToUpdate.setPosition(currentPosition - 1);
                columnRepository.save(columnToUpdate);
            }
        }
        return ResponseEntity.ok(columnToDelete);
    }

    /**
     * Gives all the cards given a certain columnId
     * @param columnId the columnId you want the cards from
     * @return a List with all the corresponding cards
     */
    @GetMapping("/getCardsByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<List<Card>> getCardsByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnRepository.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(cardRepository.findCardsByColumnId(columnId));
    }
}
