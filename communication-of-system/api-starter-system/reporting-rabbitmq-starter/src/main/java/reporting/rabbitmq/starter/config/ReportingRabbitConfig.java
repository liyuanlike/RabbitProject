package reporting.rabbitmq.starter.config;

import com.global.component.config.GlobalCommunicationInfo;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reporting.rabbitmq.starter.processors.ReportCoordinationSender;

@Configuration
public class ReportingRabbitConfig {
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
     * @return
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
     * @apiNote 为接报系统提供接收事件消息的队列
     * @return
     */
    @Bean
    public Queue acceptEventQueue(){
        return new Queue(GlobalCommunicationInfo.REPORTING_ACCEPT_EVENT_QUEUE);
    }

    /**
     * @apiNote 为接报系统封装事件的绑定
     * @param globalExchange    全局的交换机
     * @param acceptEventQueue  接收事件消息的队列
     * @return
     */
    @Bean
    public Binding bindingPrePositionEventExchange(TopicExchange globalExchange,Queue acceptEventQueue){
        return BindingBuilder.bind(acceptEventQueue).to(globalExchange).with(GlobalCommunicationInfo.REPORTING_ACCEPT_EVENT_BINDING);
    }


    /**
     * @apiNote 为接报系统封装的用于向指挥系统发送协调事件的组件
     * @param rabbitTemplate    注入的amqp中的操作模板
     * @return
     */
    @Bean
    public ReportCoordinationSender reportCoordinationSender(AmqpTemplate rabbitTemplate){
        return new ReportCoordinationSender(rabbitTemplate);
    }
}
