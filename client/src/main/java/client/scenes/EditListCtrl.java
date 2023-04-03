package client.scenes;

import client.utils.ServerUtils;
import commons.Column;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.google.inject.Inject;


import java.net.URL;
import java.util.ResourceBundle;

public class EditListCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Column columnToEdit;
    private long boardID;

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
    public EditListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
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
//        newTitle.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                confirm();
//            }
//            else if (event.getCode() == KeyCode.ESCAPE) {
//                cancel();
//            }
//        });
    }

    /**
     * Sets the column which will be edited
     * @param c new column to edit
     */
    public void setColumnToEdit(Column c) {
        this.columnToEdit = c;
        this.currentTitle.setText(columnToEdit.getTitle());
    }



    /**
     * Show customize list page
     */
    public void showCustomizeList() {
        mainCtrl.showCustomizeList(columnToEdit, boardID);
    }



    /**
     * cancel edit and return to overview
     */
    public void cancel() {
        mainCtrl.showBoardOverview(boardID);
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
            return "---";
        }
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        if(getTitle() != null) {
            server.editColumnTitle(columnToEdit, getTitle());
            newTitle.clear();
        }
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * Set the boardID of a board
     * @param boardID the boardID of the board that list will be added to
     */
    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }

//    public void showCustomizeList(ActionEvent actionEvent) {
//
//    }
}
