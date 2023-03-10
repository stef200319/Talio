package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class TaskManagementCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public TaskManagementCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }
    /**
     * show the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showBoardOverview();
    }
}
