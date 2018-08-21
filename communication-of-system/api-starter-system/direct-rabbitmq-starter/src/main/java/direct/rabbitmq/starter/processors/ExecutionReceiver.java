package direct.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.global.component.domain.GridManExecution;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.PrePositionExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

/**
 * @apiNote 为指挥系统封装处理 前置和网格员 发送的执行状况
 * @author liuxun
 */
public abstract class ExecutionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionReceiver.class);

    @RabbitListener(queues = "#{acceptExecutionQueue.name}")
    @RabbitHandler
    private void acceptExecution(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 指挥系统收到执行状况：{}", body);
        logger.info("==== headers= {}",headers);
        if (!headers.keySet().containsAll(MessageBusinessHeaderKey.BUSINESS_KEYS)){
            logger.error("===== 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        String objectType = (String) headers.get(MessageBusinessHeaderKey.OBJECT_TYPE);
        if (objectType.equals(PrePositionExecution.class.getSimpleName())){
            logger.info("====解析执行状况为部委前置执行状况 =====");
            PrePositionExecution prePositionExecution = JSON.parseObject(body, new TypeReference<PrePositionExecution>() {
            });
            resolvePrePositionExecution(prePositionExecution);
        }else if (objectType.equals(GridManExecution.class.getSimpleName())){
            logger.info("====解析执行状况为网格员执行状况 =====");
            GridManExecution gridManExecution = JSON.parseObject(body, new TypeReference<GridManExecution>() {
            });
            resolveGridManExecution(gridManExecution);
        }else {
            logger.error("====消息对象类型 {} 不合法 ",objectType);
        }
    }

    /**
     * @apiNote 指挥系统 根据具体业务实现此方法 对部委前置系统的指令执行状况进行处理
     * @param prePositionExecution
     */
    public abstract void resolvePrePositionExecution(PrePositionExecution prePositionExecution);

    /**
     * @apiNote 指挥系统 根据具体业务实现此方法 对网格员系统的 指令执行状况进行处理
     * @param gridManExecution
     */
    public abstract void resolveGridManExecution(GridManExecution gridManExecution);
}
