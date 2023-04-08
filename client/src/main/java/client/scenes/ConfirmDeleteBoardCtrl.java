package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Board;

public class ConfirmDeleteBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;

    private Board boardToDelete;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public ConfirmDeleteBoardCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.websocket = websocket;
    }

    /**
     * Cancels deletion and returns to wo
     */
    public void cancel() {
        mainCtrl.showWorkspace();
    }

    /**
     * Deletes the board and returns to workspace
     */
    public void confirm() {
        server.deleteBoard(boardToDelete);
        websocket.send("app/updateBoard", boardToDelete);
        mainCtrl.showWorkspace();
    }

    /**
     * Sets the board that might be deleted
     * @param b board
     */
    public void setBoardToDelete(Board b) {
        this.boardToDelete = b;
    }
}
