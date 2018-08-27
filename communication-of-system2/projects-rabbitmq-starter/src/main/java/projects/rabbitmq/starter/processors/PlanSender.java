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
 * @apiNote 为预案系统封装 预案发送类
 */
public class PlanSender {
    private static final Logger logger = LoggerFactory.getLogger(PlanSender.class);

    private AmqpTemplate rabbitTemplate;

    public PlanSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 为预案系统发送预案的方法
     * @param jsonPlan 预案的json字符串
     * @param objectClass 可以转化的对象类型
     * @return 发送成功还是失败
     */
    public Boolean sendPlan(String jsonPlan, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonPlan == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.PLAN_FLAG,null);
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.PLAN_FLAG, ProjectsFlags.DIRECT_FLAG, objectType, ProjectsMessageTypes.PLAN_TYPE, jsonPlan);
        final Message coorMessage = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,coorMessage);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("预案系统发送预案信息 {}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
