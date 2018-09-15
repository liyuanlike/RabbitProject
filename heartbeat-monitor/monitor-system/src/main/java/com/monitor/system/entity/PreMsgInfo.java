package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 接报监控数据的统一父类
 */
@Entity
@Table(name = "PreMsgInfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class PreMsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public PreMsgInfo() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    @Column(length = 30)
    private String eventId; // 事件ID

    @Column(length = 30)
    private String taskID;// 任务ID

    @Column(length = 30)
    private String instructionId;// 指令ID

    @Column
    private Date receiveTime; // 接收时间

    @Column
    private Boolean isResolved; // 是否处理完毕


    @Column(nullable = false, length = 30)
    private String prepositionId; // 此处处理的是部委前置的信息，此字段值用不为null

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }

    public String getPrepositionId() {
        return prepositionId;
    }

    public void setPrepositionId(String prepositionId) {
        this.prepositionId = prepositionId;
    }
}
