package client.scenes;

import client.utils.ServerUtils;
import commons.Column;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class EditListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Column columnToEdit;

    @FXML
    private TextField listName;

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
     * Sets the column which will be edited
     * @param c new column to edit
     */
    public void setColumnToEdit(Column c) {
        this.columnToEdit = c;
    }

    /**
     * Getter for testing
     * @return column to edit
     */
    public Column getColumnToEdit() {
        return columnToEdit;
    }

    /**
     * cancel edit and return to overview
     */
    public void cancel() {
        listName.clear();
        mainCtrl.showBoardOverview();
    }

    /**
     * will return a string with the new title
     * @return new title
     */
    public String getTitle() {
        var l = listName.getText();
        if(!l.equals(""))
            return l;
        return null;
    }

    /**
     * adds list to server and returns to overview
     */
    public void confirm() {
        if(getTitle() != null) {
            server.editColumnTitle(columnToEdit, getTitle());
            listName.clear();
            mainCtrl.showBoardOverview();
        }
    }

    /**
     * Setter for testing
     * @param listName the textfield
     */
    public void setListName(TextField listName) {
        this.listName = listName;
    }

}
