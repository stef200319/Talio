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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
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
    private HBox columnContainer;

    @FXML
    private Button createBoardButton;

    @FXML
    private Button myBoardsButton;

    @FXML
    private Button joinBoardButton;


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
     * Method that shows the workspace page containing all the boards on screen
     */
    public void myBoards() {mainCtrl.showWorkspace();}

    /**
     * Method that shows the workspace page containing all the boards on screen
     */
    public void createBoard() {mainCtrl.showCreateBoard();}


    /**
     * Method that refreshes the board
     */
    public void refresh() {
        columnContainer.getChildren().clear();
        List<Column> columns = server.getColumnsByBoardId(boardID);
        for(int i=0;i<columns.size();i++)
            createList(columns.get(i));
    }

    /**
     * Method that showcases the column on the board
     * @param c column to be showcased
     */

// Old create list
//    public void createList(Column c) {
//        VBox list=new VBox();
//        list.setPrefWidth(200);
//        list.setAlignment(Pos.CENTER);
//
//        Label title = new Label(c.getTitle());
//        title.setFont(new Font(20));
//
//        list.getChildren().add(title);
//
//        List<Card> cards = server.getCardsByColumnId(c.getId());
//        for(int i=0;i<cards.size();i++) {
//            Label s = new Label(cards.get(i).getTitle());
//            list.getChildren().add(s);
//        }
//
//        Button b = new Button("Add task");
//        b.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                mainCtrl.showAddTask();
//            }
//        });
//        list.getChildren().add(b);
//
//        columnContainer.getChildren().add(list);
//    }

    public void createList(Column c) {
        VBox list=new VBox();
        list.setPrefWidth(400); // Set preferred width to 400 pixels
        list.setPrefHeight(600); // Set preferred height to 600 pixels
        list.setMaxWidth(800); // Set max width to 800 pixels
        list.setAlignment(Pos.CENTER);

        Button delete = new Button("X");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                server.deleteColumn(c);
            }
        });
        HBox deleteBox = new HBox();
        deleteBox.setAlignment(Pos.TOP_RIGHT);
        deleteBox.getChildren().add(delete);
        list.getChildren().add(deleteBox);

        Label title = new Label(c.getTitle());
        title.setFont(new Font(20));

        list.getChildren().add(title);

        List<Card> cards = server.getCardsByColumnId(c.getId());
        VBox cardContainer = new VBox();
        cardContainer.setSpacing(10);
        for(int i=0;i<cards.size();i++) {
            Label s = new Label(cards.get(i).getTitle());
            cardContainer.getChildren().add(s);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(cardContainer);
        scrollPane.setPrefWidth(380); // Set preferred width to 380 pixels
        scrollPane.setPrefHeight(500); // Set preferred height to 500 pixels
        scrollPane.setFitToWidth(true);

        list.getChildren().add(scrollPane);

        Button b = new Button("Add task");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                mainCtrl.showAddTask(c.getId());
            }
        });
        list.getChildren().add(b);

        columnContainer.getChildren().add(list);
    }



}