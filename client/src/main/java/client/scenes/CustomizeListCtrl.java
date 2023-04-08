package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Column;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomizeListCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Column columnToShow;
    private long boardID;


    @FXML
    private ColorPicker listBgColourPicker;

    @FXML
    private ColorPicker listBorderColourPicker;

    @FXML
    private ColorPicker listFontColourPicker;

    @FXML
    private CheckBox listFontStyleBold;

    @FXML
    private CheckBox listFontStyleItalic;

    @FXML
    private ChoiceBox<String> listFontType;






    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public CustomizeListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * Sets the Column whose need to be customized
     * @param columnToShow the Column which needs to be customized
     */
    public void setColumnToShow(Column columnToShow) {
        this.columnToShow = columnToShow;

        String bgcolourString = columnToShow.getBgColour();
        if (bgcolourString.startsWith("#")) {
            bgcolourString = bgcolourString.substring(1);
        }
        this.listBgColourPicker.setValue(Color.web(bgcolourString));


        String bordercolourString = columnToShow.getBorderColour();
        if (bordercolourString.startsWith("#")) {
            bordercolourString = bordercolourString.substring(1);
        }
        this.listBorderColourPicker.setValue(Color.web(bordercolourString));

        String fontColourString = columnToShow.getFontColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.listFontColourPicker.setValue(Color.web(fontColourString));

        listFontType.setValue(columnToShow.getFontType());
        listFontStyleBold.setSelected(columnToShow.isFontStyleBold());
        listFontStyleItalic.setSelected(columnToShow.isFontStyleItalic());
    }

    /**
     * Cancel edit and return to Column Details
     */
    public void cancel() {
        mainCtrl.showEditList(columnToShow,boardID);
    }


    /**
     * @return Get a string representation of the Column's New Background colour in Hexcode
     */
    public String getNewColumnBgColour(){
        return colourToHexCode(this.listBgColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Columns New Border colour in Hexcode
     */
    public String getNewColumnBorderColour(){
        return colourToHexCode(this.listBorderColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Columns New Font colour in Hexcode
     */
    public String getNewColumnFontColour(){
        return colourToHexCode(this.listFontColourPicker.getValue());
    }
    /**
     * @return Get a Boolean representation of whether the Column has been edited to be Bold
     */
    public boolean getColumnFontStyleBold(){
        return listFontStyleBold.isSelected();
    }
    /**
     * @return Get a Boolean representation of whether the Column has been edited to be Italic
     */
    public boolean getColumnFontStyleItalic(){
        return listFontStyleItalic.isSelected();
    }
    /**
     * @return Get a string representation of the Columns New Font Type
     */
    public String getColumnFontType(){
        return listFontType.getValue();
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
     * Set the Background Colour of this Column to the Default Column Background Colour
     */
    public void setDefaultColumnBgColour(){
        String fontColourString = columnToShow.getDefaultBgColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.listBgColourPicker.setValue(Color.web(fontColourString));
        server.editColumnBackgroundColour(columnToShow, columnToShow.getDefaultBgColour());
    }

    /**
     * Set the Border Colour of this Column to the Default Column Border Colour
     */
    public void setDefaultColumnBorderColour(){
        String fontColourString = columnToShow.getDefaultBorderColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.listBorderColourPicker.setValue(Color.web(fontColourString));
        server.editColumnBorderColour(columnToShow, columnToShow.getDefaultBorderColour());
    }

    /**
     * Set the Font-Type of this Column to the Default Column Font-Type
     */
    public void setDefaultColumnFontType(){
        listFontType.setValue(columnToShow.getDefaultFontType());
        server.editColumnFontType(columnToShow, columnToShow.getDefaultFontType());
    }

    /**
     * Set the Font Colour of this Column to the Default Column Font Colour
     */
    public void setDefaultColumnFontColour(){
        String fontColourString = columnToShow.getDefaultFontColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.listFontColourPicker.setValue(Color.web(fontColourString));
        server.editColumnFontColour(columnToShow, columnToShow.getDefaultFontColour());
    }

    /**
     * Edits the title of the Column and returns to the Column's details
     */
    public void confirm() {
        server.editColumnBackgroundColour(columnToShow, getNewColumnBgColour());
        server.editColumnBorderColour(columnToShow, getNewColumnBorderColour());
        server.editColumnFontType(columnToShow, getColumnFontType());
        server.editColumnFontStyleBold(columnToShow, getColumnFontStyleBold());
        server.editColumnFontStyleItalic(columnToShow, getColumnFontStyleItalic());
        server.editColumnFontColour(columnToShow, getNewColumnFontColour());

        mainCtrl.showEditList(columnToShow,boardID);
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
        listFontType.getItems().addAll("Arial", "Times New Roman", "Verdana", "Tahoma", "Courier New",
                "Segoe UI", "Calibri", "Helvetica", "Georgia", "Trebuchet MS");

    }

    /**
     * Set the boardID of a board
     * @param boardID the boardID of the board that the List belongs to.
     */
    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }
}
