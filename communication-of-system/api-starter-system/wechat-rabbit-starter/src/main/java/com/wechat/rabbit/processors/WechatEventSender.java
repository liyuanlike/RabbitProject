package com.wechat.rabbit.processors;


import com.alibaba.fastjson.JSON;
import com.global.component.config.GlobalCommunicationInfo;
import com.global.component.domain.MessageBusinessHeaderKey;
import com.global.component.domain.WeChatEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WechatEventSender {
    private static final Logger logger = LoggerFactory.getLogger(WechatEventSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

//    public WechatEventSender(AmqpTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }

    public Boolean sendWeChatEvent(WeChatEventMessage weChatEventMessage){
        Boolean isSuccess = true;
        try {
            logger.info("==== 公众号系统开始发送事件信息\t routeKey={}",GlobalCommunicationInfo.WECHAT_EVENT_ROUTEKEY);
            Message message = MessageBuilder.withBody(JSON.toJSONString(weChatEventMessage).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setHeader(MessageBusinessHeaderKey.OBJECT_TYPE,WeChatEventMessage.class.getSimpleName())
                    .setHeader(MessageBusinessHeaderKey.CONTENT_TYPE,MessageProperties.CONTENT_TYPE_JSON)
                    .setHeader(MessageBusinessHeaderKey.SOURCE,GlobalCommunicationInfo.WECHAT_MODE)
                    .setHeader(MessageBusinessHeaderKey.DESTINATION,GlobalCommunicationInfo.REPORTING_MODE)
                    .setMessageId(UUID.randomUUID().toString().replace("-",""))
                    .build();
            this.rabbitTemplate.convertAndSend(GlobalCommunicationInfo.COMMUNICATION_TOPIC,GlobalCommunicationInfo.WECHAT_EVENT_ROUTEKEY,message);
        }catch (Exception e){
            logger.info("==== 发送失败 失败信息: {}",e.getMessage());
            e.printStackTrace();
            isSuccess = false;
        }finally {
            logger.info("==== 发送成功 ====");
            return isSuccess;
        }
    }

}
