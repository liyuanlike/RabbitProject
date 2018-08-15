package com.liuxun.sub.api;

import com.liuxun.sub.processors.InstructSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(InstructSenderService.class)
@EnableConfigurationProperties(ResultProperties.class)
public class StarterSenderInstructConfig {

    @Autowired
    private ResultProperties resultProperties;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(){
        return new RabbitTemplate();
    }

    @Bean
    public InstructSender instructSender(AmqpTemplate rabbitTemplate){
        InstructSender instructSender = new InstructSender();
        instructSender.setRabbitTemplate(rabbitTemplate);
        return instructSender;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "instruct.result",value = "enabled",havingValue = "true")
    public InstructSenderService instructSenderService(InstructSender instructSender){
        return new InstructSenderService(instructSender,resultProperties.getRouteKey());
    }
}
