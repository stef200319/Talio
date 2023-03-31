package server.component;

import org.springframework.context.ApplicationEvent;

public class RESTEvent extends ApplicationEvent {
    private String message;

    public RESTEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
