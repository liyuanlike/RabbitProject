package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @apiNote 网格员发送反馈 监控
 */

public class GridManSendExecution extends GridManMsgInfo {
    public GridManSendExecution() {
    }

    private String eventId; // 事件ID
    private String taskId; // 任务ID
    private String instructionId; // 指令ID
    private String executionId; // 反馈ID
    private Date sendTime; // 发出时间
    private String sendIp; // 发出IP
    private String receiveIp; // 接收IP
    private Boolean isSuccess; // 是否成功

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

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
