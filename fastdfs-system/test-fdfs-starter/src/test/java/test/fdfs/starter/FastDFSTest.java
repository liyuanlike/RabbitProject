package test.fdfs.starter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import system.fastdfs.starter.config.AppConfig;
import system.fastdfs.starter.processors.FdfsClientWrapper;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSTest.class);

    @Autowired
    FdfsClientWrapper fdfsClientWrapper;


    @Test
    public void testUploadFile() {
        String s = null;
        try {
            s = fdfsClientWrapper.uploadFile(new File("/Users/liuxun/Desktop/aaaa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("{}",s);
    }

}
