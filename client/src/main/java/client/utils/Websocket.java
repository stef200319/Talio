package client.utils;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class Websocket {

    private static final String SERVER = "http://localhost:8080/";


    private StompSession session = connect("ws://localhost:8080/websocket");

    /**
     * Connect websocket
     * @param url url to connect to
     * @return session
     */
    private StompSession connect (String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);

        stomp.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }


    /**
     * Registering for messages
     * @param dest path of message
     * @param type class of payload
     * @param consumer consumer
     * @param <T> generic class
     */
    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Send method
     * @param dest path
     * @param o payload
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }

}
