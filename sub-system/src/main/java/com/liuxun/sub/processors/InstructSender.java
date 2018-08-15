package com.liuxun.sub.processors;

import com.liuxun.sub.domain.GlobalInfo;
import com.liuxun.sub.domain.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructSender {
    private static final Logger logger = LoggerFactory.getLogger(InstructSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendInstruct(String operationID,String resultRouteKey,String id){
        Instruction instruction = new Instruction(operationID, resultRouteKey,id);
        logger.info("==开发发送指令===");
        rabbitTemplate.convertAndSend(GlobalInfo.INSTRUCT_CENTER_TOPIC,"", instruction);
    }

    public AmqpTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
