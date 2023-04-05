package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;


import commons.Column;

import commons.Subtask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.input.*;


import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;
import java.util.List;


public class TaskDetailsCtrl implements Initializable {
    private Card cardToShow;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;



    @FXML
    private Label cardTitle;

    @FXML
    private Label cardDescription;

    @FXML
    private VBox subtasksScroll;


    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public TaskDetailsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

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
     * shows the view subtasks page
     */
    public void showViewSubtask() {
        mainCtrl.showViewSubtask(cardToShow);
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        subtasksScroll.getChildren().clear();

        if(cardToShow!=null) {
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
                        cardToShow = server.getCardById(cardToShow.getId());
                    }
                });

                checkbox = enableDragAndDrop(checkbox, cardToShow, i);

                subtasksScroll.getChildren().add(checkbox);
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
                setCardToShow(newCard);
                refresh();
            }
        });
        return checkBox;
    }


}
