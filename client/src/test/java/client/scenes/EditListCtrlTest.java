package client.scenes;

import client.utils.ServerUtils;
import commons.Column;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;



public class EditListCtrlTest {

    @Mock
    private ServerUtils server;
    @Mock
    private EditListCtrl editListCtrl;
    @Mock
    private MainCtrl mainCtrl;
    @FXML
    private TextField listName;

    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() {
        editListCtrl = new EditListCtrl(server,mainCtrl);
        listName = new TextField("test");
        listName.setText("test");
        editListCtrl.setListName(listName);
    }

    @Test
    public void columnToEditTest() {
        Column c = new Column("title",1);
        editListCtrl.setColumnToEdit(c);
        editListCtrl.setColumnToEdit(c);
        assertEquals(c,editListCtrl.getColumnToEdit());
    }

    @Test
    public void cancelTest() {
        editListCtrl.cancel();
        verify(mainCtrl, times(1)).showBoardOverview();
    }

    @Test
    public void confirmTest() {
        Column c = new Column("title",1);
        editListCtrl.setColumnToEdit(c);
        editListCtrl.confirm();
        verify(server, times(1)).editColumnTitle(c, "test");
        verify(mainCtrl, times(1)).showBoardOverview();
        verify(listName, times(1)).clear();
    }

    @Test
    public void getTitleTest() {
        assertEquals("title",editListCtrl.getTitle());
    }

}
