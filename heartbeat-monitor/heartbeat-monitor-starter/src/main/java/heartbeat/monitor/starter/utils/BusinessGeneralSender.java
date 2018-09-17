package heartbeat.monitor.starter.utils;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.config.MonitorGlobalInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessGeneralSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void generalSender(String jsonStr,Class<?> objectClass){

        Message message = MessageBuilder.withBody(jsonStr.getBytes())
                .setContentEncoding("utf-8")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("OBJECT_TYPE", objectClass.getSimpleName())
                .build();
        rabbitTemplate.convertAndSend(MonitorGlobalInfo.BUSINESS_MONITOR_TOPIC,"",message);
    }
}
