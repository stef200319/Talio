package server.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private List<String> ids = new ArrayList<>();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        ids.add(SimpAttributesContextHolder.currentAttributes().getSessionId());
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void sendAllColumns(RESTEvent event) {

        System.out.println("message sent");
        System.out.println(event.getMessage());
        for (String id : ids) {
            System.out.println("send to: " + id);
            messagingTemplate.convertAndSendToUser(id, "/topic/periodic", "sent to user " + id);

        }

        messagingTemplate.convertAndSend("/topic/periodic", "all checked");

    }
}
