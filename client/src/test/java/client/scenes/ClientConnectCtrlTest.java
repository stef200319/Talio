package client.scenes;

import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientConnectCtrlTest {

    @Mock
    private ServerUtils server;

    @Mock
    private MainCtrl mainCtrl;

    private ClientConnectCtrl clientConnectCtrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientConnectCtrl = new ClientConnectCtrl(server, mainCtrl);
    }

    @Test
    public void showWorkspace_callsMainCtrl_showWorkspace() {
        clientConnectCtrl.showWorkspace();
        verify(mainCtrl, times(1)).showWorkspace();
    }
}
