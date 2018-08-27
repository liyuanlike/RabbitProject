package projects.rabbitmq.starter.domain;

import org.springframework.amqp.core.MessageProperties;

public class ProjectsMessageVO {
    private String source; // 消息发起方
    private String destination; // 消息的接收方
    private String contentType = MessageProperties.CONTENT_TYPE_JSON; // 消息的格式
    private String objectType; // 消息体可以转化的对象类型
    private String messageType; // 消息的业务类型
    private String jsonString; // 数据
    private String id = null; // 项目标识，目前只是针对 部委前置设计


    public ProjectsMessageVO() {
    }

    public ProjectsMessageVO(String source, String destination, String objectType, String messageType, String jsonString) {
        this.source = source;
        this.destination = destination;
        this.objectType = objectType;
        this.messageType = messageType;
        this.jsonString = jsonString;
    }

    public ProjectsMessageVO(String source, String destination, String objectType, String messageType, String jsonString, String id) {
        this.source = source;
        this.destination = destination;
        this.objectType = objectType;
        this.messageType = messageType;
        this.jsonString = jsonString;
        this.id = id;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
