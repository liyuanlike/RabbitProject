package batch.update.starter.config;

import batch.update.starter.domain.SystemProperties;
import batch.update.starter.processors.BatchSender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(BatchSender.class)
@EnableConfigurationProperties(SystemProperties.class)
@ConditionalOnProperty(prefix = "batch.system", value = "enabled", havingValue = "true")
public class BatchRabbitConfig {

    @Autowired
    private SystemProperties systemProperties;

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
    public TopicExchange batchGlobalExchange(){
        return new TopicExchange(BatchUpdateGlobalInfo.BATCH_UPDATE_TOPIC);
    }



    /**
     * @apiNote 定义发送指令或数据的Queue
     */
    @Bean
    public Queue acceptDataORInstruction(){
        String queueName = BatchUpdateGlobalInfo.getQueueName(systemProperties.getFlag());
        if (queueName == null){
            throw new RuntimeException("请检查全局配置的  batch.system.flag属性是否合法");
        }
        return new Queue(queueName);
    }

    /**
     * @apiNote 将队列与全局的交换机相绑定
     * @param batchGlobalExchange    全局的交换机
     * @param acceptDataORInstruction   接收协调事件的队列
     * @return
     */
    @Bean
    public Binding bindingCoordinationExchange(TopicExchange batchGlobalExchange, Queue acceptDataORInstruction){
        final String binding = BatchUpdateGlobalInfo.getBinding(systemProperties.getFlag());
        if (binding == null){
            throw new RuntimeException("请检查全局配置的  batch.system.flag属性是否合法");
        }
        return BindingBuilder.bind(acceptDataORInstruction).to(batchGlobalExchange).with(binding);
    }

    @Bean
    public BatchSender batchSender(AmqpTemplate rabbitTemplate){
        final String selfFlag = systemProperties.getFlag();
        return new BatchSender(rabbitTemplate,selfFlag);
    }

}
