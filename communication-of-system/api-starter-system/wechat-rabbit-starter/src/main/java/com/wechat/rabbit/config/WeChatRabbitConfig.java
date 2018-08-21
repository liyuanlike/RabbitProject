package com.wechat.rabbit.config;

import com.global.component.config.GlobalCommunicationInfo;
import com.wechat.rabbit.processors.WechatEventSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatRabbitConfig {

    /**
     * @apiNote 消息中间件的消息转换器
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * @apiNote 必须定义 用于注入senderService 否则打包成为starter会运行报错
     * @return
     */
    @Bean
    public AmqpTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * @apiNote 定义统一的 系统对接的Topic主题交换机
     * @return
     */
    @Bean
    public TopicExchange globalExchange(){
        return new TopicExchange(GlobalCommunicationInfo.COMMUNICATION_TOPIC);
    }

    /**
     * @apiNote 为公众号系统封装的发送 建议事件的组件类
     * @param rabbitTemplate 发送信息的模板类  自带的
     * @return
     */
    @Bean
    public WechatEventSender wechatEventSender(AmqpTemplate rabbitTemplate){
        return new WechatEventSender(rabbitTemplate);
    }

}
