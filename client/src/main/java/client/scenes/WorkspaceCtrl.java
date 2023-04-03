package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class WorkspaceCtrl implements Initializable{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @FXML
    private TextField boardTitle;

    @FXML
    private HBox boardContainer;

    @FXML
    private ScrollPane scrollPane;

    /**
     *
     * @param server the server connected to
     * @param mainCtrl the main controller
     */
    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        boardTitle.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                add();
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh();
                });
            }
        }, 0, 1000);
    }

//    /**
//     * show the board overview
//     */
//    public void showBoardOverview() {
//        mainCtrl.showBoardOverview(1l);
//    }

    /**
     * will return a new board with title boardTitle
     * @return new Board to database
     */

    public Board getBoard() {
        var l = boardTitle.getText();
        if(l.equals(""))
            l="New Board";
        return new Board(l);
    }

    /**
     * adds board to server
     */

    public void add() {
        server.addBoard(getBoard());
        refresh();
        boardTitle.clear();
    }



    /**
     * Method that refreshes the workspace
     */
    public void refresh() {
        boardContainer.getChildren().clear();
        List<Board> boards = server.getAllBoardsWithoutServers();
        for(int i=0;i<boards.size();i++)
            createBoard(boards.get(i));
    }

    /**
     * Method that showcases the board on the server
     * @param b board to be showcased
     */

    public void createBoard(Board b) {

        VBox board = new VBox();
        board.setPadding(new Insets(5));
        board.setMinHeight(70); // Set max width to 800 pixels
        board.setMinWidth(150); //Set min width to 200
        board.setAlignment(Pos.CENTER);
        board.setOnMouseClicked(event -> {
            mainCtrl.showBoardOverview(b.getId());
        });


        Button delete = new Button("x");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showConfirmDeleteBoard(b);
            }
        });

        delete.setStyle("-fx-text-fill: white; -fx-background-color: #6e0518; -fx-font-size: 12px;");
        delete.setPrefHeight(1);
        delete.setPrefWidth(1);


        HBox box = new HBox();
        box.setAlignment(Pos.TOP_RIGHT);
        box.getChildren().add(delete);
        board.getChildren().add(box);

        Label title = new Label(b.getTitle());
        title.setFont(Font.font("System", FontWeight.BOLD, 15));

        VBox.setVgrow(title, Priority.ALWAYS);
        VBox.setMargin(title, new Insets(0, 0, 5, 0));

        title.setAlignment(Pos.CENTER);

        board.getChildren().add(title);

        board.setOnMouseEntered(event -> title.setStyle("-fx-background-color: #F5DEB3"));
        board.setOnMouseExited(event -> title.setStyle("-fx-text-fill: black;"));

        boardContainer.getChildren().add(board);
        scrollPane.setContent(boardContainer);
        scrollPane.setPannable(true);
    }

    /**
     * Show client connect scene
     */
    public void showClientConnect() {
        mainCtrl.showClientConnect();
    }

}
