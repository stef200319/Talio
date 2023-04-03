package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HelpCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;



    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main controller
     */
    @Inject
    public HelpCtrl(ServerUtils server, MainCtrl mainCtrl)
    {

        this.mainCtrl = mainCtrl;
        this.server = server;

    }


    /**
     * private event handler for a key event that listens
     *       for the "Esc" key to be pressed
     * when the "Esc" key is pressed, the method showPreviousScene()
     *       is called to switch to the previous scene
     */
    private EventHandler<KeyEvent> backToPreviousScene = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {

            if(event.getCode() == KeyCode.ESCAPE)

            {
                showPreviousScene();
            }

        }

    };

    /**
     * @return the backToPreviousScene event handler
     */

    public EventHandler<KeyEvent> getBackToPreviousScene()
    {
        return backToPreviousScene;
    }

    /**
     * method that shows
     * the previous scene
     */

    public void showPreviousScene(){
        mainCtrl.showPreviousScreen();
    }

}
