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
     * @apiNote 定义统一的 监控Topic主题交换机
     * @return
     */
    @Bean
    public TopicExchange monitorGlobalExchange(){
        return new TopicExchange(MonitorGlobalInfo.MONITOR_EXCHANGE);
    }

    /**
     * @apiNote  为接报系统封装获取 对应的事件消息的队列
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
    public Binding bindingGInstructionToExchange(TopicExchange monitorGlobalExchange, Queue acceptHeartQueue){
        return BindingBuilder.bind(acceptHeartQueue).to(monitorGlobalExchange).with("#");
    }

}
