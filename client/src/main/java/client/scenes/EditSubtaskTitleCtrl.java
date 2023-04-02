package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditSubtaskTitleCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card cardToShow;

    private Subtask subtaskToShow;

    @FXML
    private Label currentTitle;

    @FXML
    private TextField newTitle;



    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public EditSubtaskTitleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * Sets the Title of the current Card which will be displayed
     * @param cardToShow the Card whose current Title needs to be shown
     */
    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
    }

    /**
     * Sets the Title of the current Subtask which will be displayed
     * @param subtaskToShow the Subtask whose current Title needs to be shown
     */
    public void setSubtaskToShow(Subtask subtaskToShow) {
        this.subtaskToShow = subtaskToShow;
        this.currentTitle.setText(subtaskToShow.getTitle());
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
    public String getTitle() {
        String l = newTitle.getText();
        if(!l.equals("")){
            return l;
        }
        else{
            return "Unnamed Subtask";
        }
    }

    /**
     * Edits the title of the Card and returns to the Card's details
     */
    public void confirm() {
        if(getTitle() != null) {
            server.editSubtaskTitle(subtaskToShow, getTitle());
            newTitle.clear();
            mainCtrl.showTaskDetails(cardToShow);
        }
    }
}
