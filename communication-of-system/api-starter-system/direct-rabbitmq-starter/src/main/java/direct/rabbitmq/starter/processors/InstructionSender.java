package direct.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.global.component.config.GlobalCommunicationInfo;
import com.global.component.domain.Coordination;
import com.global.component.domain.GridManInstrction;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.PrePositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

/**
 * @apiNote 为指挥系统封装向 前置以及网格员系统 发送指令的服务类
 * @author liuxun
 */
public class InstructionSender {
    private static final Logger logger = LoggerFactory.getLogger(InstructionSender.class);
    private AmqpTemplate rabbitTemplate;

    public InstructionSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 向部委前置系统发送指令方法
     * @param prePositionInstruction  部委前置系统接收的指令类型
     * @return
     */
    public Boolean sendInstructionToPrePosition(PrePositionInstruction prePositionInstruction){
        Boolean  isSuccess = true;
        logger.info("==== 指挥系统发送指令到部委前置.....\t{}", JSON.toJSONString(prePositionInstruction));
        try {
            logger.info("==== \t routeKey={}", GlobalCommunicationInfo.DIRECT_TO_PREPOSITION_INSTRUCTION_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(prePositionInstruction).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE, PrePositionInstruction.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.DIRECT_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.PREPOSITION_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.DIRECT_TO_PREPOSITION_INSTRUCTION_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 指挥向部委前置发送指令失败 原因: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 指挥向部委前置发送指令成功 ========");
            return isSuccess;
        }
    }

    public Boolean sendInstructionToGridMan(GridManInstrction gridManInstrction){
        Boolean  isSuccess = true;
        logger.info("==== 指挥系统发送指令到网格员.....\t{}", JSON.toJSONString(gridManInstrction));
        try {
            logger.info("==== \t routeKey={}", GlobalCommunicationInfo.DIRECT_TO_GRIDMAN_INSTRUCTION_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(gridManInstrction).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE, GridManInstrction.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.DIRECT_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.GRIDMAN_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.DIRECT_TO_GRIDMAN_INSTRUCTION_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 指挥向网格员发送指令失败 原因: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 指挥向网格员发送指令成功 ========");
            return isSuccess;
        }
    }
}
