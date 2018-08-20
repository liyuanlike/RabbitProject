package rabbit.sender.test;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class TopicReceiver2 {
    @RabbitListener(queues = {"#{queueMessage2.name}"})
    @RabbitHandler
    public void process(String message) throws Exception{
        Thread.sleep(3000L);
        System.out.println("Topic Receiver2  : " + message);
    }
}
