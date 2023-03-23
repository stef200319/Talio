package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Column;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private int boardID=1;

    @FXML
    private FlowPane flowPane;


    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Method that is once executed when the application starts
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh();
                });
            }
        }, 0, 1000);
    }

    /**
     * Method that shows the quote overview on screen
     */
    public void showQuoteOverview() {
        mainCtrl.showOverview();
    }

    /**
     * Method that shows the task details on screen
     */
    public void showTaskDetails() {
        mainCtrl.showTaskDetails();
    }

    /**
     * Method that shows the add list page on screen
     */
    public void addList() {
        mainCtrl.showListAdd();
    }

    /**
     * Method that shows the add Task page on screen
     */
    public void addTask() {mainCtrl.showAddTask();}


    /**
     * Method that refreshes the board
     */
    public void refresh() {
        flowPane.getChildren().clear();
        List<Column> columns = server.getColumnsByBoardId(boardID);
        for(int i=0;i<columns.size();i++)
            createList(columns.get(i));
    }

    /**
     * Method that creates a new list with the specified name
     * @param c
     */
    public void createList(Column c) {
        VBox list=new VBox();
        list.setPrefWidth(100);
        list.setAlignment(Pos.CENTER);

        Label title = new Label(c.getTitle());
        title.setFont(new Font(20));

        list.getChildren().add(title);

        List<Card> cards = server.getCardsByColumnId(c.getId());
        for(int i=0;i<cards.size();i++) {
            Label s = new Label(cards.get(i).getTitle());
            list.getChildren().add(s);
        }

        Button b = new Button("Add task");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showAddTask();
            }
        });
        list.getChildren().add(b);

        flowPane.getChildren().add(list);
    }
}