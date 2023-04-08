package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Card;
import commons.Subtask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSubtaskTitleCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;

    private Card cardToShow;

    private Subtask subtaskToShow;

    @FXML
    private Label currentTitle;

    @FXML
    private TextField newTitle;



    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public EditSubtaskTitleCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server=server;
        this.mainCtrl=mainCtrl;
        this.websocket = websocket;
    }

    /**
     * Method that is once executed when the application starts that includes event listener
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER)
                    confirm();
                else if (event.getCode() == KeyCode.ESCAPE)
                    cancel();
            }
        });
    }

    /**
     * Sets the Title of the current Card which will be displayed
     * @param cardToShow the Card whose current Title needs to be shown
     */
    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
    }

    /**
     * Sets the Title of the current Subtask which will be displayed
     * @param subtaskToShow the Subtask whose current Title needs to be shown
     */
    public void setSubtaskToShow(Subtask subtaskToShow) {
        this.subtaskToShow = subtaskToShow;
        this.currentTitle.setText(subtaskToShow.getTitle());
    }

    /**
     * Cancel edit and return to subtask overview
     */
    public void cancel() {
        mainCtrl.showViewSubtask(cardToShow);
    }

    /**
     * will return a string with the new title
     * @return new title
     */
    public String getTitle() {
        String l = newTitle.getText();
        if(!l.equals("")){
            return l;
        }
        else{
            return currentTitle.getText();
        }
    }

    /**
     * Edits the title of the Card and returns to the Card's details
     */
    public void confirm() {
        if(getTitle() != null) {
            server.editSubtaskTitle(subtaskToShow, getTitle());
            cardToShow = server.getCardById(cardToShow.getId());
            websocket.send("app/updateSubtask", subtaskToShow);
            newTitle.clear();
            mainCtrl.showTaskDetails(cardToShow);
        }
    }
}
