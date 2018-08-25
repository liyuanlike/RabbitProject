package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.AbstractPlanReceiver;

@Component
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.PLAN_FLAG)
public class TestPlanReceiver extends AbstractPlanReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TestPlanReceiver.class);
    @Override
    public void resolveDirectProgramme(String jsonInstruction, String objectType, String source) {
        logger.info("++++++ 预案系统接收到 指挥发来的处理方案 ++++ source={}",source);
    }
}
