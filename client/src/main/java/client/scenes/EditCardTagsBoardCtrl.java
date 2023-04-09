package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.CardTag;
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
import java.util.stream.Collectors;

public class EditCardTagsBoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;
    private Long boardId = null;

    private CardTag selectedCardTag;
    private CardTag changedCardTag;

    @FXML
    private TextField cardTagTitle;

    @FXML
    private ColorPicker cardTagColor;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<CardTag> cardTagsContainer;


    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public EditCardTagsBoardCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
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
//        refresh();
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    refresh();
//                });
//            }
//        }, 0, 1000);

        websocket.registerForMessages("/topic/updateCardTag", CardTag.class, c -> {
            System.out.println("Websocket cardTag working");
            Platform.runLater(() -> refresh());
        });

        cardTagsContainer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CardTag>() {
            @Override
            public void changed(ObservableValue<? extends CardTag> observable, CardTag oldValue, CardTag newValue) {
                selectedCardTag = cardTagsContainer.getSelectionModel().getSelectedItem();
                if (selectedCardTag != null) {
                    updateColorPicker(selectedCardTag.getColor());
                    updateTitleTextField(selectedCardTag.getTitle());
                }

            }
        });

        cardTagsContainer.setStyle("-fx-border-width: 3px; -fx-border-color: white; -fx-focus-color: white");
    }

    /**
     * refreshes everything
     */
    public void refresh() {
        if (boardId == null) return;


        List<CardTag> cardTagsDB = server.getCardTagsByBoardId(boardId);

        if (cardTagsDB.isEmpty()) selectedCardTag = null;

        if (!cardTagsContainer.getItems().equals(cardTagsDB)) {
            cardTagsContainer.getItems().clear();
            cardTagsContainer.getItems().addAll(cardTagsDB);

            if (changedCardTag != null && cardTagsContainer.getItems().stream().map(x -> x.getId())
                    .collect(Collectors.toList()).contains(changedCardTag.getId())) {
                cardTagsContainer.getSelectionModel().select(cardTagsContainer.getItems()
                        .stream().map(x -> x.getId()).collect(Collectors.toList()).indexOf(changedCardTag.getId()));
            }
            else if (!cardTagsContainer.getItems().isEmpty()) {
                cardTagsContainer.getSelectionModel().select(cardTagsContainer.getItems().size() - 1);
            }
        }
        updateColors();
        updateButtons();

    }

    /**
     * Updates the colors of every cell
     */
    public void updateColors() {
        cardTagsContainer.setCellFactory(new Callback<ListView<CardTag>, ListCell<CardTag>>() {
            @Override
            public ListCell<CardTag> call(ListView<CardTag> cardTagListView) {
                return new ListCell<CardTag>() {
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
                    protected void updateItem(CardTag cardTag, boolean empty) {
                        super.updateItem(cardTag, empty);

                        // Set the background color for the cell based on its contents
                        setText(null);
                        label.setStyle("-fx-text-fill: black;");
                        setStyle("-fx-background-color: white;");
                        if (empty) {
                            setGraphic(null);
                            label.setText("");
                        }
                        else {
                            if (cardTag.equals(selectedCardTag)) {
                                label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                            }
                            else {
                                label.setStyle("-fx-text-fill: black;");
                            }
                            coloredSquare.setStyle("-fx-background-color:" + cardTag.getColor() + ";");
                            label.setText(cardTag.getTitle());
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
        if (selectedCardTag == null) {
            saveButton.setDisable(true);
            deleteButton.setDisable(true);
            cardTagTitle.setDisable(true);
            cardTagColor.setDisable(true);
            cardTagTitle.setText("New CardTag");
            cardTagColor.setValue(Color.WHITE);
        }
        else {
            saveButton.setDisable(false);
            deleteButton.setDisable(false);
            cardTagTitle.setDisable(false);
            cardTagColor.setDisable(false);
        }
    }

    /**
     * Updates the title of the text field
     * @param title
     */
    public void updateTitleTextField(String title) {
        if (title == null || title.equals(cardTagTitle.getText())) return;
        cardTagTitle.setText(title);
    }

    /**
     * Updates the color picker
     * @param color
     */
    public void updateColorPicker(String color) {
        if (color == null || cardTagColor.getValue().toString().equals(color)) return;
        cardTagColor.setValue(Color.valueOf(color));
    }

    /**
     * Shows the board overview
     */
    public void showBoardOverview() {
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Sets the boardId of this scene
     * @param boardId
     */
    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    /**
     * Saves a cardTag
     */
    public void saveCardTag() {
        if (selectedCardTag == null) return;
        String newTitle = cardTagTitle.getText();

        if (newTitle.equals("")) {
            newTitle = "New CardTag";
            cardTagTitle.setText("New CardTag");
        }

        String newColor = colourToHexCode(cardTagColor.getValue());
        server.editCardTagColor(selectedCardTag, newColor);
        server.editCardTagTitle(selectedCardTag, newTitle);
        websocket.send("/app/updateCardTag", selectedCardTag);
        changedCardTag = selectedCardTag;
//        refresh(); We are using websocket.
    }

    /**
     * Deletes a card Tag
     */
    public void deleteCardTag() {
        if (selectedCardTag == null) return;;
        server.deleteCardTagFromBoard(selectedCardTag);
        websocket.send("/app/updateCardTag", selectedCardTag);
        refresh();
    }

    /**
     * Makes a new card Tag
     */
    public void newCardTag() {
        CardTag cardTag = new CardTag("New CardTag", "#ff0000", server.getBoardByID(boardId));
        server.addCardTagToBoard(cardTag, boardId);
        websocket.send("/app/updateCardTag", cardTag);
//        refresh(); We are using websocket.
        cardTagsContainer.getSelectionModel().select(cardTagsContainer.getItems().size() - 1);
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
}
