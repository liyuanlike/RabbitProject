package heartbeat.monitor.starter.processors.business;

import heartbeat.monitor.starter.domain.MonitorFlags;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为网格员系统封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.GRIDMAN_FLAG)
public class MonitorGridManSender {

}
