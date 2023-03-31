package client.utils;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;


public class StringMessageHandler extends StompSessionHandlerAdapter {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/app/subscribe", this);

        StompSession.Subscription subscription = session.subscribe("/topic/periodic", this);

        System.out.println(subscription.getSubscriptionId());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("payload received");
        System.out.println(payload);
    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command,
                                StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();

    }
    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("WebSocket session disconnected");
    }
}
