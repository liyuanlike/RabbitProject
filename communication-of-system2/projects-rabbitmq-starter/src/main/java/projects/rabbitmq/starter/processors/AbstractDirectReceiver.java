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
 * @apiNote 为指挥系统封装接收所有消息的 抽象类
 */
public abstract class AbstractDirectReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDirectReceiver.class);

    @RabbitListener(queues = "#{acceptCoordinationQueue.name}")
    @RabbitHandler
    private void receiveCoordination(Message message){
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
        if (messageType.equals(ProjectsMessageTypes.COORDINATION_TYPE)){
            resolveCoordination(body,objectType,source);
        }else {
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }

    @RabbitListener(queues = "#{acceptExecutionQueue.name}")
    @RabbitHandler
    private void receiveExecution(Message message){
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
        final String id = (String) headers.get("ID");
        if (messageType.equals(ProjectsMessageTypes.PREPOSITION_EXECUTION_TYPE)){
            resolvePrePositionExecution(body,objectType,source,id);
        }else if (messageType.equals(ProjectsMessageTypes.GRIDMAN_EXECUTION_TYPE)) {
            resolveGridManExecution(body,objectType,source);
        }else{
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }
    @RabbitListener(queues = "#{acceptPlanQueue.name}")
    @RabbitHandler
    private void receivePlan(Message message){
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
        if (messageType.equals(ProjectsMessageTypes.PLAN_TYPE)){
            resolvePlanData(body,objectType,source);
        }else{
            logger.error("==== 非法的消息类型---- {}",messageType);
        }
    }

    /**
     * @apiNote 为指挥系统封装 接收预案的 回调方法
     * @param jsonPlan
     * @param objectType
     * @param source
     */
    public abstract void resolvePlanData(String jsonPlan, String objectType, String source);

    /**
     * @apiNote 为指挥系统封装 处理部委前置系统执行状况的回调
     * @param jsonProPositionExecution 部委前置发送执行状况的 json字符串
     * @param objectType 可以转化的对象类型
     * @param source  来源自哪个系统
     * @param prePositionId 部委前置系统的唯一标识
     */
    public abstract void resolvePrePositionExecution(String jsonProPositionExecution, String objectType, String source,String prePositionId);


    /**
     * @apiNote 为指挥系统封装 处理网格员执行状况的 方法回调
     * @param jsonGridManExecution 网格员发送的执行状况的 json字符串
     * @param objectType 可以转化的对象类型
     * @param source  来源自哪个系统
     */
    public abstract void resolveGridManExecution(String jsonGridManExecution, String objectType, String source);


    /**
     * @apiNote 为指挥系统封装 接收协调事件的回调处理
     * @param jsonCoordination 协调信息的json字符串
     * @param objectType  可以转化的对象类型
     * @param source  从哪个系统发出的
     */
    public abstract void resolveCoordination(String jsonCoordination,String objectType,String source);
}
