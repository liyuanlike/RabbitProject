package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.GridManSender;
import projects.rabbitmq.starter.processors.PrePositionSender;

@RestController
@ConditionalOnProperty(prefix = "projects.system", value = "flag",havingValue = ProjectsFlags.GRIDMAN_FLAG)
public class TestGridManSendController {

    @Autowired
    private GridManSender gridManSender;

    @GetMapping("/send")
    public Object send(){
        final Boolean aBoolean = gridManSender.sendGridManEvent("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
    @GetMapping("/send1")
    public Object send1(){
        final Boolean aBoolean = gridManSender.sendGridManExecution("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
}
