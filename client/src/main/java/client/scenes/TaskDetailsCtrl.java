package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;


import commons.CardTag;
import commons.Column;

import commons.Subtask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;


import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;
import java.util.List;


public class TaskDetailsCtrl implements Initializable {
    private Card cardToShow;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;



    @FXML
    private Label cardTitle;

    @FXML
    private Label cardDescription;

    @FXML
    private VBox subtasksScroll;

    @FXML
    private ListView<CardTag> cardTagListView;


    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public TaskDetailsCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocket = websocket;
    }

    /**
<<<<<<< client/src/main/java/client/scenes/TaskDetailsCtrl.java
=======
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
//        Short polling
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    refresh();
//                });
//            }
//        }, 0, 1000);

        cardTagListView.setStyle("-fx-border-width: 3px; -fx-border-color: white; -fx-focus-color: white");

        // Websocket
        websocket.registerForMessages("/topic/updateSubtask", Subtask.class, subtask -> {
            System.out.println("Websocket subtask working");
            Platform.runLater(() -> refresh());
        });

        websocket.registerForMessages("/topic/updateCardTag", CardTag.class, cardTag -> {
            System.out.println("Websocket cardTag working");
            Platform.runLater(() -> refresh());
        });

        websocket.registerForMessages("/topic/deleteCard", Card.class, card -> {
            System.out.println("Websocket delete card working");
            Platform.runLater(() -> {
                if(cardToShow!=null && !server.existsByIdCard(cardToShow.getId()))
                    showBoardOverview();
            });
        });

        websocket.registerForMessages("/topic/updateCard", Card.class, card -> {
            System.out.println("Websocket card working");
            if(server.existsByIdCard(cardToShow.getId()))
                Platform.runLater(() -> refresh());
        });
    }

    

    
    /**
     * Set the Card whose details have to be displayed
     * @param cardToShow the Card whose details have to be displayed
     */

    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
    }

    /**
     * show the page to edit the Card Title
     */
    public void showEditCardTitle(){
        mainCtrl.showEditCardTitle(cardToShow);
    }

    /**
     * show the page to edit the Card Description
     */
    public void showEditCardDescription(){
        mainCtrl.showEditCardDescription(cardToShow);

    }

    /**
     * show the page to Customize the Card
     */
    public void showCardCustomization(){
        mainCtrl.showCustomizeCard(cardToShow);
    }

    /**
     * show the board overview
     */

    public void showBoardOverview() {
        long columnId = cardToShow.getColumnId();
        Column c = server.getColumnByColumnId(columnId);
        long boardId = c.getBoardId();
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * shows the addCardTagsToCard scene
     */
    public void showEditCardTagsToCard() {
        mainCtrl.showAddCardTagsToCard(cardToShow);
    }

    /**
     * shows the view subtasks page
     */
    public void showViewSubtask() {
        mainCtrl.showViewSubtask(cardToShow);
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        if (cardToShow != null) {
            subtasksScroll.getChildren().clear();

            cardToShow = server.getCardById(cardToShow.getId());

            if (cardToShow != null) {
                cardTitle.setText(cardToShow.getTitle());
                cardDescription.setText(cardToShow.getDescription());

                List<Subtask> subtasks = cardToShow.getSubtasks();
                for (int i = 0; i < subtasks.size(); i++) {
                    Subtask s = subtasks.get(i);
                    CheckBox checkbox = new CheckBox();
                    checkbox.setText(s.getTitle());
                    checkbox.setSelected(s.getDone());
                    checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable,
                                        Boolean oldValue, Boolean newValue) {
                            server.editSubtaskStatus(s.getId(), newValue);
                            websocket.send("/app/updateSubtask", s);
                            cardToShow = server.getCardById(cardToShow.getId());
                        }
                    });

                    checkbox = enableDragAndDrop(checkbox, cardToShow, i);

                    subtasksScroll.getChildren().add(checkbox);
                }
            }

            if (cardToShow != null) {
                updateCardTagListViewItems();
                updateCardTagListViewLook();
            }
        }
    }

    /**
     * Enables drag and drop for a subtask
     * @param checkBox Checkbox in which subtask is displayed
     * @param c the card that the subtask is in
     * @param i position of the subtask in the list of subtasks of the card
     * @return a new checkbox which can be dragged and dropped
     */
    public CheckBox enableDragAndDrop(CheckBox checkBox, Card c, int i) {
        checkBox.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = checkBox.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(Integer.toString(i));
                db.setContent(content);

                event.consume();
            }
        });

        checkBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != checkBox && event.getDragboard().hasString())
                    event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        checkBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                int oldPos = Integer.parseInt(db.getString());
                int newPos = i;
                Card newCard = server.changeSubtaskPosition(c.getId(), oldPos, newPos);
                websocket.send("/app/updateCard", newCard);
                setCardToShow(newCard);
//                refresh(); We are using websocket.
            }
        });
        return checkBox;


    }

    /**
     * Updates the items of the cardTagsListView
     */
    public void updateCardTagListViewItems() {
        List<CardTag> cardTagsDB = server.getCardTagsByCardId(cardToShow.getId());

        if (!cardTagsDB.equals(cardTagListView.getItems())) {
            cardTagListView.getItems().clear();
            cardTagListView.getItems().addAll(cardTagsDB);
        }
    }

    /**
     * Displays the cardTags
     */
    public void updateCardTagListViewLook() {
        cardTagListView.setCellFactory(new Callback<ListView<CardTag>, ListCell<CardTag>>() {
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
                            label.setStyle("-fx-text-fill: black;");
                            coloredSquare.setStyle("-fx-background-color:" + cardTag.getColor() + ";");
                            label.setText(cardTag.getTitle());
                            setGraphic(container);
                        }
                    }
                };
            }
        });
    }


}
