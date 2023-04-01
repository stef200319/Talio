package server.component;

import org.springframework.context.ApplicationEvent;

public class RESTEvent extends ApplicationEvent {
    private String message;

    /**
     * @param source the object
     * @param message that the object comes with
     */
    public RESTEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
