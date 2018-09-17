package heartbeat.monitor.starter.config;

import heartbeat.monitor.starter.domain.Metrics;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @apiNote 监控系统的配置信息
 */
public class MonitorGlobalInfo {
    /**
     * @apiNote 监控系统接收 其它各个系统心跳细信息的队列名称
     */
    public static final String MONITOR_QUEUE_NAME = "MONITOR_QUEUE";

    /**
     * @apiNote 监控系统接收 业务监控数据的队列名称
     */
    public static final String BUSINESS_MONITOR_QUEUE = "BUSINESS_MONITOR_QUEUE";

    /**
     * @apiNote 监控系统的 需要配置的Flag标志
     */
    public static final String MONITOR_FLAG = "MONITOR";

    public static final String HEARTBEAT_MONITOR_EXCHANGE = "HEARTBEAT_MONITOR_EXCHANGE";

    public static final String BUSINESS_MONITOR_TOPIC = "BUSINESS_MONITOR_TOPIC";

    /**
     * @apiNote 存放实时的 部委前置各个实例的健康信息
     */
    public static ConcurrentHashMap<String, Metrics> PRE_INFOS = new ConcurrentHashMap<>();

    /**
     * @apiNote 存放非部委前置系统的各个实例的健康信息
     */
    public static ConcurrentHashMap<String, Metrics> NOT_PRE_INFOS = new ConcurrentHashMap<>();
}
