package com.global.component.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 事件消息
 */
public class EventMessage implements Serializable {
    private String eventID; //此事件消息的ID
    private Date occurTime; // 事件发生时间
    private String description; //事件描述信息

    public EventMessage() {
    }

    public EventMessage(String eventID, Date occurTime) {
        this.eventID = eventID;
        this.occurTime = occurTime;
    }

    public EventMessage(String eventID, Date occurTime, String description) {
        this.eventID = eventID;
        this.occurTime = occurTime;
        this.description = description;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
