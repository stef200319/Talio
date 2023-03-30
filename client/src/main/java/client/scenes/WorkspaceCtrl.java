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
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;




public class WorkspaceCtrl implements Initializable{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @FXML
    private TextField boardTitle;

    @FXML
    private HBox boardContainer;

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
        board.setPrefWidth(400); // Set preferred width to 400 pixels
        board.setPrefHeight(600); // Set preferred height to 600 pixels
        board.setMaxWidth(800); // Set max width to 800 pixels
        board.setMinWidth(200); //Set min width to 200
        board.setAlignment(Pos.CENTER);

        Button open = new Button("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showBoardOverview(b.getId());
            }
        });

        HBox box = new HBox(5);
        box.setAlignment(Pos.TOP_RIGHT);
        box.getChildren().add(open);
        board.getChildren().add(box);

        Label title = new Label(b.getTitle());
        title.setFont(new Font(20));

        board.getChildren().add(title);

        boardContainer.getChildren().add(board);
    }



}
