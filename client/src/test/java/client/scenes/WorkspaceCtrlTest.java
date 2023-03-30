package client.scenes;

import static org.mockito.Mockito.*;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkspaceCtrlTest {

    private WorkspaceCtrl workspaceCtrl;
    private ServerUtils server;
    private MainCtrl mainCtrl;

    @BeforeEach
    void setUp() {
        server = mock(ServerUtils.class);
        mainCtrl = mock(MainCtrl.class);
        workspaceCtrl = new WorkspaceCtrl(server, mainCtrl);
    }

//    @Test
//    void testShowBoardOverview() {
//        workspaceCtrl.showBoardOverview();
//        verify(mainCtrl, times(1)).showBoardOverview();
//    }
}
