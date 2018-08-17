package com.liuxun.datasource.core.domain;

import java.util.Date;

/**
 * @apiNote 封装处理指令相关的信息
 */
public class InstructTask {
    private String operationId; // 预定的操作编号
    private String sql; //需要执行的SQL语句

    public InstructTask() {
    }

    public InstructTask(String operationId, String sql) {
        this.operationId = operationId;
        this.sql = sql;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
