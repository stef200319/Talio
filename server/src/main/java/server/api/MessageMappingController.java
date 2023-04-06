package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageMappingController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

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

    /**
     * @param message that is sent by the user
     * @param user metadata from the user
     * @return the message back
     */
    @MessageMapping("/private")
    @SendToUser("/queue/private")
    public String reply (@Payload String message, Principal user) {
        System.out.println("private message: " + user);
        return "hello " + message;
    }

}
