package heartbeat.monitor.starter.domain.msgs;

import java.io.Serializable;

/**
 * @apiNote 接报监控数据的统一父类
 */
public class PreMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public PreMsgInfo() {
    }


    private String taskID;// 任务ID

    private String sendIp; //发出IP

    private String receiveIp; //接收IP

    private String prepositionId; // 此处处理的是部委前置的信息，此字段值用不为null

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }

    public String getPrepositionId() {
        return prepositionId;
    }

    public void setPrepositionId(String prepositionId) {
        this.prepositionId = prepositionId;
    }
}
