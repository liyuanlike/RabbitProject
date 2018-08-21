package test.preposition.starter.controllers;

import com.global.component.domain.PrePositionEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import preposition.rabbitmq.starter.processors.PrePositionEventSender;

import java.util.Date;
import java.util.UUID;

@RestController
public class TestEventSend {
    @Autowired
    private PrePositionEventSender prePositionEventSender;

    @GetMapping("/pp_send")
    public Object testSendEvent(){
        PrePositionEventMessage message = new PrePositionEventMessage();
        message.setDescription("这是一个简单的部委前置事件测试");
        message.setEventID(UUID.randomUUID().toString().replaceAll("-",""));
        message.setOccurTime(new Date());
        Boolean isSuccess = prePositionEventSender.sendGridManEvent(message);
        return isSuccess? "success":"failure";
    }
}
