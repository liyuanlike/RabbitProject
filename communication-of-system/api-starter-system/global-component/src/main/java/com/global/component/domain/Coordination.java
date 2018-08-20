package com.global.component.domain;

import java.io.Serializable;

/**
 * @apiNote 接报系统向指挥系统发送的协调信息
 */
public class Coordination implements Serializable {
    private EventMessage eventMessage;

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
    }
}
