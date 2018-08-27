package projects.rabbitmq.starter.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.domain.ProjectsProperties;
import projects.rabbitmq.starter.processors.*;

/**
 * @apiNote 封装与业务相关的配置
 * @author liuxun
 */

@Configuration
@EnableConfigurationProperties(ProjectsProperties.class)
@ConditionalOnProperty(prefix = "projects.system",value = "enabled", havingValue = "true")
public class ProjectsBusinessConfig {

    @Autowired
    private ProjectsProperties projectsProperties;

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.WECHAT_FLAG)
    public WeChatSender weChatSender(AmqpTemplate rabbitTemplate){
        return new WeChatSender(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.REPORTING_FLAG)
    public ReportingSender reportingSender(AmqpTemplate rabbitTemplate){
        return  new ReportingSender(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.DIRECT_FLAG)
    public DirectSender directSender(AmqpTemplate rabbitTemplate){
        return new DirectSender(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.PREPOSITION_FLAG)
    public PrePositionSender prePositionSender(AmqpTemplate rabbitTemplate){
        final String id = projectsProperties.getId();
        return new PrePositionSender(rabbitTemplate,id);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.GRIDMAN_FLAG)
    public GridManSender gridManSender(AmqpTemplate rabbitTemplate){
        return new GridManSender(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.PLAN_FLAG)
    public PlanSender planSender(AmqpTemplate rabbitTemplate){
        return new PlanSender(rabbitTemplate);
    }
}
