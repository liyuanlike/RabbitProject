package reporting.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.global.component.config.GlobalCommunicationInfo;
import com.global.component.domain.Coordination;
import com.global.component.domain.MessageBusinessHeaderKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

/**
 * @apiNote 为接报系统封装的用于发送协调事件的组件
 * @author liuxun
 */
public class ReportCoordinationSender {
    private static final Logger logger = LoggerFactory.getLogger(ReportCoordinationSender.class);
    private AmqpTemplate rabbitTemplate;

    public ReportCoordinationSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 为接报系统封装发送协调事件的方法
     * @param coordination  协调事件
     * @return
     */
    public Boolean sendCoordination(Coordination coordination){
        Boolean isSuccess = true;
        logger.info("==== 接报系统开始发送协调事件: {}", JSON.toJSONString(coordination));
        try {
            logger.info("==== 公众号系统开始发送事件信息\t routeKey={}",GlobalCommunicationInfo.WECHAT_EVENT_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(coordination).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE, Coordination.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.REPORTING_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.DIRECT_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.REPORTING_COORDINATION_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 发送协调事件失败 原因: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 发送协调事件成功 ========");
            return isSuccess;
        }
    }
}
