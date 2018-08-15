package com.liuxun.sub.controllers;

import com.liuxun.sub.domain.GlobalInfo;
import com.liuxun.sub.processors.InstructSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestSendController {

    @Autowired
    private InstructSender instructSender;

    @GetMapping("/send")
    public Object testSend(){
        instructSender.sendInstruct("query_user", "A", UUID.randomUUID().toString());
        return "success";
    }


}
