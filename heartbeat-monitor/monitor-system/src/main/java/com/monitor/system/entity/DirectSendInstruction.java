package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @apiNote 指挥系统发出指令的监控数据
 */
@Entity
@DiscriminatorValue(value = "DirectSendInstruction")
public class DirectSendInstruction extends DirectMsgInfo {
    public DirectSendInstruction() {
    }

    @Column
    private String taskId; // 任务ID

    @Column
    private String instructionId; // 发出的指令ID

    @Column
    private Date sendTime; // 发出时间

    @Column
    private String sendIp; // 发出IP

    @Column
    private Boolean isSuccess; // 是否成功


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
