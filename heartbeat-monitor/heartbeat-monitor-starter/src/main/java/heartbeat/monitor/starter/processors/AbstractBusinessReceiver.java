package heartbeat.monitor.starter.processors;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import heartbeat.monitor.starter.config.MonitorGlobalInfo;
import heartbeat.monitor.starter.domain.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Map;

@ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
public abstract class AbstractBusinessReceiver {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBusinessReceiver.class);

    @RabbitListener(queues = "#{acceptBusinessQueue.name}")
    @RabbitHandler
    private void receive(Message message) {
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 监控系统接收到业务数据：{}", body);
        logger.info("==== headers= {}", headers);
        String objectType = (String) headers.get("OBJECT_TYPE");
        resolveBusiness(body,objectType);
    }

    public abstract void resolveBusiness(String body,String  objectType);

}
