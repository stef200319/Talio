package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Card;
import commons.Column;
import commons.Subtask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class ViewSubtaskCtrl implements Initializable {

    private Card cardToShow;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Websocket websocket;

    @FXML
    private VBox subtaskList;

    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public ViewSubtaskCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocket = websocket;
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
        websocket.registerForMessages("/topic/updateSubtask", Subtask.class, subtask -> {
            System.out.println("Websocket subtask working");

            Platform.runLater(() -> {
                refresh();
            });
        });

        websocket.registerForMessages("/topic/updateCard", Card.class, card -> {
            System.out.println("Websocket card working");

            Platform.runLater(() -> {
                refresh();
            });
        });

//        Short polling
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
//                    refresh();
//                });
//            }
//        }, 0, 1000);
    }

    /**
     * Set the Card whose details have to be displayed
     * @param cardToShow the Card whose details have to be displayed
     */

    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
    }


    /**
     * show the board overview
     */
    public void showCardDetails() {
        mainCtrl.showTaskDetails(cardToShow);
    }

    /**
     * Shows the add subtask page
     */
    public void add() {
        mainCtrl.showAddSubtask(cardToShow);
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        subtaskList.getChildren().clear();

        if(cardToShow!=null) {

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

                VBox buttons = new VBox();
                buttons.setAlignment(Pos.CENTER);

                Button delete = new Button("X");
                delete.setStyle("-fx-text-fill: white; -fx-background-color: #6e0518; -fx-font-size: 12px;");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //server.deleteSubtask(s.getId());
                        server.deleteSubtaskFromCard(cardToShow.getId(), s.getId());
                        websocket.send("/app/updateCard", cardToShow);
                        setCardToShow(server.getCardById(cardToShow.getId()));
//                        refresh(); We are using websocket.
                    }
                });

                Button edit = new Button("Edit");
                edit.setStyle("-fx-background-color: #e0cda8;");
                edit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mainCtrl.showEditSubtaskTitle(cardToShow, s);
                    }
                });

                buttons.getChildren().add(delete);
                buttons.getChildren().add(edit);

                Region r = new Region();
                HBox.setHgrow(r, Priority.ALWAYS);
                HBox subtaskBox = new HBox();
                subtaskBox.setAlignment(Pos.CENTER);

                subtaskBox.getChildren().add(checkbox);
                subtaskBox.getChildren().add(r);
                subtaskBox.getChildren().add(buttons);

                subtaskBox = enableDragAndDrop(subtaskBox, cardToShow, i);

                subtaskList.getChildren().add(subtaskBox);
            }
        }
    }

    /**
     * Enables the drag and drop for a subtask
     * @param hBox Box in which the subtask is displayed
     * @param c the card in which the subtask is
     * @param i position of subtask in card
     * @return a new Hbox which can be dragged and dropped
     */
    public HBox enableDragAndDrop(HBox hBox, Card c, int i) {
        hBox.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = hBox.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(Integer.toString(i));
                db.setContent(content);

                event.consume();
            }
        });

        hBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != hBox && event.getDragboard().hasString())
                    event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        hBox.setOnDragDropped(new EventHandler<DragEvent>() {
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
        return hBox;
    }
}
