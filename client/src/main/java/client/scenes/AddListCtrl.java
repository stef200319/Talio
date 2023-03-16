package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class AddListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField listName;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * cancel adding and return to overview
     */
    public void cancel() {
        listName.clear();
        mainCtrl.showBoardOverview();
    }

    /**
     * will return a new list with title listName (once database running)
     */
    public void getList() {
        var l = listName.getText();
        //return new list (needs backend)
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        //add list to database (needs backend)
        String list = listName.getText();
        if(list.equals(""))
            mainCtrl.createList("New List");
        else
            mainCtrl.createList(list);
        listName.clear();
        mainCtrl.showBoardOverview();
    }

}
