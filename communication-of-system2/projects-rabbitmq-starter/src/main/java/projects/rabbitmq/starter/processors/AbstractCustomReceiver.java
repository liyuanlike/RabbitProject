package projects.rabbitmq.starter.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import projects.rabbitmq.starter.domain.ProjectsHeaders;

import java.util.Map;

public abstract class AbstractCustomReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCustomReceiver.class);

    @RabbitListener(queues = "#{headerQueue.name}")
    @RabbitHandler
    private void receive(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 当前系统接收到消息：{}", body);
        logger.info("==== headers= {}",headers);

        final String objectType = (String) headers.get(ProjectsHeaders.OBJECT_TYPE);
        final String source = (String) headers.get(ProjectsHeaders.SOURCE);
        if (headers.keySet().contains(ProjectsHeaders.SOURCE_ID)){
            String sourceId = (String) headers.get(ProjectsHeaders.SOURCE_ID);
            resolvePrePositionCustomMessage(body,objectType,sourceId);
        }else {
            resolveOtherSystemCustomMessage(body,objectType,source);
        }
    }

    /**
     * @apiNote 接收部委前置的自定义消息
     * @param jsonMessage 消息的json字符串
     * @param objectType 消息可以转化的对象类型
     * @param prePositionId 对应部委前置的id
     */
    public abstract void resolvePrePositionCustomMessage(String jsonMessage,String objectType,String prePositionId);

    /**
     * @apiNote 接收非部委前置系统发来的自定义消息
     * @param jsonMessage 消息的json字符串
     * @param objectType  可以转化的对象类型
     * @param source      标识是由哪个系统发送来的
     */
    public abstract void resolveOtherSystemCustomMessage(String jsonMessage,String objectType,String source);
}
