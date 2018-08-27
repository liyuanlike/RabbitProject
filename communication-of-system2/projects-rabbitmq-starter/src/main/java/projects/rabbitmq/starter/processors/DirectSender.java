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
 * @author liuxun
 */
public class DirectSender {
    private static final Logger logger = LoggerFactory.getLogger(DirectSender.class);

    private AmqpTemplate rabbitTemplate;

    public DirectSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Boolean sendInstruction(String jsonInstruction, Class<?> objectClass, Boolean isToPrePosition, String  id){
        Boolean isSuccess = true;
        if (jsonInstruction == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String[] twoRouteKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.DIRECT_FLAG,id).split("-");
        final String routeKey = isToPrePosition? twoRouteKey[0]:twoRouteKey[1];
        final String destination = isToPrePosition ? ProjectsFlags.PREPOSITION_FLAG : ProjectsFlags.GRIDMAN_FLAG;
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.DIRECT_FLAG, destination, objectType, ProjectsMessageTypes.DIRECT_INSTRUCTION_TYPE, jsonInstruction,id);
         Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("发送{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 为指挥系统封装向预案系统发送方案的方法
     * @param jsonProgramme
     * @param objectClass
     * @return
     */
    public Boolean sendProgramme(String jsonProgramme, Class<?> objectClass){
        Boolean isSuccess = true;
        if (jsonProgramme == null || objectClass == null){
            logger.error("==== 参数不能为空");
            return false;
        }

        final String objectType = objectClass.getSimpleName();
        final String routeKey = ProjectsGlobalInfo.getRouteKey(ProjectsFlags.DIRECT_FLAG,null).split("-")[2];
        final ProjectsMessageVO messageVO = new ProjectsMessageVO(ProjectsFlags.DIRECT_FLAG, ProjectsFlags.PLAN_FLAG, objectType, ProjectsMessageTypes.PROGRAMME_TYPE, jsonProgramme);
        final Message message = MessageUtil.generateMessage(messageVO);
        try {
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.PROJECTS_TOPIC,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("指挥发送方案{}",isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 为指挥系统封装 向部委前置发送指令的方法
     * @param jsonInstruction 指令的json字符串
     * @param objectClass  可以转化的对象类型
     * @return
     */
    public Boolean sendInstructionToPrePosition(String jsonInstruction, Class<?> objectClass, String  prePositionId){
        return sendInstruction(jsonInstruction,objectClass,true,prePositionId);
    }

    /**
     * @apiNote 为指挥系统封装 向网格员发送指令的方法
     * @param jsonInstruction 指令的json字符串
     * @param objectClass  可以转化的对象类型
     * @return
     */
    public Boolean sendInstructionToGridMan(String jsonInstruction, Class<?> objectClass){
        return sendInstruction(jsonInstruction,objectClass,false,null);
    }
}
