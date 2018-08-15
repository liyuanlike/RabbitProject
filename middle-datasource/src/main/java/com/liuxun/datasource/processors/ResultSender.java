package com.liuxun.datasource.processors;

import com.liuxun.datasource.core.domain.GlobalInfo;
import com.liuxun.datasource.core.domain.InstructResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @apiNote 用于发送指令的处理结果
 * @author liuxun
 */

@Component
public class ResultSender {
    private static final Logger logger = LoggerFactory.getLogger(ResultSender.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendInstruct(InstructResult<?> result, String resultRouteKey){
        rabbitTemplate.convertAndSend(GlobalInfo.RESULT_TOPIC,resultRouteKey, result);
    }
}
