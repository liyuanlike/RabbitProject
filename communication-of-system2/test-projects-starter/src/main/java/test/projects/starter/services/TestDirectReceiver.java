package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.domain.ProjectsFlags;
import projects.rabbitmq.starter.processors.AbstractDirectReceiver;

@Component
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = ProjectsFlags.DIRECT_FLAG)
public class TestDirectReceiver extends AbstractDirectReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TestDirectReceiver.class);

    @Override
    public void resolvePlanData(String jsonPlan, String objectType, String source) {
        logger.info("++++++指挥系统接收到预案系统 发来的预案  source={}",source);
    }

    @Override
    public void resolvePrePositionExecution(String jsonProPositionExecution, String objectType, String source) {
        logger.info("+++++ 指挥系统接收到部委前置发来的执行状况 ++++++++ source={}", source);
    }

    @Override
    public void resolveGridManExecution(String jsonGridManExecution, String objectType, String source) {
        logger.info("+++++ 指挥系统接收到网格员发来的执行状况 ++++++++ source={}", source);
    }

    @Override
    public void resolveCoordination(String jsonCoordination, String objectType, String source) {
        logger.info("+++++ 指挥系统接收到接报系统发来的协调信息 +++++++ source={}",source);
    }
}
