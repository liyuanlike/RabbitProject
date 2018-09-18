package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.MonitorFlags;
import heartbeat.monitor.starter.domain.msgs.WeChatSendAppeal;
import heartbeat.monitor.starter.domain.msgs.WeChatSendReport;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为公众号系统系统封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.REPORTING_FLAG)
public class MonitorWeChatSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;

    /**
     * @param sendReport
     * @apiNote 为公众号系统封装 发送发出上报的监控数据
     */
    public void sendSendedReport(WeChatSendReport sendReport) {
        if (sendReport.getTaskId() == null) {
            throw new RuntimeException("taskId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(sendReport), WeChatSendReport.class);
    }

    /**
     * @param sendAppeal
     * @apiNote 为公众号系统封装 发送发出上诉的监控数据
     */
    public void sendSendedAppeal(WeChatSendAppeal sendAppeal) {
        if (sendAppeal.getAppealId() == null) {
            throw new RuntimeException("appealId 上诉ID不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(sendAppeal), WeChatSendAppeal.class);
    }

}
