package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @apiNote 接报接收的消息 监控
 */
public class ReportReceiveMsg extends ReportMsgInfo {
    public ReportReceiveMsg() {
    }

    private String msgId;  // 接报系统接收到的消息的标识

    private Date receiveTime; // 接收消息的时间

    private String receiveSource; // 接收来源

    private Boolean isResolved; // 是否已处理

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveSource() {
        return receiveSource;
    }

    public void setReceiveSource(String receiveSource) {
        this.receiveSource = receiveSource;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }
}
