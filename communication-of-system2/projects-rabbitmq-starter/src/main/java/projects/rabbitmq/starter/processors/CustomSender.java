package projects.rabbitmq.starter.processors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import projects.rabbitmq.starter.config.ProjectsGlobalInfo;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.domain.ProjectsHeaders;

public class CustomSender {
    private static final Logger logger = LoggerFactory.getLogger(CustomSender.class);

    private AmqpTemplate rabbitTemplate;
    private String myFlag;
    private String myId;

    public CustomSender(AmqpTemplate rabbitTemplate,String myFlag,String myId) {
        this.rabbitTemplate = rabbitTemplate;
        this.myFlag = myFlag;
        this.myId = myId;
    }

    /**
     * @apiNote  发送消息至所有的部委前置
     * @param jsonObject 发送的json字符串
     * @param objectClass json可以转化对象的Class
     * @return 返回发送成功还是失败
     */
    public Boolean sendMessageToAllPrePosition(String  jsonObject,Class<?> objectClass){
        Boolean isSuccess = true;
        MessageBuilderSupport<Message> builderSupport = MessageBuilder.withBody(jsonObject.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setHeader(ProjectsHeaders.SOURCE, myFlag)
                .setHeader(ProjectsFlags.PREPOSITION_FLAG, ProjectsFlags.PREPOSITION_FLAG)
                .setHeader(ProjectsHeaders.OBJECT_TYPE, objectClass.getSimpleName());
        if (this.myId != null){
            builderSupport.setHeader(ProjectsHeaders.SOURCE_ID,this.myId);
        }
        Message message = builderSupport.build();
        try{
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.HEADERS_TOPIC,"",message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
            logger.error("==== 自定义消息发送异常--{}",e.getMessage());
        }finally {
            logger.info("==== 自定义消息发送 {}", isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 发送消息至指定的部委前置系统
     * @param jsonObject 消息的json字符串
     * @param objectClass 可以转化的对象的Class
     * @param prepositionIds  要发送的部委前置的id属性
     * @return 发送成功/失败
     */
    public Boolean sendMessageToApointedPrePosition(String jsonObject,Class<?> objectClass,String ... prepositionIds){
        Boolean isSuccess = true;
        MessageBuilderSupport<Message> builderSupport = MessageBuilder.withBody(jsonObject.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setHeader(ProjectsHeaders.SOURCE, myFlag)
                .setHeader(ProjectsHeaders.OBJECT_TYPE, objectClass.getSimpleName());
        for (String prePositionId:prepositionIds){
            builderSupport.setHeader(prePositionId,prePositionId);
        }
        if (this.myId != null){
            builderSupport.setHeader(ProjectsHeaders.SOURCE_ID,this.myId);
        }
        Message message = builderSupport.build();
        try{
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.HEADERS_TOPIC,"",message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
            logger.error("==== 发送给指定部委的自定义消息发送异常--{}",e.getMessage());
        }finally {
            logger.info("==== 发送给指定部委的自定义消息发送 {}", isSuccess? "成功":"失败");
            return isSuccess;
        }
    }

    /**
     * @apiNote 向非部委前置的其它系统发送自定义消息
     * @param jsonObject 自定义消息的json字符串
     * @param objectClass 自定义消息可以转化的对象Class
     * @param flags 向哪些系统发送消息
     * @return
     */
    public Boolean sendMessageToAppointedSystem(String jsonObject,Class<?> objectClass,String ... flags){
        Boolean isSuccess = true;
        MessageBuilderSupport<Message> builderSupport = MessageBuilder.withBody(jsonObject.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setHeader(ProjectsHeaders.SOURCE, myFlag)
                .setHeader(ProjectsHeaders.OBJECT_TYPE, objectClass.getSimpleName());
        for (String flag:flags){
            if(!flag.equals(ProjectsFlags.PREPOSITION_FLAG)){
                builderSupport.setHeader(flag,flag);
            }
        }
        if (this.myId != null){
            builderSupport.setHeader(ProjectsHeaders.SOURCE_ID,this.myId);
        }
        Message message = builderSupport.build();
        try{
            rabbitTemplate.convertAndSend(ProjectsGlobalInfo.HEADERS_TOPIC,"",message);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
            logger.error("==== 发送给其它(非部委前置)系统的自定义消息发送异常--{}",e.getMessage());
        }finally {
            logger.info("==== 发送给其它(非部委前置)系统的自定义消息发送 {}", isSuccess? "成功":"失败");
            return isSuccess;
        }
    }
}
