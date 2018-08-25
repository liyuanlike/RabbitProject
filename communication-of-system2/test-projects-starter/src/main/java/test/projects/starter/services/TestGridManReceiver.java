package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.AbstractGridManReceiver;

@Component
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.GRIDMAN_FLAG)
public class TestGridManReceiver extends AbstractGridManReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TestGridManReceiver.class);
    @Override
    public void resolveDirectInstruction(String jsonInstruction, String objectType, String source) {
        logger.info("++++++  网格员接收到指挥发来的指令 +++++++ source={}",source);
    }
}
