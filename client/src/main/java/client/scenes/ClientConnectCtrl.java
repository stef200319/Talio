package client.scenes;

import client.utils.LongPolling;
import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientConnectCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;
    private final LongPolling longPolling;

    @FXML
    private TextField serverAddress;
    @FXML
    private Label warningLabel;

    /**
     * @param server
     * @param mainCtrl
     * @param websocket
     * @param longPolling
     */
    @Inject
    public ClientConnectCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket, LongPolling longPolling) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocket = websocket;
        this.longPolling = longPolling;
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
        try {
            server.setSERVER(serverAddress.getText());
            longPolling.setSERVER(serverAddress.getText());
            websocket.setSERVER(serverAddress.getText());
            websocket.connectSession();

            mainCtrl.showWorkspace();
        } catch (Exception e) {
            warningLabel.setVisible(true);
        }
    }

    /**
     * Show the help screen
     */
    public void showHelpScreen(){mainCtrl.showHelpScreen();}

    /**
     * Makes the warning label invisible
     */
    public void setWarning() {
        warningLabel.setVisible(false);
    }
}
