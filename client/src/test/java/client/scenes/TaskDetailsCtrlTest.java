package client.scenes;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskDetailsCtrlTest {

    @Mock
    public ServerUtils mockServer;

    @Mock
    public MainCtrl mockMainCtrl;

    public TaskDetailsCtrl taskDetailsCtrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskDetailsCtrl = new TaskDetailsCtrl(mockServer, mockMainCtrl);
    }

    @Test
    public void showBoardOverview_callsMainCtrlShowBoardOverview() {
        taskDetailsCtrl.showBoardOverview();
        verify(mockMainCtrl, times(1)).showBoardOverview();
    }

}
