package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
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





    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public CustomizeCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
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

    public String getNewCardBgColour(){
        return colourToHexCode(this.cardBgColourPicker.getValue());
    }

    public String getNewCardBorderColour(){
        return colourToHexCode(this.cardBorderColourPicker.getValue());
    }

    public boolean getCardFontStyleBold(){
        return cardFontStyleBold.isSelected();
    }
    public boolean getCardFontStyleItalic(){
        return cardFontStyleItalic.isSelected();
    }

    public String getCardFontType(){
        return cardFontType.getValue();
    }

    public static String colourToHexCode(Color colour) {
        return String.format("#%02X%02X%02X",
                (int) (colour.getRed() * 255),
                (int) (colour.getGreen() * 255),
                (int) (colour.getBlue() * 255));
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
}
