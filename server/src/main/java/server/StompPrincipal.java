package server;

import java.security.Principal;

class StompPrincipal implements Principal {
    private final String name;

    /**
     * @param name Name of the user
     */
    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}