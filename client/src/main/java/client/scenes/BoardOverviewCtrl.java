package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Column;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private long boardID = Long.MIN_VALUE;

    @FXML
    private HBox columnContainer;

    @FXML
    private Button createBoardButton;

    @FXML
    private Button myBoardsButton;

    @FXML
    private Button joinBoardButton;

    @FXML
    private Label boardName;


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
        mainCtrl.showListAdd((long) boardID);
    }

    /**
     * Method that shows the workspace page containing all the boards on screen
     */
    public void myBoards() {mainCtrl.showWorkspace();}

    /**
     * Method that shows the workspace page containing all the boards on screen
     */
    public void createBoard() {mainCtrl.showCreateBoard(boardID);}


    /**
     * Method that refreshes the board
     */
    public void refresh() {
        columnContainer.getChildren().clear();
        if(boardID == Long.MIN_VALUE ||
            !server.getAllBoardsWithoutServers().stream().map(Board::getId).collect(Collectors.toList()).contains(boardID)){
            return;
        }
        Board currentBoard = server.getBoardByID(boardID);
        boardName.setText(currentBoard.getTitle());
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
        list.setStyle("-fx-border-color: black");
        list.setPadding(new Insets(5));
        list.setPrefWidth(400); // Set preferred width to 400 pixels
        list.setPrefHeight(600); // Set preferred height to 600 pixels
        list.setMaxWidth(800); // Set max width to 800 pixels
        list.setMinWidth(200); //Set min width to 200
        list.setAlignment(Pos.CENTER);

        Button delete = new Button("X");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                server.deleteColumn(c);
            }
        });
        Button editTitle = new Button("Edit");
        editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showEditList(c, boardID);
            }
        });
        HBox deleteBox = new HBox(5);
        deleteBox.setAlignment(Pos.TOP_RIGHT);
        deleteBox.getChildren().add(editTitle);
        deleteBox.getChildren().add(delete);
        list.getChildren().add(deleteBox);

        Label title = new Label(c.getTitle());
        title.setFont(new Font(20));

        list.getChildren().add(title);

        List<Card> cards = server.getCardsByColumnId(c.getId());
        VBox cardContainer = new VBox();
        cardContainer.setSpacing(10);
        for(int i=0;i<cards.size();i++) {
            HBox card = new HBox(80);                   //card box
            card.setAlignment(Pos.CENTER_RIGHT);
            card.setStyle("-fx-background-color:#FEF9D9");
            card.setStyle("-fx-border-style: solid");
            card.setStyle("-fx-border-radius: 50px");
            card.setStyle("-fx-border-color: grey");

            Label s = new Label(cards.get(i).getTitle());       //title of the card
            card.getChildren().add(s);

            VBox cardButtons = new VBox(5);             //box for details and delet buttons
            cardButtons.setAlignment(Pos.CENTER);

            Button details = new Button("Details");
            details.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mainCtrl.showTaskDetails();
                }
            });

            Button deleteCard = new Button("X");
            int finalI = i;
            deleteCard.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    server.deleteCard(cards.get(finalI));
                }
            });

            cardButtons.getChildren().add(deleteCard);
            cardButtons.getChildren().add(details);

            card.getChildren().add(cardButtons);

            cardContainer.getChildren().add(card);
        }

        cardContainer.setPrefWidth(380); // Set preferred width to 380 pixels
        cardContainer.setPrefHeight(500); // Set preferred height to 500 pixels
        list.getChildren().add(cardContainer);

        Button b = new Button("Add task");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showAddTask(c.getId(),boardID );
            }
        });
        list.getChildren().add(b);

        columnContainer.getChildren().add(list);
    }

    /**
     * Set the boardID of a board
     * @param boardID the boardID of the board that list will be added to
     */

    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }
}