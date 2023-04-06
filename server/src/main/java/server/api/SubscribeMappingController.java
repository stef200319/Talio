package server.api;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubscribeMappingController {

    /**
     * @return that the subscription was succesfull
     */
    @SubscribeMapping("/subscribe")
    public String sendConformation() {
        return "Subscribe successfully";
    }
}
