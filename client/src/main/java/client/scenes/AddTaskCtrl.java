package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTaskCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final Websocket websocket;

    private long columnToAddId;
    private long boardID;

    @FXML
    private TextField taskName;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    AddTaskCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
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
        taskName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirm();
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                cancel();
            }
        });
    }

    /**
     * cancel adding and return to overview
     */
    public void cancel() {
        taskName.clear();
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * will return a new card with title taskName
     * @return new Card to database
     */
    public Card getCard() {
        var c = taskName.getText();
        if(c.equals(""))
            c="New Card";
        return new Card(c,1L);
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        server.addCard(getCard(), columnToAddId);
        websocket.send("/app/card/addCard", getCard());
        taskName.clear();
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * @param taskName updates the task name
     */
    public void setTaskName(TextField taskName){
        this.taskName = taskName;
    }

    /**
     * Set the columnId of a column
     * @param columnToAddId the columnID of the column that card will be deleted from
     */

    public void setColumnToAddId(long columnToAddId) {
        this.columnToAddId = columnToAddId;
    }

    /**
     * Set the boardID of a board
     * @param boardID the boardID of the board
     */
    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }
}
