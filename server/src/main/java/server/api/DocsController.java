package server.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/docs")
public class DocsController {

    /**
     * @return method which takes the user to the documentation in resources
     */
    @GetMapping(path={"", "/"})
    public String documentation() {
        return "redirect:documentation/index.html";
    }
}
