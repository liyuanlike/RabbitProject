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
 * @apiNote 网格员的接收抽象类
 */
public abstract class AbstractGridManReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractGridManReceiver.class);

    @RabbitListener(queues = "#{gridManAcceptInstructionQueue.name}")
    @RabbitHandler
    private void receiveGridManInstruction(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        final String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        logger.info("==== {} 系统接收到消息：{}",headers.get(ProjectsHeaders.DESTINATION), body);
        logger.info("==== headers= {}",headers);
        logger.info("==== routeKey= {}",routingKey);
        if (!headers.keySet().containsAll(ProjectsHeaders.BUSINESS_KEYS)){
            logger.error("===== 指令Message 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        final String objectType = (String) headers.get(ProjectsHeaders.OBJECT_TYPE);
        final String source = (String) headers.get(ProjectsHeaders.SOURCE);
        final String messageType = (String) headers.get(ProjectsHeaders.MESSAGE_TYPE);
        if (messageType.equals(ProjectsMessageTypes.DIRECT_INSTRUCTION_TYPE)){
            resolveDirectInstruction(body,objectType,source);
        }else {
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }

    /**
     * @apiNote 为网格员系统封装 处理指挥发来指令的 方法回调
     * @param jsonInstruction 指令的json字符串
     * @param objectType  可以转化的对象类型
     * @param source  从哪个系统发过来的
     */
    public abstract void resolveDirectInstruction(String jsonInstruction, String objectType, String source);

}
