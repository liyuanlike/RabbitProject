package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.PlanSender;
import projects.rabbitmq.starter.processors.WeChatSender;

@RestController
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.PLAN_FLAG)
public class TestPlanSenderController {
    @Autowired
    private PlanSender planSender;

    @GetMapping("/send")
    public Object send1(){
        final Boolean aBoolean = planSender.sendPlan("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
}
