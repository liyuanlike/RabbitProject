package batch.update.starter.processors;

import batch.update.starter.config.BatchUpdateGlobalInfo;
import batch.update.starter.domain.BatchUpdateHeaders;
import batch.update.starter.domain.MessageTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @apiNote 封装工具 用于发送数据或指令
 */

@Component
public class BatchSender {
    private static final Logger logger = LoggerFactory.getLogger(BatchSender.class);

    private AmqpTemplate rabbitTemplate;

    private String selfFlag;

    public BatchSender(AmqpTemplate rabbitTemplate,String selfFlag) {
        this.rabbitTemplate = rabbitTemplate;
        this.selfFlag = selfFlag;
    }

    private Boolean sendData(String jsonString, Class<?> objectClass,String destination,Boolean isDataType){
        Boolean isSuccess = true;
        if (jsonString == null || objectClass == null || destination == null){
            logger.error("==== 参数不能为空");
            return false;
        }
        if (!BatchUpdateGlobalInfo.flags.contains(destination)){
            logger.error("==== destination不合法---{}",destination);
            return false;
        }
        final String objectType = objectClass.getSimpleName();
        Message message = MessageBuilder.withBody(jsonString.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setHeader(BatchUpdateHeaders.SOURCE,this.selfFlag)
                .setHeader(BatchUpdateHeaders.DESTINATION,destination)
                .setHeader(BatchUpdateHeaders.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                .setHeader(BatchUpdateHeaders.OBJECT_TYPE,objectType)
                .setHeader(BatchUpdateHeaders.MESSAGE_TYPE, isDataType? MessageTypes.MESSAGE_TYPE_DATA:MessageTypes.MESSAGE_TYPE_INSTRUCTION)
                .setMessageId(UUID.randomUUID().toString().replace("-",""))
                .build();

        try {
            final String routeKey = BatchUpdateGlobalInfo.getRouteKey(destination);
            if (routeKey == null){
                logger.error("==== 检查destination参数{} 是否合法",routeKey);
                return false;
            }
            this.rabbitTemplate.convertAndSend(BatchUpdateGlobalInfo.BATCH_UPDATE_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 发送{} {} ",isDataType? "数据":"指令",isSuccess? "成功":"失败");
            return isSuccess;
        }

    }

    /**
     * @apiNote 发送指令
     * @param jsonInstruction 指令对象的json字符串
     * @param objectClass   指令对象的Class类型
     * @param destination   发送的目的地(系统标志)
     * @return
     */
    public Boolean sendInstruction(String jsonInstruction, Class<?> objectClass,String destination){
        return sendData(jsonInstruction,objectClass,destination,false);
    }

    /**
     * @apiNote 发送数据
     * @param jsonData 数据对象的json字符串
     * @param objectClass   数据对象的Class类型
     * @param destination   发送的目的地(系统标志)
     * @return
     */
    public Boolean sendData(String jsonData, Class<?> objectClass,String destination){
        return sendData(jsonData,objectClass,destination,true);
    }
}
