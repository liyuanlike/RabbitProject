package com.monitor.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liuxun
 * @apiNote 心跳信息
 */

@Entity
@Table(name = "CommonHeartBeat")
public class CommonHeartBeat implements Serializable {
    private static final long serialVersionUID = 1L;

    public CommonHeartBeat() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 主键ID

    @Lob
    @Column(columnDefinition = "TEXT")
    private String preInfos;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notPreInfos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreInfos() {
        return preInfos;
    }

    public void setPreInfos(String preInfos) {
        this.preInfos = preInfos;
    }

    public String getNotPreInfos() {
        return notPreInfos;
    }

    public void setNotPreInfos(String notPreInfos) {
        this.notPreInfos = notPreInfos;
    }
}
