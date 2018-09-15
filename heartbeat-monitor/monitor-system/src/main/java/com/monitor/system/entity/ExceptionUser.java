package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 异常用户
 * @author liuxun
 */
@Entity
@Table(name = "ExceptionUser")
public class ExceptionUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public ExceptionUser() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 异常用户ID

    @Column
    private Date warnBeginTime; // 预警开始时间

    @Column
    private String warnIp; // 预警IP

    @Column
    private String warningContent; //预警内容


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

    public Date getWarnBeginTime() {
        return warnBeginTime;
    }

    public void setWarnBeginTime(Date warnBeginTime) {
        this.warnBeginTime = warnBeginTime;
    }

    public String getWarnIp() {
        return warnIp;
    }

    public void setWarnIp(String warnIp) {
        this.warnIp = warnIp;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
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
