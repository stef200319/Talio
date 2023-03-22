package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddTaskCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField taskName;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    AddTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * cancel adding and return to overview
     */
    public void cancel() {
        taskName.clear();
        mainCtrl.showBoardOverview();
    }

    /**
     * will return a new list with title listName (once database running)
     */
    public Card getCard() {
        var c = taskName.getText();
        if(c.equals(""))
            c="New List";
        return new Card(c,1L);
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        server.addCard(getCard());
        taskName.clear();
        mainCtrl.showBoardOverview();
    }

    /**
     * Show Task Details scene
     */
    public void showTaskDetails() {
        mainCtrl.showTaskDetails();
    }
}
