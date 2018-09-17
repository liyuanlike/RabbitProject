package heartbeat.monitor.starter.domain.msgs;

import java.io.Serializable;

public class DirectMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public DirectMsgInfo() {
    }


    private String eventId; // 事件标识

    private String  receiveIp; // 接收IP

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }
}
