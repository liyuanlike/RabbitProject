package heartbeat.monitor.starter.domain.msgs;

import java.io.Serializable;

/**
 * @apiNote 接报的监控数据的父类
 * @author liuxun
 */
public class ReportMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public ReportMsgInfo() {
    }


    private String  receiveIp; // 接收IP

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }
}
