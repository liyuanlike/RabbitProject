package test.gridman.starter.controllers;

import com.global.component.domain.GridManEventMessage;
import gridman.rabbitmq.starter.processors.GridManEventSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class SendEventController {
    @Autowired
    private GridManEventSender gridManEventSender;

    @GetMapping("/gm_send")
    public Object send(){
        GridManEventMessage message = new GridManEventMessage();
        message.setDescription("这是一个网格员类型事件的测试消息");
        message.setEventID(UUID.randomUUID().toString().replaceAll("-",""));
        message.setOccurTime(new Date());
        Boolean isSuccess = gridManEventSender.sendGridManEvent(message);
        return isSuccess? "success":"failure";
    }
}
