package com.monitor.system.entity;

import javax.persistence.*;

/**
 * @apiNote 网格员的监控数据
 */
@Entity
@Table(name = "GridManMsgInfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class GridManMsgInfo {
    private static final long serialVersionUID = 1L;

    public GridManMsgInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
