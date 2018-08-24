package projects.rabbitmq.starter.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import projects.rabbitmq.starter.domain.ProjectsHeaders;
import projects.rabbitmq.starter.domain.ProjectsMessageTypes;

import java.util.Map;

/**
 * @apiNote 为接报系统封装 事件的接收类
 */
public abstract class AbstractReportingReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractReportingReceiver.class);

    @RabbitListener(queues = "#{acceptEventQueue.name}")
    @RabbitHandler
    private void receiveEventData(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        final String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        logger.info("==== {} 系统接收到消息：{}",headers.get(ProjectsHeaders.DESTINATION), body);
        logger.info("==== headers= {}",headers);
        logger.info("==== routeKey= {}",routingKey);
        if (!headers.keySet().containsAll(ProjectsHeaders.BUSINESS_KEYS)){
            logger.error("===== 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        final String objectType = (String) headers.get(ProjectsHeaders.OBJECT_TYPE);
        final String source = (String) headers.get(ProjectsHeaders.SOURCE);
        final String messageType = (String) headers.get(ProjectsHeaders.MESSAGE_TYPE);
        if (messageType.equals(ProjectsMessageTypes.WECHAT_EVENT_TYPE)){
            resolveWeChatEvent(body,objectType,source);
        }else if (messageType.equals(ProjectsMessageTypes.PREPOSITION_EVENT_TYPE)) {
            resolvePrepositionEvent(body,objectType,source);
        }else if (messageType.equals(ProjectsMessageTypes.GRIDMAN_EVENT_TYPE)){
            resolveGridManEvent(body,objectType,source);
        }else {
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }

    /**
     * @apiNote 处理从公众号系统接发送的事件
     * @param jsonWeChatEvent 公众号事件的json字符串
     * @param objectType 可以转化的对象类型
     * @param source  从哪个系统发送过来的
     */
    public abstract void resolveWeChatEvent(String jsonWeChatEvent,String objectType,String source);

    /**
     * @apiNote 处理从部委前置发送过来的事件
     * @param jsonPrePositionEvent 部委前置事件的json字符串
     * @param objectType  可以转化的对象类型
     * @param source 从哪个系统发送过来的
     */
    public abstract void resolvePrepositionEvent(String jsonPrePositionEvent,String objectType,String source);

    /**
     * @apiNote 处理从网格员发送过来的事件
     * @param jsonGridManEvent 网格员事件的json字符串
     * @param objectType  可以转化的对象类型
     * @param source 从哪个系统发送而来
     */
    public abstract void resolveGridManEvent(String jsonGridManEvent,String objectType,String source);

}
