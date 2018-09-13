package test.projects.starter.controllers;

import heartbeat.monitor.starter.processors.HeartBeatResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = "MONITOR")
public class TestMonitorController {

    @Autowired
    private HeartBeatResolver heartBeatResolver;

    @GetMapping("/health")
    public Object getHealth(){
        return heartBeatResolver.getHealthInfo();
    }
}
