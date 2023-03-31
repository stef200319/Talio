package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class EditBoardTitleCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private Board boardToEdit;
    @FXML
    private TextField newTitle;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public EditBoardTitleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }
    /**
     * cancel edit and return to overview
     */
    public void cancel(){
        newTitle.clear();
        mainCtrl.showBoardOverview();
    }

    /**
     * board to be edited
     * @param board
     */
    public void setBoardToEdit(Board board)
    {
        this.boardToEdit = board;
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
            //server

            newTitle.clear();
            mainCtrl.showBoardOverview();
        }
    }





}
