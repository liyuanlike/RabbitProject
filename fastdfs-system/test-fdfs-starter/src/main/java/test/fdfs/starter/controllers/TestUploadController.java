package test.fdfs.starter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import system.fastdfs.starter.processors.FdfsClientWrapper;

import java.io.File;
import java.io.IOException;

@RestController
public class TestUploadController {
    private static final Logger logger = LoggerFactory.getLogger(TestUploadController.class);

    @Autowired
    private FdfsClientWrapper fdfsClientWrapper;

    @GetMapping("/upload")
    public Object uploadTest(){
        String s = null;
        try {
            s = fdfsClientWrapper.uploadFile(new File("/Users/liuxun/Desktop/aaaa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("{}",s);
        return s;
    }
}
