package com.liuxun.sub.processors;

import com.alibaba.fastjson.JSON;
import com.liuxun.sub.domain.InstructResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public abstract class ResultReceiver {

    private static final Logger logger = LoggerFactory.getLogger(ResultReceiver.class);

    @RabbitListener(queues = {"#{acceptResultQueue.name}"})
    private void receiveAndProcessInstruct(InstructResult instructResult){

        logger.info("==Receive Result==={}=====", JSON.toJSON(instructResult));
        receviveInstructResult(instructResult);
    }

    public abstract void receviveInstructResult(InstructResult instructResult);
}

