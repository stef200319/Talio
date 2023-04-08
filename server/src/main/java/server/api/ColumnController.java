package server.api;

import commons.Card;
import commons.CardTag;
import commons.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.ColumnRepository;
import server.services.BoardService;
import server.services.CardService;
import server.services.ColumnService;

import java.util.List;

@RestController
@RequestMapping("/column")
public class ColumnController {

    private final ColumnService columnService;
    private final BoardService boardService;
    private final CardService cardService;


    private final ColumnRepository columnRepository;



    /**
     *
     * @param columnRepository the data container which includes all the columns
     * @param columnService the service used for the operations which use the column data access object
     * @param boardService the service used for the operations which use the board data access object
     * @param cardService the service used for the operations which use the card data access object

     */
    public ColumnController(ColumnRepository columnRepository,
                            ColumnService columnService, BoardService boardService, CardService cardService) {
        this.columnRepository = columnRepository;
        this.columnService = columnService;
        this.boardService = boardService;
        this.cardService = cardService;
    }


    /**
     * @return all columns in the database
     */
    @GetMapping("/getAllColumns")
    @ResponseBody public ResponseEntity<List<Column>> getAllColumns() {
        List<Column> columns = columnService.getAll();
        return columns.size() > 0? ResponseEntity.ok(columns) : ResponseEntity.notFound().build();
    }

//    /**
//     * Adds a new column to the board and sends the updated column list to the "/topic/column" topic.
//     *
//     * @param c the column to be added to the board
//     *
//     * @return the added column
//     */
//
//    @MessageMapping("/column/addColumn")
//    @SendTo("/topic/column")
//    public Column addMessage(Column c) {
//        addColumn(c.getTitle(), c.getId());
//        return c;
//    }

    /**
     * @param columnId the id of the column which will be retrieved
     * @return the column according to the input id
     */
    @GetMapping("/getColumnByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<Column> getColumnByColumnId(@PathVariable("columnId") long columnId) {
        Column column = columnService.getById(columnId);
        return column != null?ResponseEntity.ok(column) : ResponseEntity.notFound().build();
    }

    /**
     * @param title The title of the board that needs to be added to the database
     * @param boardId The board on which the column belongs
     * @return A response entity of the saved column
     */
    @PostMapping("/addColumn/{title}/{boardId}")
    @ResponseBody public ResponseEntity<Column> addColumn(@PathVariable("title") String title,
                                                        @PathVariable("boardId") Long boardId) {
        if (title == null || !boardService.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnService.add(title, boardId);
        return ResponseEntity.ok(column);
    }

    /**
     * @param columnId the id of the column which should be updated
     * @param title the new title of the column
     * @return whether the column was successfully updated
     */
    @PutMapping("/editColumnTitle/{columnId}/{title}")
    @ResponseBody public ResponseEntity<Column> editColumnTitle(@PathVariable("columnId") long columnId,
                                                                @PathVariable("title") String title) {
        if (!columnService.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        Column column = columnService.update(title, columnId);
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

        Column deletedColumn = columnService.deleteByColumnId(columnId);

        if (deletedColumn == null) {
            return ResponseEntity.badRequest().build();
        }

        // Delete corresponding cards
        cardService.deleteByColumnId(columnId);

        return ResponseEntity.ok(deletedColumn);
    }

    /**
     * Gives all the cards given a certain columnId
     * @param columnId the columnId you want the cards from
     * @return a List with all the corresponding cards
     */
    @GetMapping("/getCardsByColumnId/{columnId}")
    @ResponseBody public ResponseEntity<List<Card>> getCardsByColumnId(@PathVariable("columnId") long columnId) {
        if (!columnService.existsById(columnId)) {
            return ResponseEntity.badRequest().build();
        }

        List<Card> cards = cardService.getByColumnId(columnId);
        return ResponseEntity.ok(cards);
    }

    /**
     * Message mapping for websockets
     * @param column column that was changed
     * @return column
     */
    @MessageMapping("/updateColumn")
    @SendTo("/topic/updateColumn")
    public Column updateColumn(Column column) {
        return column;
    }
}
