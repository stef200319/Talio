package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
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
    public void getList() {
        var c = taskName.getText();
        //return new card (needs backend)
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        //add card to database (needs backend)
        taskName.clear();
        mainCtrl.showBoardOverview();
    }
}
