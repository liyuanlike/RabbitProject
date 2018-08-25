package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.DirectSender;
import projects.rabbitmq.starter.processors.PrePositionSender;

@RestController
@ConditionalOnProperty(prefix = "projects.system", value = "flag",havingValue = ProjectsFlags.DIRECT_FLAG)
public class TestDirectSendController {

    @Autowired
    private DirectSender directSender;

    @GetMapping("/send")
    public Object send(){
        final Boolean aBoolean = directSender.sendInstructionToPrePosition("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }

    @GetMapping("/send1")
    public Object send1(){
        final Boolean aBoolean = directSender.sendInstructionToGridMan("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
    @GetMapping("/send2")
    public Object send2(){
        final Boolean aBoolean = directSender.sendProgramme("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
}
