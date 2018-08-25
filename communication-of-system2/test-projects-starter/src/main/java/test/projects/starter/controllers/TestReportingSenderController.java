package test.projects.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.ReportingSender;
import projects.rabbitmq.starter.processors.WeChatSender;

@RestController
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.REPORTING_FLAG)
public class TestReportingSenderController {
    @Autowired
    private ReportingSender reportingSender;

    @GetMapping("/send")
    public Object send(){
        final Boolean aBoolean = reportingSender.sendCoordinationEvent("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
}
