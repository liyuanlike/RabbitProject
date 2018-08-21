package reporting.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.global.component.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * @apiNote 为接报系统封装事件接收的组件
 */
public abstract class ReportEventReceiver {

    private static final Logger logger = LoggerFactory.getLogger(ReportEventReceiver.class);

    @RabbitListener(queues = "#{acceptEventQueue.name}")
    @RabbitHandler
    private void accepEventMesage(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("====接报系统接收事件：{}", body);
        logger.info("====headers= {}",headers);
        if (!headers.keySet().containsAll(MessageBusinessHeaderKey.BUSINESS_KEYS)){
            logger.error("=====头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        String objectType = (String) headers.get(MessageBusinessHeaderKey.OBJECT_TYPE);
        if (objectType.equals(PrePositionEventMessage.class.getSimpleName())){
            logger.info("====开始处理部委前置类型的事件......");
            PrePositionEventMessage prePositionEventMessage = JSON.parseObject(body, new TypeReference<PrePositionEventMessage>() {
            });
            resolvePrepositionEvent(prePositionEventMessage);
        }else if (objectType.equals(WeChatEventMessage.class.getSimpleName())){
            logger.info("====开始处理公众号类型的事件......");
             WeChatEventMessage weChatEventMessage = JSON.parseObject(body, new TypeReference<WeChatEventMessage>() {
            });
            resolveWeChatEvent(weChatEventMessage);
        }else if (objectType.equals(GridManEventMessage.class.getSimpleName())){
            logger.info("====开始处理网格员类型的事件......");
            GridManEventMessage gridManEventMessage = JSON.parseObject(body, new TypeReference<GridManEventMessage>() {
            });
            resolveGridManEvent(gridManEventMessage);
        }else {
            logger.error("====消息体对象类型 {} 不合法，无法处理",objectType);
            return;
        }
    }

    /**
     * @apiNote 接报系统对接收到的部委前置事件进行处理
     * @param prePositionEventMessage
     */
    public abstract void resolvePrepositionEvent(PrePositionEventMessage prePositionEventMessage);

    /**
     * @apiNote 接报系统对接收到的公众号事件进行处理
     * @param weChatEventMessage
     */
    public abstract void resolveWeChatEvent(WeChatEventMessage weChatEventMessage);

    /**
     * @apiNote 接报系统对接收到的网格员信息进行处理
     * @param gridManEventMessage
     */
    public abstract void resolveGridManEvent(GridManEventMessage gridManEventMessage);

}
