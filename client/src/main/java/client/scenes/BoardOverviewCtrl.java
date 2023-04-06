package client.scenes;

import client.utils.ServerUtils;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class BoardOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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
    private Label boardName;


    @FXML
    private HBox highlightedTask;

    private TextField highlightedTextField;

    private Card highlightedCard;

    private int highlightedCardIndex;

    /**
     * @param server the server that you want to connect to
     * @param mainCtrl the main screen?
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
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


    /**
     * Method that shows the quote overview on screen
     */
    public void showQuoteOverview() {
        mainCtrl.showOverview();
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
        boardName.setText(currentBoard.getTitle());
        List<Column> columns = server.getColumnsByBoardId(boardID);
        for(int i=0;i<columns.size();i++)
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


            //card.setOnMouseEntered(event -> card.setStyle("-fx-background-color: #F5DEB3"));
            //mai tb sa fie highlightedLabel/highlightedTask; si dupa ne legam
            //daca e highlighted atunci merg shortcutirle(verif asta din mainctrl si gt)
            //card.setOnMouseExited(event -> card.setStyle("-fx-text-fill: black;"));

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
            //highlightedTask = cardFinal;
            EventHandler<MouseEvent>  handler = event -> {

                if(highlightedTask!=null) {
                    unHighlightTask(highlightedTask, highlightedCard, "#F5DEB3",list);
                    this.highlightedCard=null;
                    this.highlightedTask=null;
                }
                setHighlightedTask(cardFinal, cards.get(finalI), "#000000",list,finalI);
                System.out.println(highlightedCard.getTitle());
                //cardFinal.setOnMouseEntered(null);
                //cardFinal.setOnMouseEntered(null);

            };
            cardFinal.setOnMouseEntered(handler);
            cardFinal.setOnMouseExited(event -> {
                //cardFinal.removeEventHandler(MouseEvent.MOUSE_ENTERED, handler);
                //cardFinal.setOnMouseEntered(null);
            });
            list.setOnKeyPressed(event1 ->{
                if(event1.getCode()==KeyCode.ENTER)
                {
                    mainCtrl.showTaskDetails(this.highlightedCard); //works only if ur mouse on it
                }
                if(event1.getCode()==KeyCode.DELETE || event1.getCode()==KeyCode.BACK_SPACE)
                {
                    server.deleteCard(this.highlightedCard); //bug
                }
                if(event1.getCode()==KeyCode.T)
                {

                }
                if(event1.getCode()==KeyCode.C)
                {
                    
                }
                if(event1.getCode()==KeyCode.DOWN && getHighlightedTask()!=null /*&& cards.size()>finalI*/){
                    //cardFinal.removeEventHandler(MouseEvent.MOUSE_ENTERED, handler);
                    //cardFinal.setOnMouseEntered(null);
                    unHighlightTask(highlightedTask, highlightedCard, "#F5DEB3",list);
                    this.highlightedCard = null;
                    this.highlightedTask = null;
                    //System.out.println(highlightedCard.getTitle());
                    highlightedCardIndex=highlightedCardIndex+1;
                    setHighlightedTask((HBox)cardContainer.getChildren().get(highlightedCardIndex),
                            cards.get(highlightedCardIndex), "#000000",list, highlightedCardIndex);
                    System.out.println("New highlighted card is no "+highlightedCard.getTitle());
                }
            });






            cardButtons.getChildren().add(deleteCard);

            card.getChildren().add(cardButtons);

            card = enableDragAndDrop(card, c, cards, i);

            cardContainer.getChildren().add(card);

        }
        cardContainer.setPrefWidth(380); // Set preferred width to 380 pixels
        cardContainer.setPrefHeight(500); // Set preferred height to 500 pixels
        list.getChildren().add(cardContainer);

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

    public void setHighlightedTask(HBox l, Card card, String colorcode, VBox vbox, int index){
        this.highlightedTask=l;
        l.setStyle(("-fx-background-color: #000000"));
        this.highlightedCard=card;
        server.editCardBackgroundColour(card, colorcode);
        card.setBgColour(colorcode);
        vbox.requestFocus();
        this.highlightedCardIndex = index;
    }
    public void unHighlightTask(HBox l, Card card, String colorcode, VBox xbox){

        l.setStyle("-fx-background-color: #F5DEB3");
        server.editCardBackgroundColour(card, colorcode);
        card.setBgColour(colorcode);
        xbox.requestFocus();
       // l.setBgColour("#F2F3F4");
        //server.editCardBackgroundColour(l, "#F2F3F4");
        //System.out.println(l.getTitle());
       // l.setStyle("-fx-background-color: white");

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
     * Delete the board
     */
    public void deleteBoard() {
        mainCtrl.showConfirmDeleteBoard(server.getBoardByID(boardID));
    }

}