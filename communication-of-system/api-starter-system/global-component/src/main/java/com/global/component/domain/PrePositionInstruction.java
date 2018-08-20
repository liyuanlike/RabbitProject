package com.global.component.domain;

import java.io.Serializable;

/**
 * @apiNote 指挥系统向部委前置系统发送的指令类型
 * @author liuxun
 */
public class PrePositionInstruction implements Serializable {
    private PrePositionEventMessage prePositionEventMessage;

    public PrePositionEventMessage getPrePositionEventMessage() {
        return prePositionEventMessage;
    }

    public void setPrePositionEventMessage(PrePositionEventMessage prePositionEventMessage) {
        this.prePositionEventMessage = prePositionEventMessage;
    }
}
