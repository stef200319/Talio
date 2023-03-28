package client.scenes;

import client.utils.ServerUtils;
import commons.Column;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.google.inject.Inject;
import org.w3c.dom.Text;

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

    public void setListName(TextField listName){
        this.listName = listName;
    }
    /**
     * will return a new list with title listName
     * @return new Column to database
     */
    public Column getList() {
        var l = listName.getText();
        if(l.equals(""))
            l="New List";
        return new Column(l,1);
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        server.addColumn(getList());
        listName.clear();
        mainCtrl.showBoardOverview();
    }

}
