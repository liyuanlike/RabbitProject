package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.WeChatSender;

@RestController
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.WECHAT_FLAG)
public class TestWeChatSenderController {
    @Autowired
    private WeChatSender weChatSender;

    @GetMapping("/send")
    public Object send1(){
        final Boolean aBoolean = weChatSender.sendWeChatEvent("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
}
