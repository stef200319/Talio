package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskDetailsCtrl {
    private Card cardToShow;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label cardTitle;

    @FXML
    private Label cardDescription;

    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public TaskDetailsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Set the Card whose details have to be displayed
     * @param cardToShow the Card whose details have to be displayed
     */

    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
        cardTitle.setText(cardToShow.getTitle());
        cardDescription.setText(cardToShow.getDescription());
    }

    /**
     * show the page to edit the Card Title
     */
    public void showEditCardTitle(){
        mainCtrl.showEditCardTitle(cardToShow);
    }

    /**
     * show the page to edit the Card Description
     */
    public void showEditCardDescription(){
        mainCtrl.showEditCardDescription(cardToShow);
    }

    /**
     * show the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showBoardOverview();
    }
}
