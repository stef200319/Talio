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
     * private event handler for a key event that listens
     *       for the "Esc" key to be pressed
     * when the "Esc" key is pressed, the method showBoardOverview()
     *       is called to switch to the Board Overview scene
     */
    private EventHandler<KeyEvent> backToOverview = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ESCAPE)
            {
                showBoardOverview();
            }
        }
    };

    /**
     * @return the backToOverview event handler
     */
    public EventHandler<KeyEvent> getBackToOverview() {
        return backToOverview;
    }

    /**
     * show the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showOverview();
    }
}
