package projects.rabbitmq.starter.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import projects.rabbitmq.starter.config.ProjectsGlobalInfo;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.domain.ProjectsMessageTypes;
import projects.rabbitmq.starter.domain.ProjectsMessageVO;
import projects.rabbitmq.starter.utils.MessageUtil;

/**
 * @apiNote 为部委前置系统封装 事件发送类
 */
public class PrePositionSender {
    private static final Logger logger = LoggerFactory.getLogger(PrePositionSender.class);

    private AmqpTemplate rabbitTemplate;
    private String id;

    public PrePositionSender(AmqpTemplate rabbitTemplate,String id) {
        this.rabbitTemplate = rabbitTemplate;
        this.id = id;
    }

    /**
     * @apiNote 部委前置系统向 接报系统发送事件的方法
     * @param jsonPrePositionEvent 部委前置 发送事件的 json字符串
     * @param objectClass 可以转化的对象类型
     * @return 返回发送成功还是失败
     */
    public Boolean sendPrePositionEvent(String jsonPrePositionEvent, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonPrePositionEvent == null || objectClass == null){
            logger.error("==== param not allowed null");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.PREPOSITION_FLAG,null).split("-")[0];
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.PREPOSITION_FLAG, ProjectsFlags.REPORTING_FLAG, objectType, ProjectsMessageTypes.PREPOSITION_EVENT_TYPE, jsonPrePositionEvent,id);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("部委前置发送事件{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 部委前置系统向 指挥系统发送执行状况 的方法
     * @param jsonPrePositionExecution 部委前置 发送执行状况的 json字符串
     * @param objectClass 可以转化的对象类型
     * @return 返回发送成功还是失败
     */
    public Boolean sendPrePositionExecution(String jsonPrePositionExecution, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonPrePositionExecution == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.PREPOSITION_FLAG,null).split("-")[1];
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.PREPOSITION_FLAG, ProjectsFlags.DIRECT_FLAG, objectType, ProjectsMessageTypes.PREPOSITION_EXECUTION_TYPE, jsonPrePositionExecution,id);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("部委前置发送执行状况{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
