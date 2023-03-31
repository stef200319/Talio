package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private long boardID;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boardName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirm();
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                cancel();
            }
        });
    }

    /**
     * cancel board creation and return to overview
     */
    public void cancel() {
        boardName.clear();
        mainCtrl.showBoardOverview(boardID);
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
        mainCtrl.showBoardOverview(server.getAllBoardsWithoutServers().get(server.getAllBoardsWithoutServers().size() -1).getId());
    }

    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }
}
