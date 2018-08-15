package com.liuxun.datasource.service;


import com.liuxun.datasource.core.domain.InstructResult;

public interface ProcessInstructService {
    public InstructResult processInstruction(String operationID,String instructId);
}
