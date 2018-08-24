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
 * @apiNote 为公众号系统封装 事件发送类
 */
public class WeChatSender {
    private static final Logger logger = LoggerFactory.getLogger(WeChatSender.class);

    private AmqpTemplate rabbitTemplate;

    public WeChatSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @apiNote 公众号系统向接报系统发送事件的方法
     * @param jsonWechatEvent 公众号发送事件的json字符串
     * @param objectClass 可以转化的对象类型
     * @return  发送事件成功还是失败
     */
    public Boolean sendWeChatEvent(String jsonWechatEvent, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonWechatEvent == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.WECHAT_FLAG);
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.WECHAT_FLAG, ProjectsFlags.REPORTING_FLAG, objectType, ProjectsMessageTypes.WECHAT_EVENT_TYPE, jsonWechatEvent);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("发送公众号事件{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
