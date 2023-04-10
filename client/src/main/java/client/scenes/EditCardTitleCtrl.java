package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Card;
import commons.Column;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCardTitleCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;
    private boolean register;

    private Card cardToShow;

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
    public EditCardTitleCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server=server;
        this.mainCtrl=mainCtrl;
        this.websocket = websocket;
        register = false;
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
        this.currentTitle.setText(cardToShow.getTitle());
    }

    /**
     * Cancel edit and return to Card Details
     */
    public void cancel() {
        mainCtrl.showTaskDetails(cardToShow);
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
            cardToShow = server.editCardTitle(cardToShow, getTitle());
            websocket.send("/app/updateCard", cardToShow);
            newTitle.clear();
            mainCtrl.showTaskDetails(cardToShow);
        }
    }

    /**
     * Shows the board overview
     */
    public void showBoardOverview() {
        long columnId = cardToShow.getColumnId();
        Column c = server.getColumnByColumnId(columnId);
        long boardId = c.getBoardId();
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Registering for websocket messages
     */
    public void registerForMessages() {
        if(register == false) {
            websocket.registerForMessages("/topic/deleteCard", Card.class, card -> {
                System.out.println("Websocket delete card working");
                Platform.runLater(() -> {
                    if (cardToShow != null && !server.existsByIdCard(cardToShow.getId()))
                        showBoardOverview();
                });
            });

            websocket.registerForMessages("/topic/updateCard", Card.class, c -> {
                System.out.println("Websocket card working");

                Platform.runLater(() -> {
                    if (cardToShow != null && server.existsByIdCard(cardToShow.getId())) {
                        cardToShow = server.getCardById(cardToShow.getId());
                        setCardToShow(cardToShow);
                    }
                });
            });
            register = true;
        }
    }
}
