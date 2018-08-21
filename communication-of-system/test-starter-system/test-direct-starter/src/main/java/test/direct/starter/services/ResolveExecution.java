package test.direct.starter.services;

import com.global.component.domain.GridManExecution;
import com.global.component.domain.PrePositionExecution;
import direct.rabbitmq.starter.processors.ExecutionReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @apiNote 处理接收到的 执行状况
 */
@Component
public class ResolveExecution extends ExecutionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ResolveCoordination.class);
    @Override
    public void resolvePrePositionExecution(PrePositionExecution prePositionExecution) {
        logger.info("++++++++ 指挥系统接收到 前置系统发送的指令执行状况 ........");
    }

    @Override
    public void resolveGridManExecution(GridManExecution gridManExecution) {
        logger.info("+++++++ 指挥系统接收到 网格员系统发送的指令执行状况......");
    }
}
