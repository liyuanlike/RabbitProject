package com.liuxun.sub.test;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopic {
    //创建队列
    @Bean
    public Queue queueMessage() {
        return new AnonymousQueue();
    }
    //创建交换器
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("xxxx_exchange");
    }
   //对列绑定并关联到ROUTINGKEY
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("#");
    }

}
