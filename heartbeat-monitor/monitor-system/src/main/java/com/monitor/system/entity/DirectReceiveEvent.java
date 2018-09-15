package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @apiNote 指挥系统接收事件的监控数据
 */
@Entity
@DiscriminatorValue(value = "DirectReceiveEvent")
public class DirectReceiveEvent extends DirectMsgInfo {
    public DirectReceiveEvent() {
    }

    @Column
    private Date receiveTime;// 接收时间

    @Column
    private String sendIp; // 发出IP

    @Column
    private Boolean isResolved; // 是否处理完毕

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

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }
}
