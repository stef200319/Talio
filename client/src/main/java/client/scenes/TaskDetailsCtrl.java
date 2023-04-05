package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Column;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TaskDetailsCtrl {
    private Card cardToShow;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;



    @FXML
    private Label cardTitle;

    @FXML
    private Label cardDescription;


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

     * private event handler for a key event that listens
     *       for the "Esc" key to be pressed
     * when the "Esc" key is pressed, the method showBoardOverview()
     *       is called to switch to the Board Overview scene
     */
   /* private EventHandler<KeyEvent> backToOverview = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ESCAPE)
            {
                showBoardOverview();
            }
        }
    };*/

    /**
     * @return the backToOverview event handler
     */
  //  public EventHandler<KeyEvent> getBackToOverview() {
      //  return backToOverview;
  //  }
    /**
     * Set the Card whose details have to be displayed
     * @param cardToShow the Card whose details have to be displayed
     */

    public void setCardToShow(Card cardToShow) {
        this.cardToShow = cardToShow;
        cardTitle.setText(cardToShow.getTitle());
        cardDescription.setText(cardToShow.getDescription());
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
}
