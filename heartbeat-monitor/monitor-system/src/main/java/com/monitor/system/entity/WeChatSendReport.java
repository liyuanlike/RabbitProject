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
   private String  reportId; // 上报ID

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
