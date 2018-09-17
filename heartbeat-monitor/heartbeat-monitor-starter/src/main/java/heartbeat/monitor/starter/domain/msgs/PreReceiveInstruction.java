package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置接收事件的监控信息
 */
public class PreReceiveInstruction extends PreMsgInfo {

    public PreReceiveInstruction() {
    }

    private String eventId; // 事件ID

    private String instructionId;// 指令ID

    private Date receiveTime; // 接收时间

    private Boolean isResolved; // 是否已处理

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

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }
}
