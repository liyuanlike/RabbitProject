package test.projects.starter.controllers;

import heartbeat.monitor.starter.domain.msgs.PreReceiveInstruction;
import heartbeat.monitor.starter.processors.business.MonitorPreSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.PrePositionSender;

import java.util.Date;

@RestController
@ConditionalOnProperty(prefix = "projects.system", value = "flag",havingValue = ProjectsFlags.PREPOSITION_FLAG)
public class TestPrePositionSendController {

    @Autowired
    private PrePositionSender prePositionSender;

    @Autowired
    private MonitorPreSender monitorPreSender;

    @GetMapping("/send")
    public Object send(){
        final Boolean aBoolean = prePositionSender.sendPrePositionEvent("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }
    @GetMapping("/send1")
    public Object send1(){
        final Boolean aBoolean = prePositionSender.sendPrePositionExecution("{}", Object.class);
        return aBoolean ? "success" : "failure";
    }

    @GetMapping("/send2")
    public Object sendMonitorPreReceiveInstruction(){
        PreReceiveInstruction preReceiveInstruction = new PreReceiveInstruction();
        preReceiveInstruction.setEventId("eventId"+new Date());
        preReceiveInstruction.setReceiveTime(new Date());
        preReceiveInstruction.setResolved(false);
        preReceiveInstruction.setInstructionId("instructionId"+new Date());
        monitorPreSender.sendReceiveInstruction(preReceiveInstruction);
        return "success";
    }
}
