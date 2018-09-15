package com.monitor.system.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @apiNote 公众号 上诉的监控
 * @author liuxun
 */
@Entity
@DiscriminatorValue(value = "WeChatSendAppeal")
public class WeChatSendAppeal  extends WeChatMsgInfo{
   private String  appealId; // 上诉ID

    public String getAppealId() {
        return appealId;
    }

    public void setAppealId(String appealId) {
        this.appealId = appealId;
    }
}
