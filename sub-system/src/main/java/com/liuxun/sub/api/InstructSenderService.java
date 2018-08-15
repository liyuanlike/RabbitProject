package com.liuxun.sub.api;

import com.liuxun.sub.processors.InstructSender;

public class InstructSenderService {
    private InstructSender instructSender;
    private String resultRouteKey;

    public InstructSenderService(InstructSender instructSender,String resultRouteKey) {
        this.instructSender = instructSender;
        this.resultRouteKey = resultRouteKey;
    }

    public Boolean send(String operationID,String instructId){
        Boolean isSuccess = true;
        try {
            instructSender.sendInstruct(operationID,resultRouteKey,instructId);
        }catch (Exception e){
            isSuccess = false;
            e.printStackTrace();
        }finally {
            return isSuccess;
        }
    }
}
