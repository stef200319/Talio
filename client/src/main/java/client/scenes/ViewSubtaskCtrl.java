package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ViewSubtaskCtrl {

    private Card cardToShow;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private VBox subtaskList;

    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public ViewSubtaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Set the Card whose details have to be displayed
     * @param cardToShow the Card whose details have to be displayed
     */

    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
//        Implement get subtasks
    }


    /**
     * show the board overview
     */
    public void showCardDetails() {
        mainCtrl.showTaskDetails(cardToShow);
    }
}
