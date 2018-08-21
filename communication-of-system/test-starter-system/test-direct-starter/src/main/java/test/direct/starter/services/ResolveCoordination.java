package test.direct.starter.services;

import com.global.component.domain.Coordination;
import direct.rabbitmq.starter.processors.ReportCoorReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @apiNote 处理接收到的协调事件
 */
@Component
public class ResolveCoordination extends ReportCoorReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ResolveCoordination.class);
    @Override
    public void resolveCoordination(Coordination coordination) {
        logger.info("+++++++指挥系统接收到 协调事件 开始进行处理.....");
    }
}
