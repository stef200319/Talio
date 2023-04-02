package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class HelpCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private Scene previousScene;



    @Inject
    public HelpCtrl(ServerUtils server, MainCtrl mainCtrl)
    {

        this.mainCtrl = mainCtrl;
        this.server = server;

    }


    private EventHandler<KeyEvent> backToPreviousScene = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {

            if(event.getCode() == KeyCode.ESCAPE)

            {
                showPreviousScene();
            }

        }

    };
    public Scene getPreviousScene()
    {
        return null;
    }
    public void setPreviousScene(Scene scene){
        this.previousScene = getPreviousScene();
    }
    public EventHandler<KeyEvent> getBackToPreviousScene()
    {
        return backToPreviousScene;
    }

    public void showPreviousScene(){
        mainCtrl.showPreviousScreen();
        //mainCtrl.showOverview();
    }

}
