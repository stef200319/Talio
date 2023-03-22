package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WorkspaceCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField boardTitle;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * show the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showBoardOverview();
    }

    /**
     * will return a new board with title boardTitle
     * @return new Board to database
     */

    public Board getBoard() {
        var l = boardTitle.getText();
        if(l.equals(""))
            l="New Board";
        return new Board(l);
    }

    /**
     * adds board to server
     */

    public void add() {
        server.addBoard(getBoard());
        boardTitle.clear();
    }
}
