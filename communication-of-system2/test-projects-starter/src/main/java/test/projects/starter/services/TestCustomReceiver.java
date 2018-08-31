package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.processors.AbstractCustomReceiver;

@Component
public class TestCustomReceiver extends AbstractCustomReceiver {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCustomReceiver.class);

    @Override
    public void resolvePrePositionCustomMessage(String jsonMessage, String objectType, String prePositionId) {
        logger.info("===处理部委前置发来消息 json={} objectType={} prePositionId={}",jsonMessage,objectType,prePositionId);
    }

    @Override
    public void resolveOtherSystemCustomMessage(String jsonMessage, String objectType, String source) {
        logger.info("===接收别的系统自定义消息 json={} objectType={} source={}",jsonMessage,objectType,source);
    }
}
