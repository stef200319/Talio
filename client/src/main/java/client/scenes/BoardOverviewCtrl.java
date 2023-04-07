package client.scenes;

import client.utils.ServerUtils;
import client.utils.StringMessageHandler;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.Column;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private WebSocketStompClient stompClient;
    private static final Logger logger = LoggerFactory.getLogger(BoardOverviewCtrl.class);

    private long boardID = Long.MIN_VALUE;

    @FXML
    private HBox columnContainer;

    @FXML
    private Button createBoardButton;

    @FXML
    private Button myBoardsButton;


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


    @FXML
    private HBox highlightedTask;

    private TextField highlightedTextField;

    private Card highlightedCard;

    private int highlightedCardIndex;

    private boolean highlightedByKey=false;

    private int highlightedListIndex;



    private Column highlightedColumn;

    private VBox highlightedList;

    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

        stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))
        ));
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
        StompSessionHandler sessionHandler = new StringMessageHandler();
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.connect("http://localhost:8080/websocket-stomp", sessionHandler);

        logger.info("connected to websocket");
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //refresh();
                });
            }
        }, 0, 1000);
    }


    /**
     * Method that shows the quote overview on screen
     */
    public void showQuoteOverview() {
        mainCtrl.showOverview();
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


    @SuppressWarnings("checkstyle:MethodLength")
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
                }
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
            HBox card = new HBox(80);                   //card box
            card.setAlignment(Pos.CENTER_RIGHT);
            card.setStyle("-fx-background-color: "+cards.get(finalI).getBgColour()+"; -fx-border-style: " +
                    "solid; -fx-background-radius: 5px; -fx-border-radius: 5px;" +
                    "-fx-border-color: "+cards.get(finalI).getBorderColour());

            Label s = new Label(cards.get(i).getTitle());


            Font fontList = Font.font(cards.get(i).getFontType(),
                    cards.get(i).isFontStyleBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                    cards.get(i).isFontStyleItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                    12);
            s.setFont(fontList);
            s.setTextFill(Color.web(cards.get(i).getFontColour()));

            card.getChildren().add(s);


            VBox cardButtons = new VBox(5);             //box for details and delete buttons

            cardButtons.setAlignment(Pos.CENTER);

            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Do something when the card is clicked
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
                    refresh();
                }
            });

            HBox cardFinal = card;


            EventHandler<MouseEvent>  handler = event -> {

                if(highlightedByKey == false) {
                    highlightedByKey = false;
                    if (highlightedTask != null) {
                        unHighlightTask(highlightedTask, cardContainer);
                    }

                    highlightedColumn = server.getColumnByColumnId(cards.get(finalI).getColumnId());
                    highlightedListIndex = highlightedColumn.getPosition()-1;
                    VBox listt = (VBox)columnContainer.getChildren().get(highlightedListIndex);

                    VBox cardList = (VBox)listt.getChildren().get(2);
                    HBox cardToHighlight = (HBox) cardList.getChildren().get(finalI);
                    setHighlightedTask(cardToHighlight,
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
                    server.deleteCard(cards.get(finalI));


                    refresh();
                }
                if(event1.getCode()==KeyCode.T)
                {

                }
                if(event1.getCode()==KeyCode.C)
                {

                }
                if(event1.getCode()==KeyCode.DOWN && getHighlightedTask()!=null && highlightedCardIndex<cardContainer.getChildren().size()-1){
                    highlightedByKey = true;

                    unHighlightTask(highlightedTask, cardContainer);

                    highlightedCardIndex=highlightedCardIndex+1;
                    VBox listt = (VBox)columnContainer.getChildren().get(highlightedListIndex);//get(highlightedListIndex);

                    VBox cardList = (VBox)listt.getChildren().get(2);
                    HBox cardToHighlight = (HBox) cardList.getChildren().get(highlightedCardIndex);
                    setHighlightedTask(cardToHighlight,
                            cardContainer, highlightedCardIndex, highlightedListIndex);

                }
                if(event1.getCode()==KeyCode.UP && getHighlightedTask()!=null && highlightedCardIndex>0)
                {
                    highlightedByKey = true;
                    VBox listt = (VBox)columnContainer.getChildren().get(highlightedListIndex);

                    VBox cardList = (VBox)listt.getChildren().get(2);

                    unHighlightTask(highlightedTask, cardContainer);

                    highlightedCardIndex=highlightedCardIndex-1;
                    HBox cardToHighlight = (HBox) cardList.getChildren().get(highlightedCardIndex);
                    setHighlightedTask(cardToHighlight,
                            cardContainer, highlightedCardIndex, highlightedListIndex);

                }
                if(event1.getCode()==KeyCode.LEFT && getHighlightedTask()!=null && highlightedListIndex>0)
                {
                    highlightedByKey = true;


                    VBox listt;

                    unHighlightTask(highlightedTask, cardContainer);




                    //unHighlightTask(highlightedTask, highlightedCard, "#F5DEB3",cardContainer);

                    this.highlightedListIndex = highlightedListIndex-1;
                    listt = (VBox)columnContainer.getChildren().get(highlightedListIndex);
                    VBox cardList = (VBox)listt.getChildren().get(2);
                    HBox cardToHighlight = (HBox) cardList.getChildren().get(highlightedCardIndex);
                    setHighlightedTask(cardToHighlight ,cardContainer, highlightedCardIndex, highlightedListIndex);
                }
                if(event1.getCode()==KeyCode.RIGHT && getHighlightedTask()!=null && highlightedListIndex<server.getColumnsByBoardId(boardID).size()-1)
                {
                    highlightedByKey = true;
                    VBox listt;
                    unHighlightTask(highlightedTask, cardContainer);

                    this.highlightedListIndex = highlightedListIndex+1;
                    listt = (VBox)columnContainer.getChildren().get(highlightedListIndex);
                    VBox cardList = (VBox)listt.getChildren().get(2);
                    HBox cardToHighlight = (HBox) cardList.getChildren().get(highlightedCardIndex);
                    setHighlightedTask(cardToHighlight,
                            cardContainer, highlightedCardIndex, highlightedListIndex);

                }
            });






            cardButtons.getChildren().add(deleteCard);

            card.getChildren().add(cardButtons);

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

    public HBox getHighlightedTask(){return this.highlightedTask;}

    public void setHighlightedCard(Card card){this.highlightedCard = card;}
    public void setHighlightedTask(HBox hbox){this.highlightedTask = hbox;}
    public void setHighlightedByKey(boolean bool){this.highlightedByKey = bool;}

    public void setHighlightedTask(HBox l, VBox vbox, int index, int indexList){
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
    public void unHighlightTask(HBox l, VBox xbox){

        l.setEffect(null);
        xbox.requestFocus();

    }


    /**
     * Method that enables drag and drop for a card
     * @param card Card to be changed
     * @param c Column in which the card is placed
     * @param cards List of all card objects
     * @param i Position of card in that list
     * @return The new card which has drag and drop enabled
     */
    public HBox enableDragAndDrop(HBox card, Column c, List<Card> cards, int i) {
        //Methods for dragging and dropping the card
        int finalI1 = i;
        card.setOnDragDetected(new EventHandler<MouseEvent>() {      //When starting to drag remember the cardId
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = card.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(Long.toString(cards.get(finalI1).getId()));
                db.setContent(content);

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
                    event.setDropCompleted(true);
                }
                else {                                              //Changhing columns
                    int newPos = cards.get(finalI2).getPosition();
                    server.editCardColumn(oldId,c.getId());
                    server.editCardPosition(oldId, newPos);
                    event.setDropCompleted(true);
                }
                refresh();
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