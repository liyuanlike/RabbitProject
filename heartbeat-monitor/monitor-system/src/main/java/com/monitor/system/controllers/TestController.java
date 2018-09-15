package com.monitor.system.controllers;

import com.monitor.system.repository.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/add")
    public Object addTest(){
        testService.testInsert();
        return "success";
    }

    @GetMapping("/query")
    public Object queryTest(){
       return testService.testQuery();
    }
}
