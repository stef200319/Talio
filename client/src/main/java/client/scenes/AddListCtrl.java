package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class AddListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField boardName;

    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    public void cancel() {
        //clearFields();
        mainCtrl.showOverview();
    }

}
