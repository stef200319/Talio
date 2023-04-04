package client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Column;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.List;


public class StringMessageHandler extends StompSessionHandlerAdapter {

    private final ObjectMapper objectMapper;

    /**
     * Sets a new objectMapper
     */
    public StringMessageHandler() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * @param session          the client STOMP session
     * @param connectedHeaders the STOMP CONNECTED frame headers
     */
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/app/subscribe", this);
        session.subscribe("/user/queue/private", this);


        StompSession.Subscription subscription = session.subscribe("/topic/periodic", this);

        session.send("/app/private", "hello world");
        System.out.println(subscription.getSubscriptionId());
    }

    /**
     * @param headers the headers of a message
     * @return String.class
     */
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    /**
     * @param headers the headers of the frame
     * @param payload the payload, or {@code null} if there was no payload
     */
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("payload received");
        System.out.println(payload);

        if (headers.containsKey("method") && headers.get("method").contains("getAllColumns")) {
            try {
                List<Column> columns = objectMapper.readValue((String) payload, objectMapper.getTypeFactory().
                        constructCollectionType(List.class, Column.class));
                System.out.println(columns.get(0).toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param session   the client STOMP session
     * @param command   the STOMP command of the frame
     * @param headers   the headers
     * @param payload   the raw payload
     * @param exception the exception
     */
    @Override
    public void handleException(StompSession session, @Nullable StompCommand command,
                                StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();

    }

    /**
     * @param session   the client STOMP session
     * @param exception the exception that occurred
     */
    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("WebSocket session disconnected");
    }
}
