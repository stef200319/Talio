/*package client.scenes;

import commons.Column;
import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.control.TextField;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddListCtrlTest {

    private AddListCtrl addListCtrl;

    @Mock
    private MainCtrl mainCtrl;

    @Mock
    private ServerUtils server;

    @Mock
    private TextField listName;

    @BeforeEach
    void setUp() {
        server = mock(ServerUtils.class);
        listName = mock(TextField.class);
        mainCtrl = mock(MainCtrl.class);
        //MockitoAnnotations.openMocks(this);

        addListCtrl = new AddListCtrl(server, mainCtrl);
        addListCtrl.setListName(listName);
    }

    @Test
    void testCancel() {
        addListCtrl.cancel();
        verify(listName, times(1)).clear();
        verify(mainCtrl, times(1)).showBoardOverview();
    }

    @Test
    void testGetList() {
        when(listName.getText()).thenReturn("Test List");
        Column column = new Column("Test List",1 );
        assertEquals(column, addListCtrl.getList());
        when(listName.getText()).thenReturn("");
        column = new Column("New List", 1);
        assertEquals(column, addListCtrl.getList());
    }

    @Test
    void testConfirm() {
        when(listName.getText()).thenReturn("Test List");
        addListCtrl.confirm();
        verify(server, times(1)).addColumn(new Column("Test List",1));
        verify(listName, times(1)).clear();
        verify(mainCtrl, times(1)).showBoardOverview();
    }
}*/