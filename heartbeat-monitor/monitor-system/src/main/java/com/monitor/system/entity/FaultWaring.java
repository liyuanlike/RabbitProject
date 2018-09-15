package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @apiNote 故障告警实体类
 * @author liuxun
 */
@Entity
@Table(name = "FaultWaring")
public class FaultWaring implements Serializable {
    private static final long serialVersionUID = 1L;


    public FaultWaring() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 故障告警ID

    @Column(nullable = true,length = 100)
    private String  userName; // 登录用户名

    @Column(nullable = true,length = 20)
    private String ip;  // 用户登录IP

    private String  exceptionType; // 异常类型


    @Column(nullable = false,length = 100)
    private String flag; // 系统标识

    @Column(nullable = true,length = 30)
    private String  prepositionId; // 如果非部委前置系统，此字段是空值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPrepositionId() {
        return prepositionId;
    }

    public void setPrepositionId(String prepositionId) {
        this.prepositionId = prepositionId;
    }
}
