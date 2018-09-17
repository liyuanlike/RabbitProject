package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @author liuxun
 * @apiNote 网格员接收指令的监控
 */

public class GridManReceiveInstruction extends GridManMsgInfo {
    public GridManReceiveInstruction() {
    }

    private String eventId; // 事件ID
    private String taskId; // 任务ID
    private String instructionId; // 指令ID
    private Date receiveTime; // 接收时间
    private String sendIp; // 发出IP
    private String receiveIp; // 接收IP
    private Boolean isResolved; // 是否处理完毕


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }
}
