package preposition.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.global.component.config.GlobalCommunicationInfo;
import com.global.component.domain.GridManEventMessage;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.PrePositionEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

/**
 * @apiNote 为部委前置系统封装 向接报系统发送事件的服务类
 */
public class PrePositionEventSender {
    private static final Logger logger = LoggerFactory.getLogger(PrePositionEventSender.class);
    private AmqpTemplate rabbitTemplate;

    public PrePositionEventSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean sendGridManEvent(PrePositionEventMessage prePositionEventMessage){
        Boolean  isSuccess = true;
        logger.info("==== 部委前置系统发送事件到接报系统.....\t{}", JSON.toJSONString(prePositionEventMessage));
        try {
            logger.info("==== \t routeKey={}", GlobalCommunicationInfo.PREPOSITION_EVENT_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(prePositionEventMessage).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE, PrePositionEventMessage.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.PREPOSITION_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.REPORTING_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.PREPOSITION_EVENT_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 部委前置向接报发送事件信息失败 原因: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 部委前置向接报发送事件信息成功 ========");
            return isSuccess;
        }
    }
}
