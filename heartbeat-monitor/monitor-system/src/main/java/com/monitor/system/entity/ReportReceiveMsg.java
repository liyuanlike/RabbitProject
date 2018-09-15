package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @apiNote 接报接收的消息 监控
 */
@Entity
@DiscriminatorValue(value = "ReportReceiveMsg")
public class ReportReceiveMsg extends ReportMsgInfo {
    public ReportReceiveMsg() {
    }

    @Column(length = 30)
    private String msgId;  // 接报系统接收到的消息的标识

    @Column
    private Date receiveTime; // 接收消息的时间

    @Column
    private String receiveSource; // 接收来源

    @Column
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
