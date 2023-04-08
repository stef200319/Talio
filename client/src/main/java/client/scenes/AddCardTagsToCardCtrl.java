package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.CardTag;
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

public class AddCardTagsToCardCtrl implements Initializable {

    private ServerUtils server;
    private MainCtrl mainCtrl;


    @FXML
    private ListView<CardTag> ownedTags;

    @FXML
    private ListView<CardTag> availableTags;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private CardTag selectedAvailable;
    private CardTag selectedOwned;


    private Long boardId;

    private Card card;

    /**
     * Constructor
     * @param server
     * @param mainCtrl
     */
    @Inject
    public AddCardTagsToCardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh();
                });
            }
        }, 0, 1000);

        ownedTags.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CardTag>() {
            @Override
            public void changed(ObservableValue<? extends CardTag> observable, CardTag oldValue, CardTag newValue) {
                selectedOwned = ownedTags.getSelectionModel().getSelectedItem();
                updateButtons();
            }
        });

        availableTags.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CardTag>() {
            @Override
            public void changed(ObservableValue<? extends CardTag> observable, CardTag oldValue, CardTag newValue) {
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
        List<CardTag> allCardTags = server.getCardTagsByBoardId(boardId);
        List<CardTag> owned = server.getCardTagsByCardId(card.getId());
        List<CardTag> available = new ArrayList<>(allCardTags);
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
    public void updateSingleListViewItems(ListView<CardTag> list, List<CardTag> items) {
        if (list.getItems().equals(items)) return;

        list.getItems().clear();
        list.getItems().addAll(items);
    }

    /**
     * Updates the look of a single listview
     * @param list
     */
    public void updateSingleListViewLook(ListView<CardTag> list) {
        list.setCellFactory(new Callback<ListView<CardTag>, ListCell<CardTag>>() {
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
                        setStyle("-fx-background-color: white;");
                        if (empty) {
                            setGraphic(null);
                            label.setText("");
                        }
                        else {
                            if (cardTag.equals(selectedOwned) || cardTag.equals(selectedAvailable)) {
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
     * Sets the card of this scene
     * @param card
     */
    public void setCard(Card card) {
        this.card = card;
        this.boardId = server.getBoardByCardId(card.getId()).getId();
    }

    /**
     * shows the taskdetails screen
     */
    public void backButtonPressed() {
        mainCtrl.showTaskDetails(card);
    }

    /**
     * Handles logic for the add button
     */
    public void addButtonPressed() {
        server.addCardTagToCard(selectedAvailable, card.getId());
        refresh();
    }

    /**
     * Handles logic for the remove button
     */
    public void removeButtonPressed() {
        server.deleteCardTagFromCard(selectedOwned, card.getId());
        refresh();
    }


}
