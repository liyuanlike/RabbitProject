package com.monitor.system.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @apiNote 网格员接收任务监控
 */
@Entity
@DiscriminatorValue(value = "GridManReceiveTask")
public class GridManReceiveTask extends GridManMsgInfo {
    public GridManReceiveTask() {
    }

    private String taskId; // 任务ID
    private Date receiveTime; // 接收时间
    private String sendIp; // 发出IP
    private String receiveIp; // 接收IP
    private Boolean isResolved; // 是否处理完毕

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }
}
