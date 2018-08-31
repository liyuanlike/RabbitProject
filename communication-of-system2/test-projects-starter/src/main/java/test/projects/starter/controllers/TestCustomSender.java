package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.CustomSender;

@RestController
@RequestMapping("/custom")
public class TestCustomSender {

    @Autowired
    private CustomSender customSender;

    @GetMapping("send")
    public Object send(){
        final Boolean aBoolean = customSender.sendMessageToAllPrePosition("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
    @GetMapping("send1")
    public Object send1(){
        final Boolean aBoolean = customSender.sendMessageToApointedPrePosition("{}", Object.class,"AAA");
        return aBoolean ? "success" : "failure";
    }
    @GetMapping("send2")
    public Object send2(){
        final Boolean aBoolean = customSender.sendMessageToAppointedSystem("{}", Object.class, ProjectsFlags.WECHAT_FLAG,ProjectsFlags.REPORTING_FLAG,ProjectsFlags.DIRECT_FLAG);
        return aBoolean ? "success" : "failure";
    }
}
