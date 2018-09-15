package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @apiNote 指挥系统消息的 监控数据父类
 */
@Entity
@Table(name = "DirectMsgInfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class DirectMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public DirectMsgInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    @Column(length = 30)
    private String eventId; // 事件标识

    @Column(length = 30)
    private String  receiveIp; // 接收IP

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }
}
