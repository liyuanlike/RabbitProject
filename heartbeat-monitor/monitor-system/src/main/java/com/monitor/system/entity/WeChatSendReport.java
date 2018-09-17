package com.monitor.system.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @apiNote 公众号 上报的监控
 * @author liuxun
 */
@Entity
@DiscriminatorValue(value = "WeChatSendReport")
public class WeChatSendReport extends WeChatMsgInfo{
   private String  taskId; // 上报ID

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
