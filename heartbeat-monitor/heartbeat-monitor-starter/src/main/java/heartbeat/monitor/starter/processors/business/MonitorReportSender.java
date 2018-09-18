package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.MonitorFlags;
import heartbeat.monitor.starter.domain.msgs.ReportReceiveMsg;
import heartbeat.monitor.starter.domain.msgs.ReportSendEvent;
import heartbeat.monitor.starter.properties.MonitorProperties;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为接报系统封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.REPORTING_FLAG)
public class MonitorReportSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;
    @Autowired
    private MonitorProperties properties;

    /**
     * @param reportReceiveMsg
     * @apiNote 为接报系统封装 发送接收消息的监控
     */
    public void sendReceiveMessage(ReportReceiveMsg reportReceiveMsg) {
        if (reportReceiveMsg.getMsgId() == null) {
            throw new RuntimeException("msgId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(reportReceiveMsg), ReportReceiveMsg.class);
    }

    /**
     * @param reportSendEvent
     * @apiNote 为接报系统封装接收发送事件的监控数据
     */
    public void sendSendedEvent(ReportSendEvent reportSendEvent) {
        if (reportSendEvent.getEventId() == null) {
            throw new RuntimeException("eventId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(reportSendEvent), ReportSendEvent.class);
    }
}
