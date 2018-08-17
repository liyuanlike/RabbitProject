package com.liuxun.datasource;

import com.liuxun.datasource.processors.TestSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SendTest {
    @Autowired
    private TestSender sender;

    @Test
    public void topic() throws Exception {
        sender.send();
    }
}