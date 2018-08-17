package com.liuxun.sub.api;

import com.liuxun.sub.domain.GlobalInfo;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ResultProperties.class)
public class StaterRabbitConfig {

    @Autowired
    private ResultProperties resultProperties;

    /**
     * @apiNote 定义指令的主题交换器
     * @return
     */
    @Bean
    public TopicExchange topicInstructExchange(){
        return new TopicExchange(GlobalInfo.INSTRUCT_CENTER_TOPIC);
    }

    /**
     * @apiNote 定义转发指令处理结果的交换器
     * @return
     */
    @Bean
    public TopicExchange topicResultExchange(){
        return new TopicExchange(GlobalInfo.RESULT_TOPIC);
    }

    /**
     * @apiNote 定义自动删除匿名队列 用于接收处理结果
     * @return
     */
    @Bean
    public Queue acceptResultQueue(){
        return new AnonymousQueue();
    }


    /**
     *
     * @param topicResultExchange 转发指令处理结果的转换器
     * @param acceptResultQueue  将路由键为A的匿名队列绑定到 结果交换器上
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "instruct.result",value = "enabled",havingValue = "true")
    public Binding bindingResultExchange(TopicExchange topicResultExchange,Queue acceptResultQueue){
        return BindingBuilder.bind(acceptResultQueue).to(topicResultExchange).with(resultProperties.getRouteKey());
    }


    /**
     * @apiNote 消息中间件的消息转换器
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
