package com.liuxun.datasource.core.domain;

import java.io.Serializable;

/**
 * 表示一条指令
 * @author liuxun
 */
public class Instruction implements Serializable {
   private String operationID; // 操作编号
   private String resultRouteKey; //接收结果的路由键
   private String id; // 指令ID 有了此ID可以清楚知道返回结果适合哪个指令相对应的

    public Instruction() {
    }

    public Instruction(String operationID, String resultRouteKey,String id) {
        this.operationID = operationID;
        this.resultRouteKey = resultRouteKey;
        this.id = id;
    }

    public String getResultRouteKey() {
        return resultRouteKey;
    }

    public void setResultRouteKey(String resultRouteKey) {
        this.resultRouteKey = resultRouteKey;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
