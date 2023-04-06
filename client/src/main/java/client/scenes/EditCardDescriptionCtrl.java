package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCardDescriptionCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card cardToShow;

    @FXML
    private Label currentDescription;

    @FXML
    private TextArea newDescription;



    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public EditCardDescriptionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * Method that is once executed when the application starts that includes event listener
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newDescription.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER)
                    confirm();
                else if (event.getCode() == KeyCode.ESCAPE)
                    cancel();
            }
        });
    }

    /**
     * Sets the Title of the current Card which will be displayed
     * @param cardToShow the Card whose current Title needs to be shown
     */
    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
        this.currentDescription.setText(cardToShow.getDescription());
    }

    /**
     * Cancel edit and return to Card Details
     */
    public void cancel() {
        mainCtrl.showTaskDetails(cardToShow);
    }

    /**
     * will return a string with the new title
     * @return new title
     */
    public String getDescription() {
        String l = newDescription.getText();
        if(!l.equals(""))
            return l;
        else
            return currentDescription.getText();
    }

    /**
     * Edits the title of the Card and returns to the Card's details
     */
    public void confirm() {
        if (getDescription() != "") {
            cardToShow = server.editCardDescription(cardToShow, getDescription());
        }
        newDescription.clear();
        mainCtrl.showTaskDetails(cardToShow);
    }
}
