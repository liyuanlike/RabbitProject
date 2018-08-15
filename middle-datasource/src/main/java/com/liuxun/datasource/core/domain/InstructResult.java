package com.liuxun.datasource.core.domain;

import java.io.Serializable;

/**
 * 指令的处理结果
 * @author liuxun
 */
public class InstructResult<T> implements Serializable {
    private String operationID; // 操作编号
    private String instructId;  // 对应的指令的唯一标识
    private Boolean isSuccess;  // 是否处理成功
    private T result;     // 处理后返回的结果

    public InstructResult() {
    }

    public InstructResult(String operationID, Boolean isSuccess, T result,String instructId) {
        this.operationID = operationID;
        this.isSuccess = isSuccess;
        this.result = result;
        this.instructId = instructId;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getInstructId() {
        return instructId;
    }

    public void setInstructId(String instructId) {
        this.instructId = instructId;
    }
}
