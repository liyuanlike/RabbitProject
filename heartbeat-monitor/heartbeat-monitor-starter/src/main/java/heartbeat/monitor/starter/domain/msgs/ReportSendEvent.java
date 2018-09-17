package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @apiNote 接报系统发送的协调事件监控
 */
public class ReportSendEvent extends ReportMsgInfo {
    public ReportSendEvent() {
    }

    private String eventId; // 接报发送的协调事件ID

    private Date sendTime; // 发出时间

    private String sendIp; // 发出IP

    private Boolean isSuccess; // 是否发送成功

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
