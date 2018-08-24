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
public class GridManSender {
    private static final Logger logger = LoggerFactory.getLogger(GridManSender.class);

    private AmqpTemplate rabbitTemplate;

    public GridManSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 网格员系统向 接报系统发送事件的方法
     * @param jsonGridManEvent 网格员系统 发送事件的 json字符串
     * @param objectClass 可以转化的对象类型
     * @return 返回发送成功还是失败
     */
    public Boolean sendGridManEvent(String jsonGridManEvent, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonGridManEvent == null || objectClass == null){
            logger.error("==== param not allowed null");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.GRIDMAN_FLAG).split("-")[0];
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.GRIDMAN_FLAG, ProjectsFlags.REPORTING_FLAG, objectType, ProjectsMessageTypes.GRIDMAN_EVENT_TYPE, jsonGridManEvent);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("网格员发送事件{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 网格员系统向 指挥系统发送执行状况 的方法
     * @param jsonGridManExecution  执行状况的 json字符串
     * @param objectClass 可以转化的对象类型
     * @return 返回发送成功还是失败
     */
    public Boolean sendGridManExecution(String jsonGridManExecution, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonGridManExecution == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.GRIDMAN_FLAG).split("-")[1];
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.GRIDMAN_FLAG, ProjectsFlags.DIRECT_FLAG, objectType, ProjectsMessageTypes.GRIDMAN_EXECUTION_TYPE, jsonGridManExecution);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("网格员发送执行状况{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
