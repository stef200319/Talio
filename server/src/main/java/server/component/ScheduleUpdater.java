package server.component;

import org.springframework.messaging.core.MessageSendingOperations;
import server.services.ColumnService;

public class ScheduleUpdater {

    private final MessageSendingOperations<String> message;
    private ColumnService columnService;

    public ScheduleUpdater(MessageSendingOperations<String> message, ColumnService columnService) {
        this.message = message;
        this.columnService = columnService;
    }

//    @Scheduled
//    public void updateBoard() {
//        message.convertAndSend("/topic/periodic", columnService.getAll());
//    }
}
