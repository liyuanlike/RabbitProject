package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置发出反馈的监控信息
 */
public class PreSendExecution extends PreMsgInfo {

    public PreSendExecution() {
    }

    private String eventId; // 事件ID

    private String instructionId; // 指令ID

    private String executionId; // 反馈ID

    private Date sendTime; // 发出时间

    private Boolean isSuccess; // 是否发出成功


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

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
