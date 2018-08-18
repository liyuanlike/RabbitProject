package com.liuxun.datasource.core.config;

import com.liuxun.datasource.core.property.ResolveInstructPropertity;
import com.liuxun.datasource.processors.InstructReceiver;
import com.liuxun.datasource.processors.ResultSender;
import com.liuxun.datasource.service.ProcessInstructService;
import com.liuxun.datasource.service.impl.ProcessInstructServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ResolveInstructPropertity.class)
@ConditionalOnProperty(prefix = "instruct.resolve",value = "enabled",havingValue = "true")
public class ServiceConfig {
    @Autowired
    private ResolveInstructPropertity propertity;

    @Bean
    public AmqpTemplate rabbitTemplate(){
        return new RabbitTemplate();
    }

    @Bean
    public ProcessInstructService processInstructService(){
        return new ProcessInstructServiceImpl();
    }

    @Bean
    public ResultSender resultSender(AmqpTemplate rabbitTemplate){
        return new ResultSender(rabbitTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstructReceiver instructReceiver(ProcessInstructService processInstructService, ResultSender resultSender){
        return  new InstructReceiver(processInstructService,resultSender,propertity.getRouteKey());
    }
}
