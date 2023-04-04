package server.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Board;
import commons.Column;
import commons.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import server.services.ColumnService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final ColumnService columnService;
    private final ObjectMapper objectMapper;

    public WebSocketEventListener(ColumnService columnService, ObjectMapper objectMapper) {
        this.columnService = columnService;
        this.objectMapper = objectMapper;
    }

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
        if (!event.getMessage().equals("everything was found")) {
            return;
        }

        String json = writeToJSON(event.getSource());

        Map<String, Object> headers = new HashMap<>();
        headers.put("method", "getAllColumns");

        System.out.println("message sent");
        System.out.println(event.getMessage());
        for (User user : users) {
            System.out.println("send to: " + user.getName());
            messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", json, headers);
        }
    }

    /**
     * @param event that is sent out
     */
    @EventListener
    public String deleteBoard(RESTEvent event) {
        if (!event.getMessage().equals("board was deleted")) {
            return null;
        }

        String json = writeToJSON(event.getSource());

        Board b = (Board) event.getSource();
        Map<String, Object> headers = new HashMap<>();
        headers.put("Service", "board");
        headers.put("Method", "delete");

        for (User user : users) {
            if (user.containsBoardId(b.getId())) {
                messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", json, headers);
                user.removeBoardId(b.getId());
            }
        }

        logger.info("Board with id " + b.getId() + " was deleted.");

        return json;
    }

    @EventListener
    public String editBoardTitle(RESTEvent event) {
        if (!event.getMessage().equals("board was edited")) {
            return null;
        }

        String json = writeToJSON(event.getSource());

        Board b = (Board) event.getSource();
        Map<String, Object> headers = new HashMap<>();
        headers.put("Service", "board");
        headers.put("Method", "edit");

        for (User user : users) {
            if (user.containsBoardId(b.getId())) {
                messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", json, headers);
            }
        }

        logger.info("Board with id " + b.getId() + " was renamed to: " + b.getTitle());

        return json;
    }

    @EventListener
    public String addColumn(RESTEvent event) {
        if (!event.getMessage().equals("column was created")) {
            return null;
        }

        String json = writeToJSON(event.getSource());

        Column c = (Column) event.getSource();
        Map<String, Object> headers = new HashMap<>();
        headers.put("Service", "column");
        headers.put("Method", "add");

        for (User user : users) {
            if (user.containsBoardId(c.getBoardId())) {
                messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", json, headers);
            }
        }

        logger.info("Column with id " + c.getId() + " and board id " + c.getBoardId() + " was created and named: " + c.getTitle());

        return json;
    }

    @EventListener
    public String editColumn(RESTEvent event) {
        if (!event.getMessage().equals("column was updated")) {
            return null;
        }

        String json = writeToJSON(event.getSource());

        Column c = (Column) event.getSource();
        Map<String, Object> headers = new HashMap<>();
        headers.put("Service", "column");
        headers.put("Method", "edit");

        for (User user : users) {
            if (user.containsBoardId(c.getBoardId())) {
                messagingTemplate.convertAndSendToUser(user.getName(), "/queue/private", json, headers);
            }
        }

        logger.info("Column with id " + c.getId() + " and board id " + c.getBoardId() + " was updated and renamed to " + c.getTitle());

        return json;
    }

    /**
     * @param o object that needs to be serialized
     * @return json string
     */
    private String writeToJSON(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("JSON serialization failed");
            throw new RuntimeException(e);
        }
    }
}
