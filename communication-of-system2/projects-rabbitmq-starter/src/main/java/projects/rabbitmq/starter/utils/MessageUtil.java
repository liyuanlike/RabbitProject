package projects.rabbitmq.starter.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import projects.rabbitmq.starter.domain.ProjectsHeaders;
import projects.rabbitmq.starter.domain.ProjectsMessageVO;

import java.util.UUID;

public class MessageUtil {

    /**
     * @apiNote 根据业务封装处理消息的工具类
     * @param projectsMessageVO
     * @return
     */
    public static Message generateMessage(ProjectsMessageVO projectsMessageVO){
        Message message = MessageBuilder.withBody(projectsMessageVO.getJsonString().getBytes())
                .setContentType(projectsMessageVO.getContentType())
                .setContentEncoding("utf-8")
                .setHeader(ProjectsHeaders.CONTENT_TYPE,projectsMessageVO.getContentType())
                .setHeader(ProjectsHeaders.MESSAGE_TYPE,projectsMessageVO.getMessageType())
                .setHeader(ProjectsHeaders.OBJECT_TYPE,projectsMessageVO.getObjectType())
                .setHeader(ProjectsHeaders.SOURCE,projectsMessageVO.getSource())
                .setHeader(ProjectsHeaders.DESTINATION,projectsMessageVO.getDestination())
                .setMessageId(UUID.randomUUID().toString().replace("-",""))
                .build();
        return message;
    }
}
