package client.scenes;

import client.utils.ServerUtils;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Column;
import commons.Subtask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.checkerframework.checker.units.qual.g;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final Websocket websocket;

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
    private Button editBoardTitleButton;

    @FXML
    private Button copyCodeButton;
    @FXML
    private Label boardName;

    @FXML
    private Pane sidePane;


    @FXML
    private AnchorPane centerPane;

    private Card highlightedCard;

    private boolean highlightedByKey;

    private int highlightedListIndex;

    private int highlightedCardIndex;

    private Column highlightedColumn;

    private VBox highlightedTask;


    /**
     * @param server Server we are connected to
     * @param mainCtrl the main controller
     * @param websocket websocket for updating
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl, Websocket websocket) {
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
        websocket.registerForMessages("/topic/updateColumn", Column.class, c -> {
            System.out.println("Websocket column working");

            Platform.runLater(() -> {
                refresh();
            });

//            createList(c); Not working

        });

        websocket.registerForMessages("/topic/updateCard", Card.class, c -> {
            System.out.println("Websocket card working");

            Platform.runLater(() -> {
                refresh();
            });
        });

        websocket.registerForMessages("/topic/deleteCard", Card.class, c -> {
            System.out.println("Websocket card delete working");

            Platform.runLater(() -> {
                refresh();
            });
        });

        websocket.registerForMessages("/topic/updateBoard", Board.class, c -> {
            System.out.println("Websocket board working");

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
     * Method that shows the quote overview on screen
     */
    public void showQuoteOverview() {
        mainCtrl.showOverview();
    }

    /**
     * method that shows the join board by key screen
     */
    public void joinBoard(){

        mainCtrl.showJoinBoard(boardID);
    }
    /**
     * method that calls the method in MainCtrl that copies the code
     * of the current board
     */
    public void copyCode()
    {
        mainCtrl.copyCode(boardID);
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
     * Method that shows the edit board title page
     */
    public void editBoardTitle(){mainCtrl.showEditBoardTitle(boardID);}

    /**
     * Method that shows the create board page
     */
    public void createBoard() {mainCtrl.showCreateBoard(boardID);}

    /**
     * Method that refreshes the board
     */
    public void refresh() {
        columnContainer.getChildren().clear();
        if(boardID == Long.MIN_VALUE ||
            !server.getAllBoardsWithoutServers().stream().map(Board::getId)
                .collect(Collectors.toList()).contains(boardID)){
            return;
        }
        Board currentBoard = server.getBoardByID(boardID);

        Font fontBoard = Font.font(currentBoard.getFontType(),
                currentBoard.isFontStyleBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                currentBoard.isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                20);
        boardName.setFont(fontBoard);
        boardName.setTextFill(Color.web(currentBoard.getFontColour()));

        boardName.setText(currentBoard.getTitle());

        sidePane.setStyle("-fx-background-color: "+currentBoard.getSideColour());

        centerPane.setStyle("-fx-background-color: "+currentBoard.getCenterColour());

        List<Column> columns = server.getColumnsByBoardId(boardID);
        for (int i = 0; i < columns.size(); i++)
            createList(columns.get(i));
    }



    /**
     * Method that showcases the column on the board
     * @param c column to be showcased
     */


    @SuppressWarnings({"checkstyle:MethodLength","checkstyle:CyclomaticComplexity"})
    public void createList(Column c) {
        VBox list=new VBox();
        list.setStyle("-fx-background-color: "+c.getBgColour()+"; -fx-border-style: " +
                "solid; -fx-background-radius: 5px; -fx-border-radius: 5px;" +
                "-fx-border-color: "+c.getBorderColour());



        list.setPadding(new Insets(5));
        list.setPrefWidth(400); // Set preferred width to 400 pixels
        list.setPrefHeight(600); // Set preferred height to 600 pixels
        list.setMaxWidth(800); // Set max width to 800 pixels
        list.setMinWidth(200); //Set min width to 200
        list.setAlignment(Pos.CENTER);


        //Dropping a card directly on a column
        list.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getDragboard().hasString())
                    event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        list.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                long oldId = Long.parseLong(db.getString());
                Card oldCard = server.getCardById(oldId);
                if(oldCard.getColumnId()!=c.getId()) {
                    server.editCardColumn(oldId,c.getId());
                    websocket.send("/app/updateCard", oldCard);
                }
//                refresh(); We are using websocket.
            }
        });
        // End of dropping card on column


        // Delete and edit buttons
        Button delete = new Button("x");
        delete.setStyle("-fx-text-fill: white; -fx-background-color: #6e0518; -fx-font-size: 12px;");
        delete.setPrefHeight(1);
        delete.setPrefWidth(1);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showConfirmDeleteColumn(c);
            }
        });
        Button editTitle = new Button("Edit");
        editTitle.setStyle("-fx-background-color: #E0CDA8");
        editTitle.setFont(Font.font("System", FontPosture.ITALIC, 12));
        editTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showEditList(c, boardID);
            }
        });

        HBox deleteBox = new HBox(130);
        deleteBox.getChildren().add(editTitle);
        deleteBox.getChildren().add(delete);
        list.getChildren().add(deleteBox);
        //End of delete and edit buttons
        delete.setAlignment(Pos.TOP_RIGHT);



        Label title = new Label(c.getTitle());

        Font font = Font.font(c.getFontType(),
                c.isFontStyleBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                c.isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                20);
        title.setFont(font);
        title.setTextFill(Color.web(c.getFontColour()));




        list.getChildren().add(title);

        List<Card> cards = server.getCardsByColumnId(c.getId());
        VBox cardContainer = new VBox();
        cardContainer.setSpacing(10);
        list.getChildren().add(cardContainer);
        for(int i=0;i<cards.size();i++) {
            int finalI = i;

            VBox card = new VBox();



            Region r = new Region();
            HBox.setHgrow(r, Priority.ALWAYS);
            HBox cardSmall = new HBox();                   //card box
            cardSmall.setAlignment(Pos.CENTER_RIGHT);
            card.setStyle("-fx-background-color: "+cards.get(finalI).getBgColour()+"; -fx-border-style: " +
                    "solid; -fx-background-radius: 5px; -fx-border-radius: 5px;" +
                    "-fx-border-color: "+cards.get(finalI).getBorderColour());


            Label s = new Label(cards.get(i).getTitle());
            //s.setMaxWidth(80);
            Font fontList = Font.font(cards.get(i).getFontType(),
                    cards.get(i).isFontStyleBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                    cards.get(i).isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                    12);
            s.setFont(fontList);
            s.setTextFill(Color.web(cards.get(i).getFontColour()));

            cardSmall.getChildren().add(s);
            cardSmall.getChildren().add(r);                          //region between text and delete button


            VBox cardButtons = new VBox(5);             //box for details and delete buttons

            cardButtons.setAlignment(Pos.CENTER);

            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Do something when the card is clicked
                    if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
                        mainCtrl.showTaskDetails(cards.get(finalI));
                }
            });

            Button deleteCard = new Button("x");
            deleteCard.setStyle("-fx-text-fill: white; -fx-background-color: #6e0518; -fx-font-size: 12px;");
            deleteCard.setPrefHeight(1);
            deleteCard.setPrefWidth(1);


            deleteCard.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    server.deleteCard(cards.get(finalI));
                    websocket.send("/app/deleteCard", cards.get(finalI));
