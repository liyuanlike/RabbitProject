package com.monitor.system.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @apiNote 网格员发送任务监控
 */
@Entity
@DiscriminatorValue(value = "GridManSendReport")
public class GridManSendReport extends GridManMsgInfo {
    public GridManSendReport() {
    }

    private String taskId; // 任务ID
    private Date sendTime; // 发出时间
    private String sendIp; // 发出IP
    private String receiveIp; // 接收IP
    private Boolean isSuccess; // 是否成功

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
