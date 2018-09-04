package rabbit.sender.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TopicSender {
    private static final Logger logger = LoggerFactory.getLogger(TopicSender.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        final Date date = new Date();
        this.rabbitTemplate.convertAndSend("xxxx_exchange", "wwwww",date);
    }

}



