package server.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

public class MessageMappingController {

    /**
     * @param message of the exception
     * @return response
     */
    @MessageMapping("/request")
    @SendTo("/queue/responses")
    public String handleMessageWithExplicitResponse(String message) {
        if (message.equals("zero")) {
            throw new RuntimeException(String.format("'%s' is rejected", message));
        }
        return "response to " + HtmlUtils.htmlEscape(message);
    }

}
