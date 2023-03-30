package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateBoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField boardName;


    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public CreateBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * cancel board creation and return to overview
     */
    public void cancel() {
        boardName.clear();
//        mainCtrl.showBoardOverview(1l);
    }

    /**
     * will return a new list with title listName
     * @return new Column to database
     */
    public Board getBoard() {
        var name = boardName.getText();
        if(name.equals(""))
            name="New Board";
        return new Board(name);
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        server.addBoard(getBoard());
        boardName.clear();
//        mainCtrl.showBoardOverview(1l);
    }

}
