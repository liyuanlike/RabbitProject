package com.liuxun.sub.domain;

import java.io.Serializable;

/**
 * 指令的处理结果
 * @author liuxun
 */
public class InstructResult<T> implements Serializable {
    private String operationID; // 操作编号
    private String instructRequestId;  // 对应的一次指令请求的唯一标识
    private Boolean isSuccess;  // 是否处理成功
    private T result;     // 处理后返回的结果

    public InstructResult() {
    }

    public InstructResult(String operationID, Boolean isSuccess, T result,String instructRequestId) {
        this.operationID = operationID;
        this.isSuccess = isSuccess;
        this.result = result;
        this.instructRequestId = instructRequestId;
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

    public String getInstructRequestId() {
        return instructRequestId;
    }

    public void setInstructRequestId(String instructRequestId) {
        this.instructRequestId = instructRequestId;
    }
}
