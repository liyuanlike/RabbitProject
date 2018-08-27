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
 * @apiNote 为接报系统封装 事件发送类
 */
public class ReportingSender {
    private static final Logger logger = LoggerFactory.getLogger(ReportingSender.class);

    private AmqpTemplate rabbitTemplate;

    public ReportingSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 为接报系统发送协调事件的方法
     * @param jsonCoordination 协调事件的json字符串
     * @param objectClass 可以转化的对象类型
     * @return 发送成功还是失败
     */
    public Boolean sendCoordinationEvent(String jsonCoordination, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonCoordination == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.REPORTING_FLAG,null);
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.REPORTING_FLAG, ProjectsFlags.DIRECT_FLAG, objectType, ProjectsMessageTypes.COORDINATION_TYPE, jsonCoordination);
        final Message coorMessage = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,coorMessage);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("接报系统发送协调信息 {}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
