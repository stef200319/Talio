package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Column;

public class ConfirmDeleteColumnCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Column columnToDelete;

    /**
     * @param server server we're connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public ConfirmDeleteColumnCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
