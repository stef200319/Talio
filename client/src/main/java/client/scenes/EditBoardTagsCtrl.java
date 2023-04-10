package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.BoardTag;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class EditBoardTagsCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;
    private Long boardId = null;

    private BoardTag selectedBoardTag;
    private BoardTag changedBoardTag;

    @FXML
    private TextField boardTagTitle;

    @FXML
    private ColorPicker boardTagColor;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<BoardTag> boardTagsContainer;


    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public EditBoardTagsCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.websocket = websocket;
    }

    /**
     * Initializes the scene
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


        boardTagsContainer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BoardTag>() {
            @Override
            public void changed(ObservableValue<? extends BoardTag> observable, BoardTag oldValue, BoardTag newValue) {
                selectedBoardTag = boardTagsContainer.getSelectionModel().getSelectedItem();
                if (selectedBoardTag != null) {
                    updateColorPicker(selectedBoardTag.getColor());
                    updateTitleTextField(selectedBoardTag.getTitle());
                }

            }
        });

        boardTagsContainer.setStyle("-fx-border-width: 3px; -fx-border-color: white; -fx-focus-color: white");

    }

    /**
     * refreshes everything
     */
    public void refresh() {
        if (server.getServer() == null) return; // this is temporarily

        List<BoardTag> boardTagsDB = server.getAllBoardTags();

        if (boardTagsDB.isEmpty()) selectedBoardTag = null;

        if (!boardTagsContainer.getItems().equals(boardTagsDB)) {
            boardTagsContainer.getItems().clear();
            boardTagsContainer.getItems().addAll(boardTagsDB);

            if (changedBoardTag != null && boardTagsContainer.getItems().stream().map(x -> x.getId())
                    .collect(Collectors.toList()).contains(changedBoardTag.getId())) {
                boardTagsContainer.getSelectionModel().select(boardTagsContainer.getItems()
                        .stream().map(x -> x.getId()).collect(Collectors.toList()).indexOf(changedBoardTag.getId()));
            }
            else if (!boardTagsContainer.getItems().isEmpty()) {
                boardTagsContainer.getSelectionModel().select(boardTagsContainer.getItems().size() - 1);
            }
        }
        updateColors();
        updateButtons();

    }

    /**
     * Updates the colors of every cell
     */
    public void updateColors() {
        boardTagsContainer.setCellFactory(new Callback<ListView<BoardTag>, ListCell<BoardTag>>() {
            @Override
            public ListCell<BoardTag> call(ListView<BoardTag> boardTagListView) {
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
                        label.setStyle("-fx-text-fill: black;");
                        setStyle("-fx-background-color: white;");
                        if (empty) {
                            setGraphic(null);
                            label.setText("");
                        }
                        else {
                            if (boardTag.equals(selectedBoardTag)) {
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
     * Updates all the buttons based on if there is a card selected
     */
    public void updateButtons() {
        if (selectedBoardTag == null) {
            saveButton.setDisable(true);
            deleteButton.setDisable(true);
            boardTagTitle.setDisable(true);
            boardTagColor.setDisable(true);
            boardTagTitle.setText("New BoardTag");
            boardTagColor.setValue(Color.WHITE);
        }
        else {
            saveButton.setDisable(false);
            deleteButton.setDisable(false);
            boardTagTitle.setDisable(false);
            boardTagColor.setDisable(false);
        }
    }

    /**
     * Updates the title of the text field
     * @param title
     */
    public void updateTitleTextField(String title) {
        if (title == null || title.equals(boardTagTitle.getText())) return;
        boardTagTitle.setText(title);
    }

    /**
     * Updates the color picker
     * @param color
     */
    public void updateColorPicker(String color) {
        if (color == null || boardTagColor.getValue().toString().equals(color)) return;
        boardTagColor.setValue(Color.valueOf(color));
    }

    /**
     * Saves a boardTag
     */
    public void saveBoardTag() {
        if (selectedBoardTag == null) return;
        String newTitle = boardTagTitle.getText();

        if (newTitle.equals("")) {
            newTitle = "New BoardTag";
            boardTagTitle.setText("New BoardTag");
        }

        String newColor = colourToHexCode(boardTagColor.getValue());
        server.editBoardTagColor(selectedBoardTag, newColor);
        server.editBoardTagTitle(selectedBoardTag, newTitle);
//        websocket.send("/app/updateCardTag", selectedBoardTag);
        changedBoardTag = selectedBoardTag;
        refresh();
    }

    /**
     * Deletes a card Tag
     */
    public void deleteBoardTag() {
        if (selectedBoardTag == null) return;;
        server.deleteBoardTag(selectedBoardTag);
//        websocket.send("/app/updateBoardTag", selectedBoardTag);
        refresh();
    }

    /**
     * Makes a new card Tag
     */
    public void newBoardTag() {
        BoardTag boardTag = new BoardTag("New BoardTag", "#ff0000");
        server.addBoardTag(boardTag);
//        websocket.send("/app/updateBoardTag", boardTag);
        refresh();
        boardTagsContainer.getSelectionModel().select(boardTagsContainer.getItems().size() - 1);
    }

    /**
     * Converts a color to hex string
     * @param colour
     * @return string of the colour
     */
    public static String colourToHexCode(Color colour) {
        return String.format("#%02X%02X%02X",
                (int) (colour.getRed() * 255),
                (int) (colour.getGreen() * 255),
                (int) (colour.getBlue() * 255));
    }

//    /**
//     * Registering for websocket messages
//     */
//    public void registerForMessages() {
//        websocket.registerForMessages("/topic/updateCardTag", BoardTag.class, c -> {
//            System.out.println("Websocket boardTag working");
//            Platform.runLater(() -> refresh());
//        });
//    }

    /**
     * Shows the workspace
     */
    public void showWorkspace() {
        mainCtrl.showWorkspace();
    }
}
