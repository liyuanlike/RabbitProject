package direct.rabbitmq.starter.config;

import com.global.component.config.GlobalCommunicationInfo;
import direct.rabbitmq.starter.processors.InstructionSender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {
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
     *
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
     * @apiNote 为指挥系统定义 接收接报系统发送协调事件的队列
     */
    @Bean
    public Queue acceptCoordinationQueue(){
        return new Queue(GlobalCommunicationInfo.DIRECT_ACCEPT_COORDINATION_QUEUE);
    }

    /**
     * @apiNote 为指挥系统定义 接收网格员以及部委前置系统发送执行状况的队列
     */
    @Bean
    public Queue acceptExecutionQueue(){
        return new Queue(GlobalCommunicationInfo.DIRECT_ACCEPT_EXECUTION_QUEUE);
    }

    /**
     * @apiNote 将接收协调事件的队列与全局的交换机相绑定
     * @param globalExchange    全局的交换机
     * @param acceptCoordinationQueue   接收协调事件的队列
     * @return
     */
    @Bean
    public Binding bindingCoordinationExchange(TopicExchange globalExchange, Queue acceptCoordinationQueue){
        return BindingBuilder.bind(acceptCoordinationQueue).to(globalExchange).with(GlobalCommunicationInfo.DIRECT_ACCEPT_COORDINATION_BINDING);
    }

    /**
     * @apiNote 将接收执行状况的队列与全局的交换机绑定
     * @param globalExchange    交互系统全局的交换机
     * @param acceptExecutionQueue  接收执行状况的队列
     * @return
     */
    @Bean
    public Binding bindingExecutionExchange(TopicExchange globalExchange, Queue acceptExecutionQueue){
        return BindingBuilder.bind(acceptExecutionQueue).to(globalExchange).with(GlobalCommunicationInfo.DIRECT_ACCEPT_EXECUTION_BINDING);
    }

    /**
     * @apiNote 为指挥系统封装 向部委前置以及网格员 发送指令的服务类
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public InstructionSender instructionSender(AmqpTemplate rabbitTemplate){
        return new InstructionSender(rabbitTemplate);
    }
}
