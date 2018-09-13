package heartbeat.monitor.starter.processors;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.Metrics;
import heartbeat.monitor.starter.config.MonitorGlobalInfo;
import heartbeat.monitor.starter.properties.MonitorProperties;
import heartbeat.monitor.starter.utils.MetricsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @apiNote 定期向监控系统发送心跳
 */
@Component
@ConditionalOnMissingBean(name = {"acceptHeartQueue"})
public class HeartBeatSender {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private MonitorProperties properties;

    @Scheduled(fixedRate = 5000) // 定时发送心跳 每隔5秒发送一次
    private void sendHeartBeatToMonitor(){
        if (properties.getFlag().equals(MonitorGlobalInfo.MONITOR_FLAG)){
            return;
        }
        // 获取指标
        Metrics metrics = new Metrics();
        metrics.setFlag(properties.getFlag());
        metrics.setId(properties.getId());
        metrics.setName(properties.getName());
        metrics.setTimestamp(System.currentTimeMillis());
        metrics.setIp(MetricsUtil.getLocalIp());
        metrics.setMemPercent(MetricsUtil.getMemoryPercent());

        Message message = MessageBuilder.withBody(JSON.toJSONString(metrics).getBytes())
                .setContentEncoding("utf-8")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("FLAG", metrics.getFlag())
                .setHeader("ID", metrics.getId())
                .build();
        rabbitTemplate.convertAndSend(MonitorGlobalInfo.MONITOR_EXCHANGE,"",message);
    }
}
