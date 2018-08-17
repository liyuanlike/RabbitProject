package com.liuxun.datasource.processors;

import com.liuxun.datasource.core.domain.GlobalInfo;
import com.liuxun.datasource.core.domain.Instruction;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 仅仅测试使用
 */

@Component
public class TestSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, i am message all";
        Instruction instruction = new Instruction("aaa", context,"ttt");
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(GlobalInfo.INSTRUCT_CENTER_TOPIC, "wwwww", instruction);
    }
}
