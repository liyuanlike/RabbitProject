package com.liuxun.datasource.core.config;

import com.liuxun.datasource.core.domain.GlobalInfo;
import com.liuxun.datasource.core.property.ResolveInstructPropertity;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ResolveInstructPropertity.class)
@ConditionalOnProperty(prefix = "instruct.resolve",value = "enabled",havingValue = "true") // 存在此属性时初始化此配置类
public class RabbitConfig {

    @Autowired
    private ResolveInstructPropertity propertity;

    @Bean
    public AmqpTemplate rabbitTemplate(){
        return new RabbitTemplate();
    }

    /**
     * @apiNote 定义指令的主题交换器
     * @return
     */
    @Bean
    public TopicExchange topicInstructExchange(){
        return new TopicExchange(GlobalInfo.INSTRUCT_CENTER_TOPIC);
    }

    /**
     * @apiNote 定义队列 用于接收指令
     * @return
     */
    @Bean
    public Queue acceptInstructQueue(){
        return new Queue(propertity.getRouteKey()+"_Queue");
    }

    /**
     *
     * @param topicInstructExchange 转发指令的主题交换机
     * @param acceptInstructQueue  接收指令的匿名队列
     * @apiNote 将使用路由键为 INSTRUCT_ROUTE_KEY 的 acceptInstructQueue 队列绑定到主题交换器上
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Binding bindingInstructExchange(TopicExchange topicInstructExchange,Queue acceptInstructQueue){
        return BindingBuilder.bind(acceptInstructQueue).to(topicInstructExchange).with(propertity.getRouteKey()+".RESOLVE");
    }

    /**
     * @apiNote 定义转发指令处理结果的交换器
     * @return
     */
    @Bean
    public TopicExchange topicResultExchange(){
        return new TopicExchange(GlobalInfo.RESULT_TOPIC);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
