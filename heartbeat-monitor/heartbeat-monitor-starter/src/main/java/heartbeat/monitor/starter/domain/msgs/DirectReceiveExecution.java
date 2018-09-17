package heartbeat.monitor.starter.domain.msgs;

import java.util.Date;

/**
 * @apiNote 指挥系统接收反馈的监控数据
 */
public class DirectReceiveExecution extends DirectMsgInfo {
    public DirectReceiveExecution() {
    }

    private String taskId; // 任务ID

    private  String instructionId; // 发出的指令ID

    private String executionId; // 反馈ID

    private Date receiveTime;// 接收时间

    private String receiveSource; // 接收来源

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
}
