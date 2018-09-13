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
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
public class HeartBeatReceiver {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatReceiver.class);

    @RabbitListener(queues = "#{acceptHeartQueue.name}")
    @RabbitHandler
    private void receive(Message message) {
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 监控系统接收到消息：{}", body);
        logger.info("==== headers= {}", headers);

        Metrics metrics = JSON.parseObject(body, new TypeReference<Metrics>() {
        });
        String flag = (String) headers.get("FLAG");
        String ID = (String) headers.get("ID");
        if (flag.equals("PREPOSITION")) { // 是部委前置
            updatePrepositionHealthInfo(metrics);
        } else { // 其它的非部委前置系统 （综合治理的)）
            updateNotPreHealthInfo(metrics);
        }
    }

    // 处理前置心跳信息
    private void updatePrepositionHealthInfo(Metrics metrics) {
        ConcurrentHashMap<String, Metrics> preInfos = MonitorGlobalInfo.PRE_INFOS;
        String key = metrics.getFlag() + "_" + metrics.getId() + "_" + metrics.getIp();
        preInfos.put(key, metrics);
    }

    // 处理综合治理前置信息
    private void updateNotPreHealthInfo(Metrics metrics) {
        ConcurrentHashMap<String, Metrics> notPreInfos = MonitorGlobalInfo.NOT_PRE_INFOS;
        String key = metrics.getFlag() + "_" + metrics.getIp();
        notPreInfos.put(key,metrics);
    }

}
