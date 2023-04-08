package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Column;

public class ConfirmDeleteColumnCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;

    private Column columnToDelete;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public ConfirmDeleteColumnCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.websocket = websocket;
    }

    /**
     * Cancels the action of deleting the column and returns to the board overview
     */
    public void cancel() {
        mainCtrl.showBoardOverview(columnToDelete.getBoardId());
    }

    /**
     * Deletes a column and returns to the board overview
     */
    public void confirm() {
        server.deleteColumn(columnToDelete);
        websocket.send("app/updateColumn", columnToDelete);
        mainCtrl.showBoardOverview(columnToDelete.getBoardId());
    }

    /**
     * Sets that column that might be deleted
     * @param c column
     */
    public void setColumnToDelete(Column c) {
        this.columnToDelete = c;
    }

}
