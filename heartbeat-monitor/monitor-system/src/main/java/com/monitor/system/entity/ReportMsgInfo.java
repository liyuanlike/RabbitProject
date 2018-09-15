package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @apiNote 接报的监控数据的父类
 * @author liuxun
 */
@Entity
@Table(name = "ReportMsgInfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class ReportMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public ReportMsgInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    @Column(length = 30)
    private String  receiveIp; // 接收IP

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
