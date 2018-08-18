package com.liuxun.datasource.service.impl;

import com.liuxun.datasource.core.domain.InstructResult;
import com.liuxun.datasource.service.ProcessInstructService;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstructServiceImpl implements ProcessInstructService {
    @Override
    public InstructResult<?> processInstruction(String operationID,String instructRequestId) {

        String result = "默认不传操作编号的数据";
        if (operationID.equals("query_user")){
            result = "已查询到用户信息，如下......";
        }else if (operationID.equals("query_product")){
            result = "已查询到用户信息，如下......";
        }
        InstructResult<String> instructResult = new InstructResult<>();
        instructResult.setInstructRequestId(instructRequestId);
        instructResult.setOperationID(operationID);
        instructResult.setSuccess(true);
        instructResult.setResult(result);
        return instructResult;
    }
}
