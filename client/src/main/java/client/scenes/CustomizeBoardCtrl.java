package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomizeBoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Board boardToShow;
    private long boardID;


    @FXML
    private ColorPicker boardBgColourPicker;

    @FXML
    private ColorPicker boardBorderColourPicker;

    @FXML
    private ColorPicker boardFontColourPicker;

    @FXML
    private CheckBox boardFontStyleBold;

    @FXML
    private CheckBox boardFontStyleItalic;

    @FXML
    private ChoiceBox<String> boardFontType;






    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public CustomizeBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * Sets the Board whose need to be customized
     * @param boardID the Board which needs to be customized
     */
    public void setBoardToShow(long boardID) {
        this.boardID = boardID;
        this.boardToShow= server.getBoardByID(boardID);

        String bgcolourString = boardToShow.getCenterColour();
        if (bgcolourString.startsWith("#")) {
            bgcolourString = bgcolourString.substring(1);
        }
        this.boardBgColourPicker.setValue(Color.web(bgcolourString));


        String bordercolourString = boardToShow.getSideColour();
        if (bordercolourString.startsWith("#")) {
            bordercolourString = bordercolourString.substring(1);
        }
        this.boardBorderColourPicker.setValue(Color.web(bordercolourString));

        String fontColourString = boardToShow.getFontColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.boardFontColourPicker.setValue(Color.web(fontColourString));

        boardFontType.setValue(boardToShow.getFontType());
        boardFontStyleBold.setSelected(boardToShow.isFontStyleBold());
        boardFontStyleItalic.setSelected(boardToShow.isFontStyleItalic());
    }

    /**
     * Cancel edit and return to Board Edit page
     */
    public void cancel() {
        mainCtrl.showEditBoardTitle(boardID);
    }


    /**
     * @return Get a string representation of the Column's New Background colour in Hexcode
     */
    public String getNewColumnBgColour(){
        return colourToHexCode(this.boardBgColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Columns New Border colour in Hexcode
     */
    public String getNewColumnBorderColour(){
        return colourToHexCode(this.boardBorderColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Columns New Font colour in Hexcode
     */
    public String getNewColumnFontColour(){
        return colourToHexCode(this.boardFontColourPicker.getValue());
    }
    /**
     * @return Get a Boolean representation of whether the Column has been edited to be Bold
     */
    public boolean getColumnFontStyleBold(){
        return boardFontStyleBold.isSelected();
    }
    /**
     * @return Get a Boolean representation of whether the Column has been edited to be Italic
     */
    public boolean getColumnFontStyleItalic(){
        return boardFontStyleItalic.isSelected();
    }
    /**
     * @return Get a string representation of the Columns New Font Type
     */
    public String getColumnFontType(){
        return boardFontType.getValue();
    }


    /**
     * Convert a colour to a Hexcode String representation
     * @param colour Colour to be converted to Hexcode
     * @return Hexcode value of the colour as a String
     */
    public static String colourToHexCode(Color colour) {
        return String.format("#%02X%02X%02X",
                (int) (colour.getRed() * 255),
                (int) (colour.getGreen() * 255),
                (int) (colour.getBlue() * 255));
    }

    /**
     * Set the Center Colour of this Board to the Default Board Center Colour
     */
    public void setDefaultBoardCenterColour(){
        server.editBoardCenterColour(boardToShow, boardToShow.getDefaultCenterColour());
    }

    /**
     * Set the Side Colour of this Board to the Default Board Side Colour
     */
    public void setDefaultBoardSideColour(){
        server.editBoardSideColour(boardToShow, boardToShow.getDefaultSideColour());
    }

    /**
     * Set the Font-Type of this Board to the Default Board Font-Type
     */
    public void setDefaultBoardFontType(){
        server.editBoardFontType(boardToShow, boardToShow.getDefaultFontType());
    }

    /**
     * Set the Font Colour of this Board to the Default Board Font Colour
     */
    public void setDefaultBoardFontColour(){
        server.editBoardFontColour(boardToShow, boardToShow.getDefaultFontColour());
    }

    /**
     * Edits the title of the Column and returns to the Column's details
     */
    public void confirm() {
        server.editBoardCenterColour(boardToShow, getNewColumnBgColour());
        server.editBoardSideColour(boardToShow, getNewColumnBorderColour());
        server.editBoardFontType(boardToShow, getColumnFontType());
        server.editBoardFontStyleBold(boardToShow, getColumnFontStyleBold());
        server.editBoardFontStyleItalic(boardToShow, getColumnFontStyleItalic());
        server.editBoardFontColour(boardToShow, getNewColumnFontColour());

        mainCtrl.showEditBoardTitle(boardID);
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardFontType.getItems().addAll("Arial", "Times New Roman", "Verdana", "Tahoma", "Courier New",
                "Segoe UI", "Calibri", "Helvetica", "Georgia", "Trebuchet MS");

    }


}
