package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 公众号消息监控
 */

@Entity
@Table(name = "WeChatMsgInfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class WeChatMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public WeChatMsgInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    private Date sendTime; // 发出时间

    private String sendIp; // 发出IP

    private String receiveIp; // 接收IP

    private Boolean isSuccess; // 是否成功

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
