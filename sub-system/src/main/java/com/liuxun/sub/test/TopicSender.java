package com.liuxun.sub.test;

import com.liuxun.sub.domain.Instruction;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, i am message all";
        Instruction instruction = new Instruction("aaa", context,"aaaaa");
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("xxxx_exchange", "wwwww", instruction);
    }

}



