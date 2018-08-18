package com.aaa.bbb;

import com.liuxun.sub.api.InstructSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    private InstructSenderService instructSenderService;

    @GetMapping("/dy")
    public Object testDiaoyong(){
        Boolean send = instructSenderService.send("query", UUID.randomUUID().toString(),"system2");
        return send ? "send success" : "send failure";
    }
}
