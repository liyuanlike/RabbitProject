package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置接收事件的监控信息
 */
@Entity
@DiscriminatorValue(value = "PreReceiveInstruction")
public class PreReceiveInstruction extends PreMsgInfo {

    public PreReceiveInstruction() {
    }

    @Column(length = 30)
    private String eventId; // 事件ID

    @Column(nullable = false, length = 30)
    private String instructionId;// 指令ID

    @Column
    private Date receiveTime; // 接收时间

    @Column
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
