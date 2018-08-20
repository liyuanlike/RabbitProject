package rabbit.sender.test;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopic {
    //创建队列
    @Bean
    public Queue queueMessage() {
        return new Queue("Test_AAA");
    }

    @Bean
    public Queue queueMessage2() {
        return new Queue("Test_BBB");
    }

    //创建交换器
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("xxxx_exchange");
    }
   //对列绑定并关联到ROUTINGKEY
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("#");
    }

    //对列绑定并关联到ROUTINGKEY
    @Bean
    Binding bindingExchangeMessage2(Queue queueMessage2, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage2).to(exchange).with("#");
    }
}
