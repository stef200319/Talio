package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocsControllerTest {

    private DocsController sut;

    @BeforeEach
    public void setup() {
        sut = new DocsController();
    }

    @Test
    public void documentationTest() {
        String ret = sut.documentation();

        assertEquals("redirect:documentation/index.html", ret);
    }
}