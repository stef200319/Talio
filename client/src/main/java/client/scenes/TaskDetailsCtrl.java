package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class TaskDetailsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @Inject
    public TaskDetailsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void goBoardOverview() {
        mainCtrl.showBoardOverview();
    }
}
