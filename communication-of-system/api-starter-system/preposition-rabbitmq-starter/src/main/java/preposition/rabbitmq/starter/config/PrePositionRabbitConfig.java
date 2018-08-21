package preposition.rabbitmq.starter.config;

import com.global.component.config.GlobalCommunicationInfo;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import preposition.rabbitmq.starter.processors.PrePositionExecutionSender;

/**
 * @apiNote 为前置系统封装的 与消息中间件交互的配置类
 */
@Configuration
public class PrePositionRabbitConfig {
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
     * @apiNote 为部委前置系统定义接收指令的队列
     * @return
     */
    @Bean
    public Queue acceptInstructionQueue(){
        return new Queue(GlobalCommunicationInfo.PREPOSITION_ACCEPT_INSTRUCTION_QUEUE);
    }

    /**
     * @apiNote 将队列与交换机绑定
     * @param globalExchange    全局的交换机
     * @param acceptInstructionQueue   接收指令的队列
     * @return
     */
    @Bean
    public Binding bindingInstructionExchange(TopicExchange globalExchange, Queue acceptInstructionQueue){
        return BindingBuilder.bind(acceptInstructionQueue).to(globalExchange).with(GlobalCommunicationInfo.PREPOSITION_ACCEPT_INSTRUCTION_BINDING);
    }


    @Bean
    public PrePositionExecutionSender prePositionExecutionSender(AmqpTemplate rabbitTemplate){
        return new PrePositionExecutionSender(rabbitTemplate);
    }
}
