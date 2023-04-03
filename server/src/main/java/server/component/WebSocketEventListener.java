package server.component;

import commons.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private List<User> users = new ArrayList<>();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * @param event the event listener listens to
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        User user = new User(sha.getUser().getName());

        users.add(user);
        logger.info("Received a new web socket connection");
    }

    /**
     * @param event the event listener listens to
     */
    @EventListener
    public void sendAllColumns(RESTEvent event) {

        System.out.println("message sent");
        System.out.println(event.getMessage());
        for (User user : users) {
            System.out.println("send to: " + user.getName());
            messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", "sent to user " + user.getName());
        }

        messagingTemplate.convertAndSend("/topic/periodic", "all checked");

    }
}
