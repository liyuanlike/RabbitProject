package gridman.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.global.component.config.GlobalCommunicationInfo;
import com.global.component.domain.GridManExecution;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.PrePositionExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

/**
 * @apiNote 为部委前置系统封装 用于发送指令执行状况的服务类
 * @author liuxun
 */
public class GridManExecutionSender {
    private static final Logger logger = LoggerFactory.getLogger(GridManExecutionSender.class);
    private AmqpTemplate rabbitTemplate;

    public GridManExecutionSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean sendInstructionExecution(GridManExecution gridManExecution){
        Boolean  isSuccess = true;
        logger.info("==== 网格员系统发送执行状况到指挥.....\t{}", JSON.toJSONString(gridManExecution));
        try {
            logger.info("==== \t routeKey={}", GlobalCommunicationInfo.GRIDMAN_TO_DIRECT_EXECUTION_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(gridManExecution).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE, GridManExecution.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.GRIDMAN_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.DIRECT_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.GRIDMAN_TO_DIRECT_EXECUTION_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 网格员向指挥发送执行状况失败 原因: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 网格员向指挥发送执行状况成功 ========");
            return isSuccess;
        }
    }
}
