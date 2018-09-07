package test.fdfs.starter.services;

import batch.update.starter.config.BatchUpdateGlobalInfo;
import batch.update.starter.processors.BatchSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendTest {

    @Autowired
    private BatchSender batchSender;

    @GetMapping("/send1")
    public Object send1(){
        final Boolean aBoolean = batchSender.sendData("{}", Object.class, BatchUpdateGlobalInfo.REPORTING_FLAG);
        return aBoolean? "success":"failure";
    }
}
