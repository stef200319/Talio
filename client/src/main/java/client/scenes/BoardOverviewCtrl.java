package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
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
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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
                refresh();
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
        // For now empty, here we must use the fetch methods for the boards which Benjamin and Ruthvik will implement
    }

    /**
     * Method that creates a new list with the specified name
     * @param text
     */
    public void createList(String text) {
        VBox list=new VBox();
        list.setPrefWidth(100);
        list.setAlignment(Pos.CENTER);

        Label title = new Label(text);
        title.setFont(new Font(20));

        list.getChildren().add(title);

        Label s = new Label("task1");     // this is just a placeholder to see how it looks like
        list.getChildren().add(s);            //

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