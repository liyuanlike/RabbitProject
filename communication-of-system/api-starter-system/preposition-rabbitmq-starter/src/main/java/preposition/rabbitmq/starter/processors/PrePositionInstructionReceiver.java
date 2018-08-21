package preposition.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.PrePositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @apiNote 为部委前置系统封装接收 指挥系统发送命令的抽象父类
 */
@Component
public abstract class PrePositionInstructionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(PrePositionInstructionReceiver.class);

    @RabbitListener(queues = "#{acceptInstructionQueue.name}")
    @RabbitHandler
    private void acceptReportingCoordination(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 部委前置系统收到 指挥系统发来的指令：{}", body);
        logger.info("==== headers= {}",headers);
        if (!headers.keySet().containsAll(MessageBusinessHeaderKey.BUSINESS_KEYS)){
            logger.error("===== 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        String objectType = (String) headers.get(MessageBusinessHeaderKey.OBJECT_TYPE);
        if (!objectType.equals(PrePositionInstruction.class.getSimpleName())){
            logger.error("====对象类型有误，请核查后重新发送！！！");
        }
        PrePositionInstruction prePositionInstruction = JSON.parseObject(body, new TypeReference<PrePositionInstruction>() {
        });
        logger.info("====开始处理事件中....");
        resolveInstruction(prePositionInstruction);
    }

    /**
     * @apiNote 部委前置系统需要根据业务逻辑 实现此抽象方法 对指令进行处理
     * @param prePositionInstruction
     */
    public abstract void resolveInstruction(PrePositionInstruction prePositionInstruction);
}
