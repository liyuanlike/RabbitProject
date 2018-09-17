package heartbeat.monitor.starter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"heartbeat.monitor.starter"})
@PropertySource(value = {"classpath:monitor.properties"},ignoreResourceNotFound = true)
@ConditionalOnProperty(prefix = "projects.system",value = "enabled", havingValue = "true")
@EnableScheduling
public class MonitorConfig {
    /**
     * @apiNote 定义心跳的 监控Topic主题交换机
     * @return
     */
    @Bean
    public TopicExchange monitorGlobalExchange(){
        return new TopicExchange(MonitorGlobalInfo.HEARTBEAT_MONITOR_EXCHANGE);
    }

    @Bean
    public TopicExchange monitorBusinessExchange(){
        return new TopicExchange(MonitorGlobalInfo.BUSINESS_MONITOR_TOPIC);
    }

    /**
     * @apiNote  为接监控系统封装获取 心跳消息的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
    public Queue acceptHeartQueue(){
        final String queueName = MonitorGlobalInfo.MONITOR_QUEUE_NAME;
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
    public Binding bindingHeartBeatExchange(TopicExchange monitorGlobalExchange, Queue acceptHeartQueue){
        return BindingBuilder.bind(acceptHeartQueue).to(monitorGlobalExchange).with("#");
    }

    /**
     * @apiNote  为接监控系统封装获取业务监控数据的队列
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
    public Queue acceptBusinessQueue(){
        final String queueName = MonitorGlobalInfo.BUSINESS_MONITOR_QUEUE;
        if (queueName == null){
            throw new RuntimeException("请检查 projects.system.flag属性值是否合法 ");
        }
        return new Queue(queueName);
    }

    @Bean
    @ConditionalOnProperty(prefix = "projects.system",value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
    public Binding bindingGInstructionToExchange(TopicExchange monitorBusinessExchange, Queue acceptBusinessQueue){
        return BindingBuilder.bind(acceptBusinessQueue).to(monitorBusinessExchange).with("#");
    }

}
