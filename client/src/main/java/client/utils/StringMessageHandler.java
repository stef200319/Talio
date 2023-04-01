package client.utils;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;


public class StringMessageHandler extends StompSessionHandlerAdapter {

    /**
     * @param session          the client STOMP session
     * @param connectedHeaders the STOMP CONNECTED frame headers
     */
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/app/subscribe", this);

        StompSession.Subscription subscription = session.subscribe("/topic/periodic", this);

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
