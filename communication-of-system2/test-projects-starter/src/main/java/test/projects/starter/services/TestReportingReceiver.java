package test.projects.starter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import projects.rabbitmq.starter.processors.AbstractReportingReceiver;

@Component
@ConditionalOnProperty(prefix = "projects.system",value = "flag",havingValue = "REPORTING")
public class TestReportingReceiver extends AbstractReportingReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TestReportingReceiver.class);
    @Override
    public void resolveWeChatEvent(String jsonWeChatEvent, String objectType, String source) {
      logger.info("+++++接报系统收到公众号事件 sourrce={} objectType={} json={}+++++++++++++",source,objectType,jsonWeChatEvent);
    }

    @Override
    public void resolvePrepositionEvent(String jsonPrePositionEvent, String objectType, String source) {
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("+++++接报系统收到前置事件 sourrce={} objectType={} json={}+++++++++++++",source,objectType,jsonPrePositionEvent);
    }

    @Override
    public void resolveGridManEvent(String jsonGridManEvent, String objectType, String source) {
        logger.info("+++++接报系统收到网格员事件 sourrce={} objectType={} json={}+++++++++++++",source,objectType,jsonGridManEvent);
    }
}
