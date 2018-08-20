package rabbit.sender.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TopicSender topicSender;

    @GetMapping("/test")
    public Object test(){
        topicSender.send();
        return "success";
    }
}
