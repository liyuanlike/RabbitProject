package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置发出上报的监控信息
 */
public class PreSendReport extends PreMsgInfo {

    public PreSendReport() {
    }

    private Date sendTime; // 发出时间

    private Boolean isSuccess; // 是否发出成

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
