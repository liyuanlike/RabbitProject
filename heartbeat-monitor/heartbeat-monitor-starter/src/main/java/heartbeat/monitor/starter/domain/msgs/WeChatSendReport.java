package heartbeat.monitor.starter.domain.msgs;


/**
 * @apiNote 公众号 上报的监控
 * @author liuxun
 */
public class WeChatSendReport extends WeChatMsgInfo{
   private String  reportId; // 上报ID

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
