package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class AddBoardTagsToBoardCtrl implements Initializable {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Websocket websocket;
    private boolean register;


    @FXML
    private ListView<BoardTag> ownedTags;

    @FXML
    private ListView<BoardTag> availableTags;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private BoardTag selectedAvailable;
    private BoardTag selectedOwned;


    private Long boardId;

    private Board boardToChange;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public AddBoardTagsToBoardCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.websocket = websocket;
        register = false;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Short polling
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    refresh();
//                });
//            }
//        }, 0, 1000);

        ownedTags.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BoardTag>() {
            @Override
            public void changed(ObservableValue<? extends BoardTag> observable, BoardTag oldValue, BoardTag newValue) {
                selectedOwned = ownedTags.getSelectionModel().getSelectedItem();
                updateButtons();
            }
        });

        availableTags.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BoardTag>() {
            @Override
            public void changed(ObservableValue<? extends BoardTag> observable, BoardTag oldValue, BoardTag newValue) {
                selectedAvailable = availableTags.getSelectionModel().getSelectedItem();
                updateButtons();
            }
        });

        ownedTags.setStyle("-fx-border-width: 3px; -fx-border-color: white; -fx-focus-color: white");
        availableTags.setStyle("-fx-border-width: 3px; -fx-border-color: white; -fx-focus-color: white");


    }


    /**
     * Refreshes the scene
     */
    public void refresh() {
        if (boardId == null) return;
        updateListViews();
        updateButtons();
    }

    /**
     * Updates the buttons
     */
    public void updateButtons() {
        if (selectedOwned == null) {
            removeButton.setDisable(true);
        }
        else {
            removeButton.setDisable(false);
        }

        if (selectedAvailable == null) {
            addButton.setDisable(true);
        }
        else {
            addButton.setDisable(false);
        }
    }

    /**
     * Updates all the listviews
     */
    public void updateListViews() {
        List<BoardTag> allBoardTags = server.getAllBoardTags();
        List<BoardTag> owned = server.getBoardTagsByBoardId(boardToChange.getId());
        List<BoardTag> available = new ArrayList<>(allBoardTags);
        available.removeAll(owned);

        updateSingleListViewItems(availableTags, available);
        updateSingleListViewItems(ownedTags, owned);
        updateSingleListViewLook(availableTags);
        updateSingleListViewLook(ownedTags);
    }

    /**
     * Updates the items of a single listview
     * @param list
     * @param items
     */
    public void updateSingleListViewItems(ListView<BoardTag> list, List<BoardTag> items) {
        if (list.getItems().equals(items)) return;

        list.getItems().clear();
        list.getItems().addAll(items);
    }

    /**
     * Updates the look of a single listview
     * @param list
     */
    public void updateSingleListViewLook(ListView<BoardTag> list) {
        list.setCellFactory(new Callback<ListView<BoardTag>, ListCell<BoardTag>>() {
            @Override
            public ListCell<BoardTag> call(ListView<BoardTag> cardTagListView) {
                return new ListCell<BoardTag>() {
                    private final StackPane container = new StackPane();
                    private final StackPane coloredSquare = new StackPane();
                    private final Label label = new Label();

                    {
                        // Set the size of the colored square
                        coloredSquare.setMaxHeight(12);
                        coloredSquare.setMaxWidth(12);
                        container.setAlignment(Pos.BASELINE_LEFT);
                        // Set the label to be right-aligned
                        label.setPadding(new Insets(0, 0, 0, 22));
                        // Add the colored square and label to the container
                        container.getChildren().addAll(coloredSquare, label);
                    }

                    @Override
                    protected void updateItem(BoardTag boardTag, boolean empty) {
                        super.updateItem(boardTag, empty);

                        // Set the background color for the cell based on its contents
                        setText(null);
                        setStyle("-fx-background-color: white;");
                        if (empty) {
                            setGraphic(null);
                            label.setText("");
                        }
                        else {
                            if (boardTag.equals(selectedOwned) || boardTag.equals(selectedAvailable)) {
                                label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                            }
                            else {
                                label.setStyle("-fx-text-fill: black;");
                            }
                            coloredSquare.setStyle("-fx-background-color:" + boardTag.getColor() + ";");
                            label.setText(boardTag.getTitle());
                            setGraphic(container);
                        }
                    }
                };
            }
        });
    }

    /**
     * Sets the card of this scene
     * @param boardId
     */
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
        this.boardToChange = server.getBoardByID(boardId);
    }

    /**
     * shows the boardOverview screen
     */
    public void backButtonPressed() {
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Handles logic for the add button
     */
    public void addButtonPressed() {
        server.addBoardTagToBoard(boardId, selectedAvailable);
        websocket.send("/app/updateBoardTag", selectedAvailable);
    }

    /**
     * Handles logic for the remove button
     */
    public void removeButtonPressed() {
        server.deleteBoardTagFromBoard(boardId, selectedOwned);
        websocket.send("/app/updateBoardTag", selectedOwned);
    }

    /**
     * Shows the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Registering for websocket messages
     */
    public void registerForMessages() {
        if(register==false) {
            websocket.registerForMessages("/topic/updateBoardTag", BoardTag.class, boardTag -> {
                System.out.println("Websocket board tag working");

                Platform.runLater(() -> {
                    if (boardToChange != null)
                        refresh();
                });
            });
            register = true;
        }
    }
}
