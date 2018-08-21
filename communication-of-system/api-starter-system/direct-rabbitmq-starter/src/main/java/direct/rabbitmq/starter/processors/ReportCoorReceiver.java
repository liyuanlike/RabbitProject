package direct.rabbitmq.starter.processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.global.component.domain.Coordination;
import com.global.component.domain.MessageBusinessHeaderKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

/**
 * @apiNote 为指挥系统封装接收处理协调事件的父类
 */
public abstract class ReportCoorReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ReportCoorReceiver.class);

    @RabbitListener(queues = "#{acceptCoordinationQueue.name}")
    @RabbitHandler
    private void acceptReportingCoordination(Message message){
        String body = new String(message.getBody());
        final Map<String, Object> headers = message.getMessageProperties().getHeaders();
        logger.info("==== 指挥系统收到协调事件：{}", body);
        logger.info("==== headers= {}",headers);
        if (!headers.keySet().containsAll(MessageBusinessHeaderKey.BUSINESS_KEYS)){
            logger.error("===== 头信息不完全 请核查发送时的头信息 ===========");
            return;
        }

        String objectType = (String) headers.get(MessageBusinessHeaderKey.OBJECT_TYPE);
        if (!objectType.equals(Coordination.class.getSimpleName())){
            logger.error("====对象类型有误，请核查后重新发送！！！");
        }
        Coordination coordination = JSON.parseObject(body, new TypeReference<Coordination>() {
        });
        logger.info("====开始处理协调事件中....");
        resolveCoordination(coordination);
    }

    /**
     * @apiNote 指挥系统需要实现的抽象方法，根据具体的业务逻辑对协调事件进行处理
     * @param coordination
     */
    public abstract void resolveCoordination(Coordination coordination);
}
