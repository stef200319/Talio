package client.scenes;

import static org.mockito.Mockito.*;

import client.utils.LongPolling;
import client.utils.Websocket;
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

    @Mock
    private LongPolling longPolling;

    @Mock
    private Websocket websocket;

    private ClientConnectCtrl clientConnectCtrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientConnectCtrl = new ClientConnectCtrl(server, mainCtrl, websocket, longPolling);
    }

    @Test
    public void showHelp() {
        clientConnectCtrl.showHelpScreen();
        verify(mainCtrl, times(1)).showHelpScreen();
    }
}
