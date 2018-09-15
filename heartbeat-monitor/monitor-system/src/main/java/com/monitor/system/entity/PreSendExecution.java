package com.monitor.system.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author liuxun
 * @apiNote 部委前置发出反馈的监控信息
 */
@Entity
@DiscriminatorValue(value = "PreSendExecution")
public class PreSendExecution extends PreMsgInfo {

    public PreSendExecution() {
    }

}
