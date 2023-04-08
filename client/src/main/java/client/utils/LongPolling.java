package client.utils;

import commons.Board;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class LongPolling {

    private static String SERVER = "http://localhost:8080/";

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Set server
     * @param server server to connect to
     */
    public void setSERVER(String server) {
        SERVER = "http://" + server + "/";
    }

    /**
     * Method which works as a register for updates in long polling for boards
     *
     * @param consumer Consumer of boards
     */
    public void registerForUpdates(Consumer<Board> consumer) {
        EXEC.submit(() -> {
            while(!Thread.interrupted()){
                var res = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER)
                    .path("board/updates")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(Response.class);
                if(res.getStatus() == 204) {
                    continue;
                }
                var b = res.readEntity(Board.class);
                consumer.accept(b);

            }

        });

    }

    /**
     * Method for stopping the long poll thread
     */
    public void stop() {
        EXEC.shutdownNow();
    }


}
