package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.List;

public class JoinBoardByKeyCtrl {

    private ServerUtils server;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField boardKeyField;

    private MainCtrl mainCtrl;

    private long prevBoardId;

    /**
     * @param server
     * @param mainCtrl
     */
    @Inject
    public JoinBoardByKeyCtrl(ServerUtils server, MainCtrl mainCtrl)
    {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method that lets the user join a board by its key, by introducing
     * a key into the text field
     */
    public void confirm()
    {
        long boardId;
        textFieldAllowOnlyNumbers();
        String text = boardKeyField.getText();
        boardId = Long.parseLong(text);
        List<Board> boards = server.getAllBoardsWithoutServers();
        if(server.getBoardByID(boardId)!=null) {
            if (boards.contains(server.getBoardByID(boardId))) {
                mainCtrl.showBoardOverview(boardId);
                boardKeyField.setEffect(null);

            } else {
                boardKeyField.clear();
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.RED);
                boardKeyField.setEffect(dropShadow);
            }
        }


    }

    /**
     * method that returns to the board overview
     */
    public void cancel()
    {
        textFieldAllowOnlyNumbers();
        mainCtrl.showBoardOverview(prevBoardId);
        boardKeyField.clear();

    }

    /**
     * method that sets the previous board id
     * @param boardId the id of the board
     */
    public void setPrevBoardId(long boardId){
        this.prevBoardId = boardId;
    }

    /**
     * method sets the text field property of accepting only numbers
     */
    public void textFieldAllowOnlyNumbers()
    {
        boardKeyField.textProperty().addListener(((observable, oldValue, newValue) -> {
            String filteredText = newValue.replaceAll("[^\\d]", "");
            boardKeyField.setText(filteredText);
        }));
    }


}
