package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientConnectCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField serverAddress;

    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public ClientConnectCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showWorkspace();
            }
        });
    }

    /**
     * Show the workspace
     */
    public void showWorkspace() {
        mainCtrl.showWorkspace();
    }
}
