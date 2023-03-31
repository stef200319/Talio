package server.api;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubscribeMappingController {

    @SubscribeMapping("/subscribe")
    public String sendConformation() {
        return "Subscribe successfully";
    }
}
