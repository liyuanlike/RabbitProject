package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置发出反馈的监控信息
 */
@Entity
@DiscriminatorValue(value = "PreSendExecution")
public class PreSendExecution extends PreMsgInfo {

    public PreSendExecution() {
    }

    @Column(length = 30)
    private String eventId; // 事件ID

    @Column(nullable = false, length = 30)
    private String instructionId; // 指令ID

    @Column
    private String executionId; // 反馈ID

    @Column
    private Date sendTime; // 发出时间

    @Column
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