//                    refresh(); We are using websocket.
                }
            });

            VBox cardFinal = card;


            EventHandler<MouseEvent>  handler = event -> {

                if(highlightedByKey == false) {
                    highlightedByKey = false;
                    if (highlightedTask != null) {
                        unHighlightTask(highlightedTask, cardContainer);
                    }

                    highlightedColumn = server.getColumnByColumnId(cards.get(finalI).getColumnId());
                    highlightedListIndex = highlightedColumn.getPosition()-1;

                    setHighlightedTask(getCardToHiglight(columnContainer, highlightedListIndex, finalI),
                            cardContainer, finalI, highlightedListIndex);

                }

            };
            cardFinal.setOnMouseEntered(handler);
            cardFinal.setOnMouseExited(event -> {

            });
            cardContainer.setOnKeyReleased(event -> highlightedByKey = false);
            cardContainer.setOnKeyPressed(event1 ->{

                if(event1.getCode()==KeyCode.ENTER)
                {

                    mainCtrl.showTaskDetails(this.highlightedCard);

                }
                if(event1.getCode()==KeyCode.DELETE || event1.getCode()==KeyCode.BACK_SPACE)
                {
                    server.deleteCard(this.highlightedCard);
                    websocket.send("/app/updateCard", this.highlightedCard);

//                    refresh(); We are using websocket.
                }
                if(event1.getCode()==KeyCode.T)
                {
                    //show pop up
                }
                if(event1.getCode()==KeyCode.C)
                {
                    //show pop up
                }
                if(event1.getCode()==KeyCode.DOWN && getHighlightedTask()!=null &&
                                 highlightedCardIndex<cardContainer.getChildren().size()-1 && !event1.isShiftDown()){
                    if(server.getCardsByColumnId(server.getColumnsByBoardId(boardID).
                            get(highlightedListIndex).getId()).size()-1>=this.highlightedCardIndex+1) {
                        highlightedByKey = true;

                        unHighlightTask(highlightedTask, cardContainer);

                        highlightedCardIndex = highlightedCardIndex + 1;

                        setHighlightedTask(getCardToHiglight(columnContainer, highlightedListIndex,
                                        highlightedCardIndex),
                                cardContainer, highlightedCardIndex, highlightedListIndex);
                    }

                }
                if(event1.getCode()==KeyCode.UP && getHighlightedTask()!=null && highlightedCardIndex>0
                            && !event1.isShiftDown())
                {
                    highlightedByKey = true;


                    unHighlightTask(highlightedTask, cardContainer);

                    highlightedCardIndex=highlightedCardIndex-1;

                    setHighlightedTask(getCardToHiglight(columnContainer, highlightedListIndex, highlightedCardIndex),
                            cardContainer, highlightedCardIndex, highlightedListIndex);

                }
                if(event1.isShiftDown() && event1.getCode()==KeyCode.UP && getHighlightedTask()!=null)
                {
                    /*highlightedByKey = true;
                    Card swapCard = server.getCardsByColumnId(server.getColumnsByBoardId(boardID).
                            get(highlightedListIndex).getId()).get(highlightedCardIndex-1);
                    highlightedCardIndex--;  //position = index+1; so index is now at the position-1 value

                    server.editCardPosition(highlightedCard.getId(), highlightedCardIndex+1);
                    server.editCardPosition(swapCard.getId(), highlightedCardIndex+2);

                    //refresh();
                    cardContainer.requestFocus();
                    unHighlightTask(this.highlightedTask, cardContainer);
                    setHighlightedTask(getCardToHiglight(columnContainer, highlightedListIndex, highlightedCardIndex),
                            cardContainer, highlightedCardIndex, highlightedListIndex);
                    cardContainer.requestFocus();*/
                }
                if(event1.isShiftDown() && event1.getCode()==KeyCode.DOWN && getHighlightedTask()!=null)
                {
                    /*highlightedByKey = true;
                    //the card under the highlighted card
                    Card swapCard = server.getCardsByColumnId(server.getColumnsByBoardId(boardID).
                            get(highlightedListIndex).getId()).get(highlightedCardIndex+1);
                    //index moves to the position we want to move the highlighted card to
                    highlightedCardIndex++;

                    server.editCardPosition(highlightedCard.getId(), highlightedCardIndex+1);
                    server.editCardPosition(swapCard.getId(), highlightedCardIndex);

                    //refresh();
                    cardContainer.requestFocus();
                    unHighlightTask(this.highlightedTask, cardContainer);
                    setHighlightedTask(getCardToHiglight(columnContainer, highlightedListIndex, highlightedCardIndex),
                            cardContainer, highlightedCardIndex, highlightedListIndex);
                    cardContainer.requestFocus();;*/

                }
                if(event1.getCode()==KeyCode.LEFT && getHighlightedTask()!=null && highlightedListIndex>0 &&
                        server.getCardsByColumnId(
                                server.getColumnsByBoardId(boardID).get(highlightedListIndex-1).getId()).size()>0)
                {
                    highlightedByKey = true;
                    unHighlightTask(highlightedTask, cardContainer);
                    this.highlightedListIndex = highlightedListIndex-1;
                    if(this.highlightedCardIndex>server.getCardsByColumnId(
                            server.getColumnsByBoardId(boardID).get(highlightedListIndex).getId()).size()-1)
                    {
                        this.highlightedCardIndex = server.getCardsByColumnId(server.getColumnsByBoardId(boardID)
                                .get(highlightedListIndex).getId()).size()-1;
                    }

                    setHighlightedTask(getCardToHiglight(columnContainer,
                                    highlightedListIndex, highlightedCardIndex) ,cardContainer, highlightedCardIndex,
                            highlightedListIndex);
                }
                if(event1.getCode()==KeyCode.RIGHT && getHighlightedTask()!=null &&
                        highlightedListIndex<server.getColumnsByBoardId(boardID).size()-1
                        && server.getCardsByColumnId(
                        server.getColumnsByBoardId(boardID).get(highlightedListIndex+1).getId()).size()>0)
                {
                    highlightedByKey = true;
                    unHighlightTask(highlightedTask, cardContainer);
                    this.highlightedListIndex = highlightedListIndex+1;
                    if(this.highlightedCardIndex>server.
                            getCardsByColumnId(server.getColumnsByBoardId(boardID).
                                    get(highlightedListIndex).getId()).size()-1)
                    {
                        this.highlightedCardIndex = server.
                                getCardsByColumnId(server.getColumnsByBoardId(boardID).
                                        get(highlightedListIndex).getId()).size()-1;
                    }

                    setHighlightedTask(getCardToHiglight(columnContainer,
                                    highlightedListIndex, highlightedCardIndex),
                            cardContainer, highlightedCardIndex, highlightedListIndex);

                }
            });
            cardButtons.getChildren().add(deleteCard);

            cardSmall.getChildren().add(cardButtons);

            card.getChildren().add(cardSmall);

            //Making Description and subtask icon
            Region reg = new Region();
            HBox.setHgrow(reg, Priority.ALWAYS);
            HBox details = new HBox();
            if(cards.get(i).getSubtasks()!=null && cards.get(i).getSubtasks().size()!=0) {
                List<Subtask> subtasks = cards.get(i).getSubtasks();
                int nrSub = subtasks.size();
                int nrSubCom = 0;
                for(Subtask subt : subtasks) {
                    if(subt.getDone()==true)
                        nrSubCom++;
                }
                Label subtasksLabel = new Label(nrSubCom+"/"+nrSub);
                fontList = Font.font(cards.get(i).getFontType(),
                    FontWeight.BOLD,
                    cards.get(i).isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                    12);
                subtasksLabel.setFont(fontList);
                subtasksLabel.setTextFill(Color.web(cards.get(i).getFontColour()));
                details.getChildren().add(subtasksLabel);
            }

            details.getChildren().add(reg);

            if(cards.get(i).getDescription() != null && cards.get(i).getDescription()!="") {
                Label description = new Label("Detailed");
                fontList = Font.font(cards.get(i).getFontType(),
                    FontWeight.BOLD,
                    cards.get(i).isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                    12);
                description.setFont(fontList);
                description.setTextFill(Color.web(cards.get(i).getFontColour()));
                details.getChildren().add(description);
            }

            card.getChildren().add(details);
            //Finish description and subtasks icon

            card = enableDragAndDrop(card, c, cards, i);

            cardContainer.getChildren().add(card);
        }
        cardContainer.setPrefWidth(380); // Set preferred width to 380 pixels
        cardContainer.setPrefHeight(500); // Set preferred height to 500 pixels


        //Button for adding a task
        Button b = new Button("Add Task");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainCtrl.showAddTask(c.getId(),boardID );
            }
        });
        list.getChildren().add(b);
        b.setAlignment(Pos.BOTTOM_LEFT);
        b.setStyle("-fx-text-fill: white; -fx-background-color:  #104a03; -fx-font-size: 12px;");
        //End of button for adding a task

        columnContainer.getChildren().add(list);
    }

    /**
     * @return the currently highlighted task
     */
    public VBox getHighlightedTask(){return this.highlightedTask;}

    /**
     * sets the newly highlighted card
     * @param card
     */
    public void setHighlightedCard(Card card){this.highlightedCard = card;}

    /**
     * sets the newly highlighted task
     * @param hbox
     */
    public void setHighlightedTask(VBox hbox){this.highlightedTask = hbox;}

    /**
     * method that changes the highlighted by key value
     * @param bool
     */
    public void setHighlightedByKey(boolean bool){this.highlightedByKey = bool;}

    /**
     * @param container
     * @param indexList of the list that contains the card
     * @param indexCard of the card
     * @return the card to be highlighted
     */

    public VBox getCardToHiglight(HBox container,  int indexList, int indexCard)
    {
        VBox list = (VBox)container.getChildren().get(indexList);
        VBox cardList = (VBox)list.getChildren().get(2);
        VBox cardToHighlight = (VBox) cardList.getChildren().get(indexCard);
        return cardToHighlight;
    }

    /**
     * method that sets the highlighted task
     * @param l
     * @param vbox
     * @param index
     * @param indexList
     */
    public void setHighlightedTask(VBox l, VBox vbox, int index, int indexList){
        this.highlightedTask=l;
        vbox.requestFocus();
        this.highlightedCardIndex = index;
        this.highlightedListIndex = indexList;
        this.highlightedColumn = server.getColumnsByBoardId(boardID).get(indexList);
        this.highlightedCard = server.getCardsByColumnId(highlightedColumn.getId()).get(index);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);
        this.highlightedTask.setEffect(dropShadow);

    }

    /**
     * method that unhighlights the task
     * @param l
     * @param vbox
     */
    public void unHighlightTask(VBox l, VBox vbox){

        l.setEffect(null);
        vbox.requestFocus();

    }

    /**
     * Method that enables drag and drop for a card
     * @param card Card to be changed
     * @param c Column in which the card is placed
     * @param cards List of all card objects
     * @param i Position of card in that list
     * @return The new card which has drag and drop enabled
     */
    public VBox enableDragAndDrop(VBox card, Column c, List<Card> cards, int i) {
        //Methods for dragging and dropping the card
        int finalI1 = i;
        card.setOnDragDetected(new EventHandler<MouseEvent>() {      //When starting to drag remember the cardId
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = card.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(Long.toString(cards.get(finalI1).getId()));
                highlightedTask=card;
                db.setContent(content);
                db.setDragView(card.snapshot(null, null));

                event.consume();
            }
        });

        card.setOnDragOver(new EventHandler<DragEvent>() {          //Can only move on other objects
            @Override
            public void handle(DragEvent event) {
                if(event.getGestureSource() != card && event.getDragboard().hasString())
                    event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        int finalI2 = i;
        card.setOnDragDropped(new EventHandler<DragEvent>() {       //When dropped update positions
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                long oldId = Long.parseLong(db.getString());
                Card oldCard = server.getCardById(oldId);
                if(oldCard.getColumnId()==c.getId()) {              //Same column
                    int newPos = cards.get(finalI2).getPosition();
                    server.editCardPosition(oldId, newPos);
                    websocket.send("/app/updateCard", oldCard);
                    System.out.println(newPos+"  "+cards.size());
                    highlightedCard = cards.get(newPos-1);;
                    highlightedCardIndex = newPos-1;
                    highlightedListIndex = c.getPosition()-1;
                    event.setDropCompleted(true);
                }
                else {                                              //Changing columns
                    int newPos = cards.get(finalI2).getPosition();
                    server.editCardColumn(oldId,c.getId());
                    server.editCardPosition(oldId, newPos);
                    websocket.send("/app/updateCard", oldCard);
                    highlightedCard = cards.get(newPos-1);
                    highlightedCardIndex = newPos-1;
                    highlightedListIndex = c.getPosition()-1;
                    event.setDropCompleted(true);
                }
//                refresh(); We are using websocket.
                event.consume();

            }
        });
        //End of drag and drop methods
        return card;
    }

    /**
     * Set the boardID of a board
     * @param boardID the boardID of the board that list will be added to
     */
    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }


    /**
     * Shows the editCardTagsBoard scene
     */
    public void showEditCardTagsBoard() {
        mainCtrl.showEditCardTagsBoard(boardID);
    }
    /**
     * Delete the board
     */
    public void deleteBoard() {
        mainCtrl.showConfirmDeleteBoard(server.getBoardByID(boardID));
    }
}