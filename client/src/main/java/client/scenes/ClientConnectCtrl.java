package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

public class ClientConnectCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public ClientConnectCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
