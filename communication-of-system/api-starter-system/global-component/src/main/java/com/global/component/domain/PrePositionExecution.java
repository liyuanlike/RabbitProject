package com.global.component.domain;

import java.io.Serializable;

/**
 * @apiNote 部委前置系统的执行状况（部委前置系统发送给指挥系统的消息）
 */
public class PrePositionExecution implements Serializable {
    private PrePositionInstruction prePositionInstruction;

    public PrePositionInstruction getPrePositionInstruction() {
        return prePositionInstruction;
    }

    public void setPrePositionInstruction(PrePositionInstruction prePositionInstruction) {
        this.prePositionInstruction = prePositionInstruction;
    }
}
