package com.liuxun.datasource.core.config;

import com.liuxun.datasource.processors.InstructReceiver;
import com.liuxun.datasource.processors.ResultSender;
import com.liuxun.datasource.service.ProcessInstructService;
import com.liuxun.datasource.service.impl.ProcessInstructServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {


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
    public InstructReceiver instructReceiver(ProcessInstructService processInstructService, ResultSender resultSender){
        return  new InstructReceiver(processInstructService,resultSender);
    }
}
