package com.aaa.bbb;

import com.alibaba.fastjson.JSON;
import com.liuxun.sub.domain.InstructResult;
import com.liuxun.sub.processors.ResultReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReceiveResult extends ResultReceiver{
    private static final Logger logger = LoggerFactory.getLogger(ReceiveResult.class);

    @Override
    public void receviveInstructResult(InstructResult instructResult) {
        logger.info("======对处理结果进行业务处理===......", JSON.toJSON(instructResult));
    }
}
