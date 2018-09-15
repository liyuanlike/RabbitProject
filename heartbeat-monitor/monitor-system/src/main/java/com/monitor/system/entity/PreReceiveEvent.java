package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @apiNote 部委前置接收事件的监控信息
 * @author liuxun
 */
@Entity
@DiscriminatorValue(value = "PreReceiveEvent")
public class PreReceiveEvent extends PreMsgInfo {

    public PreReceiveEvent() {
    }

    @Column(length = 30)
    private String sendIp; //发出IP

    @Column(length = 30)
    private String receiveIp; //接收IP

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
}
