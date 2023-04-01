package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class EditCardDescriptionCtrl {

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
        return l;
    }

    /**
     * Edits the title of the Card and returns to the Card's details
     */
    public void confirm() {
        if(getDescription() != null) {
            server.editCardDescription(cardToShow, getDescription());
            newDescription.clear();
            mainCtrl.showTaskDetails(cardToShow);
        }
    }
}
