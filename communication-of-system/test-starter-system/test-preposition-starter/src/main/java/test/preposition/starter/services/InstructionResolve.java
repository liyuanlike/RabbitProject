package test.preposition.starter.services;

import com.global.component.domain.PrePositionExecution;
import com.global.component.domain.PrePositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import preposition.rabbitmq.starter.processors.PrePositionExecutionSender;
import preposition.rabbitmq.starter.processors.PrePositionInstructionReceiver;

@Component
public class InstructionResolve extends PrePositionInstructionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(InstructionResolve.class);
    @Autowired
    private PrePositionExecutionSender prePositionExecutionSender;

    @Override
    public void resolveInstruction(PrePositionInstruction prePositionInstruction) {
        logger.info("++++++++ 部委前置系统接收到 指挥发来的指令 ++++++++++");
        logger.info("++++++++ 对指令进行处理中 ......");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("+++++++ 指令处理完毕，开始向 指挥系统发送执行状况......");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Boolean isSuccess = prePositionExecutionSender.sendInstructionExecution(new PrePositionExecution());
        logger.info("++++++ 指令状况发送{}",isSuccess? "成功":"失败");
    }
}
