package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Card;
import commons.Column;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class CustomizeCardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;
    private boolean register;

    private Card cardToShow;

    @FXML
    private ColorPicker cardBgColourPicker;

    @FXML
    private ColorPicker cardBorderColourPicker;

    @FXML
    private ChoiceBox<String> cardFontType;

    @FXML
    private CheckBox cardFontStyleBold;

    @FXML
    private CheckBox cardFontStyleItalic;

    @FXML
    private ColorPicker cardFontColourPicker;





    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public CustomizeCardCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server=server;
        this.mainCtrl=mainCtrl;
        this.websocket = websocket;
        register = false;
    }

    /**
     * Sets the Card whose need to be customized
     * @param cardToShow the Card which needs to be customized
     */
    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;

        String bgcolourString = cardToShow.getBgColour();
        if (bgcolourString.startsWith("#")) {
            bgcolourString = bgcolourString.substring(1);
        }
        this.cardBgColourPicker.setValue(Color.web(bgcolourString));


        String bordercolourString = cardToShow.getBorderColour();
        if (bordercolourString.startsWith("#")) {
            bordercolourString = bordercolourString.substring(1);
        }
        this.cardBorderColourPicker.setValue(Color.web(bordercolourString));

        String fontColourString = cardToShow.getFontColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.cardFontColourPicker.setValue(Color.web(fontColourString));

        cardFontType.setValue(cardToShow.getFontType());
        cardFontStyleBold.setSelected(cardToShow.isFontStyleBold());
        cardFontStyleItalic.setSelected(cardToShow.isFontStyleItalic());
    }

    /**
     * Cancel edit and return to Card Details
     */
    public void cancel() {
        mainCtrl.showTaskDetails(cardToShow);
    }


    /**
     * @return Get a string representation of the Cards New Background colour in Hexcode
     */
    public String getNewCardBgColour(){
        return colourToHexCode(this.cardBgColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Cards New Border colour in Hexcode
     */
    public String getNewCardBorderColour(){
        return colourToHexCode(this.cardBorderColourPicker.getValue());
    }
    /**
     * @return Get a string representation of the Cards New Font colour in Hexcode
     */
    public String getNewCardFontColour(){
        return colourToHexCode(this.cardFontColourPicker.getValue());
    }
    /**
     * @return Get a Boolean representation of whether the Card has been edited to be Bold
     */
    public boolean getCardFontStyleBold(){
        return cardFontStyleBold.isSelected();
    }
    /**
     * @return Get a Boolean representation of whether the Card has been edited to be Italic
     */
    public boolean getCardFontStyleItalic(){
        return cardFontStyleItalic.isSelected();
    }
    /**
     * @return Get a string representation of the Cards New Font Type
     */
    public String getCardFontType(){
        return cardFontType.getValue();
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
     * Set the Background Colour of this Card to the Default Card Background Colour
     */
    public void setDefaultCardBgColour(){
        String fontColourString = cardToShow.getDefaultBgColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.cardBgColourPicker.setValue(Color.web(fontColourString));

        server.editCardBackgroundColour(cardToShow, cardToShow.getDefaultBgColour());
        websocket.send("/app/updateCard", cardToShow);
    }

    /**
     * Set the Border Colour of this Card to the Default Card Border Colour
     */
    public void setDefaultCardBorderColour(){
        String fontColourString = cardToShow.getDefaultBorderColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.cardBorderColourPicker.setValue(Color.web(fontColourString));
        server.editCardBorderColour(cardToShow, cardToShow.getDefaultBorderColour());
        websocket.send("/app/updateCard", cardToShow);
    }

    /**
     * Set the Font-Type of this Card to the Default Card Font-Type
     */
    public void setDefaultCardFontType(){
        cardFontType.setValue(cardToShow.getDefaultFontType());
        server.editCardFontType(cardToShow, cardToShow.getDefaultFontType());
        websocket.send("/app/updateCard", cardToShow);
    }

    /**
     * Set the Font Colour of this Card to the Default Card Font Colour
     */
    public void setDefaultCardFontColour(){
        String fontColourString = cardToShow.getDefaultFontColour();
        if (fontColourString.startsWith("#")) {
            fontColourString = fontColourString.substring(1);
        }
        this.cardFontColourPicker.setValue(Color.web(fontColourString));
        server.editCardFontColour(cardToShow, cardToShow.getDefaultFontColour());
        websocket.send("/app/updateCard", cardToShow);
    }

    /**
     * Edits the title of the Card and returns to the Card's details
     */
    public void confirm() {
        server.editCardBackgroundColour(cardToShow, getNewCardBgColour());
        server.editCardBorderColour(cardToShow, getNewCardBorderColour());
        server.editCardFontType(cardToShow, getCardFontType());
        server.editCardFontStyleBold(cardToShow, getCardFontStyleBold());
        server.editCardFontStyleItalic(cardToShow, getCardFontStyleItalic());
        server.editCardFontColour(cardToShow, getNewCardFontColour());
        websocket.send("/app/updateCard", cardToShow);

        mainCtrl.showTaskDetails(cardToShow);
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
        cardFontType.getItems().addAll("Arial", "Times New Roman", "Verdana", "Tahoma", "Courier New",
                "Segoe UI", "Calibri", "Helvetica", "Georgia", "Trebuchet MS");


    }

    /**
     * Shows the board overview
     */
    public void showBoardOverview() {
        long columnId = cardToShow.getColumnId();
        Column c = server.getColumnByColumnId(columnId);
        long boardId = c.getBoardId();
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Registering for websocket messages
     */
    public void registerForMessages() {
        if(register == false) {
            websocket.registerForMessages("/topic/deleteCard", Card.class, card -> {
                System.out.println("Websocket delete card working");
                Platform.runLater(() -> {
                    if (cardToShow != null)
                        if (!server.existsByIdCard(cardToShow.getId()))
                            showBoardOverview();
                });
            });
            register = true;
        }
    }

}
