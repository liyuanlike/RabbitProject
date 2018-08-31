package projects.rabbitmq.starter.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.domain.ProjectsProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote 封装各个系统与RabbitMQ整合的配置
 */

@Configuration
@EnableConfigurationProperties(ProjectsProperties.class)
@PropertySource(value = {"classpath:default.properties"},ignoreResourceNotFound = true)
@ConditionalOnProperty(prefix = "projects.system",value = "enabled", havingValue = "true")
public class ProjectsRabbitConfig {

    @Autowired
    private ProjectsProperties projectsProperties;


    /**
     * @apiNote 消息中间件的消息转换器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * @apiNote 必须定义 用于注入senderService 否则打包成为starter会运行报错
     *
     */
    @Bean
    @ConditionalOnMissingBean
    public AmqpTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * @apiNote 定义统一的 批量更新Topic主题交换机
     * @return
     */
    @Bean
    public TopicExchange projectsGlobalExchange(){
        return new TopicExchange(ProjectsGlobalInfo.PROJECTS_TOPIC);
    }


    // ================================== 封装接报系统与RabbitMQ整合的配置 ================================
    /**
     * @apiNote  为接报系统封装获取 对应的事件消息的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.REPORTING_FLAG)
    public Queue acceptEventQueue(){
        final String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.REPORTING_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    /**
     * @apiNote 将接收事件的队列与交换机绑定
     * @param projectsGlobalExchange
     * @param acceptEventQueue
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"acceptEventQueue"})
    public Binding bindingEventToExchange(TopicExchange projectsGlobalExchange, Queue acceptEventQueue){
        final String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(acceptEventQueue).to(projectsGlobalExchange).with(binding);
    }

    // ================================== 指挥系统与RabbitMQ整合的配置 ================================

    /**
     * @apiNote 为指挥系统封装接收协调事件的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.DIRECT_FLAG)
    public Queue acceptCoordinationQueue(){
         String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.DIRECT_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName.split("-")[0]);
    }
    /**
     * @apiNote 将接收协调事件的队列与交换机绑定
     * @param projectsGlobalExchange 交换机
     * @param acceptCoordinationQueue 接收协调事件的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"acceptCoordinationQueue"})
    public Binding bindingCoordinationToExchange(TopicExchange projectsGlobalExchange, Queue acceptCoordinationQueue){
        final String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(acceptCoordinationQueue).to(projectsGlobalExchange).with(binding.split("-")[0]);
    }

    /**
     * @apiNote 为指挥系统封装接收 执行状况 的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.DIRECT_FLAG)
    public Queue acceptExecutionQueue(){
        String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.DIRECT_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName.split("-")[1]);
    }

    /**
     * @apiNote 将接收执行状况的队列与交换机绑定
     * @param projectsGlobalExchange 全局交换机
     * @param acceptExecutionQueue    接收执行状况的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"acceptExecutionQueue"})
    public Binding bindingExecutionToExchange(TopicExchange projectsGlobalExchange, Queue acceptExecutionQueue){
        String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(acceptExecutionQueue).to(projectsGlobalExchange).with(binding.split("-")[1]);
    }


    /**
     * @apiNote 为指挥系统封装接收 预案系统发来预案 的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.DIRECT_FLAG)
    public Queue acceptPlanQueue(){
        String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.DIRECT_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName.split("-")[2]);
    }

    /**
     * @apiNote 将接收预案的队列与交换机绑定
     * @param projectsGlobalExchange 全局交换机
     * @param acceptPlanQueue    接收预案的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"acceptPlanQueue"})
    public Binding bindingAcceptPlanToExchange(TopicExchange projectsGlobalExchange, Queue acceptPlanQueue){
        String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(acceptPlanQueue).to(projectsGlobalExchange).with(binding.split("-")[2]);
    }

    // ================================== 部委前置系统与RabbitMQ整合的配置 ================================

    /**
     * @apiNote 为部委前置系统封装接收 指挥指令的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.PREPOSITION_FLAG)
    public Queue prePositionAcceptInstructionQueue(){
        final String id = projectsProperties.getId();
        if(id == null){
            throw new RuntimeException("请检查 projects.system.id属性值是否合法 ");
        }
        String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.PREPOSITION_FLAG,id);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    /**
     * @apiNote 将部委前置接收命令的队列与交换机绑定
     * @param projectsGlobalExchange 全局的交换机
     * @param prePositionAcceptInstructionQueue 部委前置接收指令的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"prePositionAcceptInstructionQueue"})
    public Binding bindingPInstructionToExchange(TopicExchange projectsGlobalExchange, Queue prePositionAcceptInstructionQueue){
        final String id = projectsProperties.getId();
        if(id == null){
            throw new RuntimeException("请检查 projects.system.id属性值是否合法 ");
        }
        String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),id);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(prePositionAcceptInstructionQueue).to(projectsGlobalExchange).with(binding);
    }
    // ================================== 网格员系统与RabbitMQ整合的配置 ================================

    /**
     * @apiNote 为网格员系统封装接收 指挥指令的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.GRIDMAN_FLAG)
    public Queue gridManAcceptInstructionQueue(){
        String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.GRIDMAN_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    /**
     * @apiNote 将网格员接收命令的队列与交换机绑定
     * @param projectsGlobalExchange 全局的交换机
     * @param gridManAcceptInstructionQueue 网格员接收指令的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"gridManAcceptInstructionQueue"})
    public Binding bindingGInstructionToExchange(TopicExchange projectsGlobalExchange, Queue gridManAcceptInstructionQueue){
        String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(gridManAcceptInstructionQueue).to(projectsGlobalExchange).with(binding);
    }

    // ================================== 预案系统与RabbitMQ整合的配置 ================================
    /**
     * @apiNote 为预案系统封装接收 指挥发出方案的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = ProjectsFlags.PLAN_FLAG)
    public Queue planAcceptProgrammeQueue(){
        String queueName = ProjectsGlobalInfo.getQueueName(ProjectsFlags.PLAN_FLAG,null);
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    /**
     * @apiNote 将预案系统接收方案的队列与交换机绑定
     * @param projectsGlobalExchange 全局的交换机
     * @param planAcceptProgrammeQueue 预案系统接收方案的队列
     * @return
     */
    @Bean
    @ConditionalOnBean(name = {"planAcceptProgrammeQueue"})
    @ConditionalOnMissingBean
    public Binding bindingPlanAcceptProgrammeToExchange(TopicExchange projectsGlobalExchange, Queue planAcceptProgrammeQueue){
        String binding = ProjectsGlobalInfo.getBinding(projectsProperties.getFlag(),null);
        if (binding == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        return BindingBuilder.bind(planAcceptProgrammeQueue).to(projectsGlobalExchange).with(binding);
    }

    // ================================== 为所有系统自定义的灵活配置 ================================
    @Bean
    public HeadersExchange headersGlobalExchange(){
        return new HeadersExchange(ProjectsGlobalInfo.HEADERS_TOPIC,true,false);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag")
    public Queue headerQueue(){
        if (projectsProperties.getFlag() == null){
            throw new RuntimeException("请检查  projects.system.flag属性是否合法");
        }
        String id = projectsProperties.getId() == null ? "" : projectsProperties.getId();
        String queueName = projectsProperties.getFlag() + "_" + id +"_HEADER_QUEUE";
        return new Queue(queueName.toUpperCase());
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag")
    public Binding bindingQueueToHeadersExchange(HeadersExchange headersGlobalExchange, Queue headerQueue){
        Map<String, Object> headersMap = new HashMap<>();
        if (projectsProperties.getFlag().equals(ProjectsFlags.PREPOSITION_FLAG)){
            if (projectsProperties.getId() == null){
                throw new RuntimeException("部委前置必须配置 projects.system.id");
            }else{
                headersMap.put(projectsProperties.getId(),projectsProperties.getId());
            }
        }
        headersMap.put(projectsProperties.getFlag(),projectsProperties.getFlag());
        return BindingBuilder.bind(headerQueue).to(headersGlobalExchange).whereAny(headersMap).match();
    }
}
