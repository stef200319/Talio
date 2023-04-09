package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.google.inject.Inject;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class EditBoardTitleCtrl implements Initializable {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;
    private final Websocket websocket;

    private Long boardToEditID;

    private Board boardToEdit;

    @FXML
    private Label currentTitle;
    @FXML
    private TextField newTitle;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public EditBoardTitleCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
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
        newTitle.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirm();
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                cancel();
            }
        });
    }

    /**
     * cancel edit and return to overview
     */
    public void cancel(){
        newTitle.clear();
        mainCtrl.showBoardOverview(boardToEditID);
    }

    /**
     * Set the boardID of the board
     * @param boardToEditID boardID
     */

    public void setBoardToEditID(long boardToEditID) {
        this.boardToEditID = boardToEditID;
        this.boardToEdit = server.getBoardByID(boardToEditID);
        this.currentTitle.setText(boardToEdit.getTitle());
    }

    /**
     * Show Board edit page
     */

    public void showBoardCustomize() {
        mainCtrl.showCustomizeBoard(boardToEditID);
    }

    /**
     * will return a string with the new title
     * @return new title
     */
    public String getTitle(){
        var title = newTitle.getText();
        if(!title.equals(""))
            return title;
        return null;
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        if(getTitle() != null) {
            server.editBoardTitle(server.getBoardByID(boardToEditID), getTitle());
            websocket.send("/app/updateBoard", boardToEdit);
            newTitle.clear();
        }
        mainCtrl.showBoardOverview(boardToEditID);
    }


}
