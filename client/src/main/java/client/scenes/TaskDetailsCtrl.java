package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class TaskDetailsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public TaskDetailsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     *
     */
    private EventHandler<KeyEvent> switchScene = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ESCAPE)
            {
                showBoardOverview();
            }
        }
    };

    /**
     *
     * @return
     */
    public EventHandler<KeyEvent> getSwitchScene() {
        return switchScene;
    }

    /**
     * show the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showOverview();
    }
}
