package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSubtaskCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Card cardToAddTo;

    @FXML
    private TextField title;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public AddSubtaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        title.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
     * Sets the card that the subtask needs to be added to
     * @param c card
     */
    public void setCard(Card c) {
        this.cardToAddTo = c;
    }

    /**
     * Cancel and return to subtask overview
     */
    public void cancel() {
        title.clear();
        mainCtrl.showViewSubtask(cardToAddTo);
    }

    /**
     * Reads the title
     * @return the title for the new subtask
     */
    public String getTitle() {
        String t = title.getText();
        if(!t.equals(""))
            return t;
        return "New Subtask";
    }

    /**
     * Adds the subtask to the database and returns to subtask overview
     */
    public void confirm() {
        String t = getTitle();
        title.clear();
        cardToAddTo = server.addSubtask(cardToAddTo.getId(), t);
        mainCtrl.showViewSubtask(cardToAddTo);
    }
}
