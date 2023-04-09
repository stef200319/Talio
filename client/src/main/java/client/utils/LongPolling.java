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

    private String SERVER = null;

    private ExecutorService EXEC = Executors.newSingleThreadExecutor();

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
     * Sets the server address
     * @param server new address
     */
    public void setSERVER(String server) {
        this.SERVER = "http://" + server + "/";
        EXEC = Executors.newSingleThreadExecutor();
    }

    /**
     * Method for stopping the long poll thread
     */
    public void stop() {
        EXEC.shutdownNow();
    }


}
