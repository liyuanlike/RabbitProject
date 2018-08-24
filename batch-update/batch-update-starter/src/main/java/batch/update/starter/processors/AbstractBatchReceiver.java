package batch.update.starter.processors;

import batch.update.starter.domain.BatchUpdateHeaders;
import batch.update.starter.domain.MessageTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @apiNote 封装回调类 用于接收指令或数据
 */

@Component
public abstract class AbstractBatchReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBatchReceiver.class);

    @RabbitListener(queues = "#{acceptDataORInstruction.name}")
    @RabbitHandler
    private void receiveInstructionORData(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        final String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        logger.info("==== {} 系统接收到消息：{}",headers.get(BatchUpdateHeaders.DESTINATION), body);
        logger.info("==== headers= {}",headers);
        logger.info("==== routeKey= {}",routingKey);
        if (!headers.keySet().containsAll(BatchUpdateHeaders.BUSINESS_KEYS)){
            logger.error("===== 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        final String objectType = (String) headers.get(BatchUpdateHeaders.OBJECT_TYPE);
        final String source = (String) headers.get(BatchUpdateHeaders.SOURCE);
        final String messageType = (String) headers.get(BatchUpdateHeaders.MESSAGE_TYPE);
        if (messageType.equals(MessageTypes.MESSAGE_TYPE_DATA)){
            resolveData(body,objectType,source);
        }else if (messageType.equals(MessageTypes.MESSAGE_TYPE_INSTRUCTION)){
            resolveInstruction(body,objectType,source);
        }else {
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }

    /**
     * @apiNote 处理接收到的数据
     * @param dataContent 收到的数据(json字符串)
     * @param objectType  可以转化的对象类型
     * @param source    是哪个系统发过来的数据
     */
    public abstract void resolveData(String dataContent,String objectType,String source);

    /**
     * @apiNote 处理接收到的指令
     * @param instructionContent 收到的指令内容(json 字符串)
     * @param objectType  可以转化的对象类型
     * @param source    由哪个系统发送过来的指令
     */
    public abstract void resolveInstruction(String instructionContent,String objectType,String source);
}
