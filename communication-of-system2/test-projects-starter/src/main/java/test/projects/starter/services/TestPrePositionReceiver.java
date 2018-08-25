package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.AbstractPrePositionReceiver;

@Component
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.PREPOSITION_FLAG)
public class TestPrePositionReceiver extends AbstractPrePositionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TestPrePositionReceiver.class);
    @Override
    public void resolveDirectInstruction(String jsonInstruction, String objectType, String source) {
        logger.info("+++++部委前置接收到 指挥发来的指令+++++ source={}",source);
    }
}
