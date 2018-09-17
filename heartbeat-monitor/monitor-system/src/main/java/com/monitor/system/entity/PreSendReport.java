package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author liuxun
 * @apiNote 部委前置发出上报的监控信息
 */
@Entity
@DiscriminatorValue(value = "PreSendReport")
public class PreSendReport extends PreMsgInfo {

    public PreSendReport() {
    }

    @Column
    private Date sendTime; // 发出时间

    @Column
    private Boolean isSuccess; // 是否发出成

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
