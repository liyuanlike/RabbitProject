package com.liuxun.sub.test;

import com.liuxun.sub.domain.Instruction;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class TopicReceiver {
    @RabbitListener(queues = {"#{queueMessage.name}"})
    @RabbitHandler
    public void process(Instruction instruction) {
        System.out.println("Topic Receiver1  : " + instruction.getOperationID());
    }
}
