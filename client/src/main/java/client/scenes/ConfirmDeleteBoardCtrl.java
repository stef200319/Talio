package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;

public class ConfirmDeleteBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Board boardToDelete;

    /**
     * @param server server we're connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public ConfirmDeleteBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
