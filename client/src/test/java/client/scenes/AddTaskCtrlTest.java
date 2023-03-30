/*package client.scenes;

import javafx.application.Platform;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.utils.ServerUtils;
import commons.Card;
import javafx.scene.control.TextField;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTaskCtrlTest {

    private AddTaskCtrl addTaskCtrl;

    @Mock
    private ServerUtils server;

    @Mock
    private MainCtrl mainCtrl;

    @Mock
    private TextField taskName;

    @Mock
    private Text text;

    @BeforeAll
    static void initJFXRuntime() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        server = mock(ServerUtils.class);
        taskName = mock(TextField.class);
        mainCtrl = mock(MainCtrl.class);

        addTaskCtrl = new AddTaskCtrl(server, mainCtrl);
        addTaskCtrl.setTaskName(taskName);
    }

    @Test
    void testCancel() {
        addTaskCtrl.cancel();
        verify(taskName).clear();
        verify(mainCtrl).showBoardOverview();
    }

   /* @Test
    void testGetCard() {
        try {
            when(taskName.getText()).thenReturn("");
            Card card1 = addTaskCtrl.getCard();
            assertEquals("New List", card1.getTitle());
            assertEquals(1L, card1.getColumnId());

            when(taskName.getText()).thenReturn("Task 1");
            card1 = addTaskCtrl.getCard();
            assertEquals("Task 1", card1.getTitle());
            assertEquals(1L, card1.getColumnId());
        }catch (NullPointerException e){
            System.out.println("nu");
        }

    }

    @Test
    void testConfirm() {
        when(taskName.getText()).thenReturn("Task 1");
        addTaskCtrl.confirm();
       // verify(server).addCard(any(Card.class));
        verify(taskName).clear();
        verify(mainCtrl).showBoardOverview();
    }

    @Test
    void testShowTaskDetails(){
        addTaskCtrl.showTaskDetails();
        verify(mainCtrl, times(1)).showTaskDetails();
    }


}*/