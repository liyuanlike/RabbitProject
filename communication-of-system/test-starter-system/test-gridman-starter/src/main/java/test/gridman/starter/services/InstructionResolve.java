package test.gridman.starter.services;

import com.global.component.domain.GridManExecution;
import com.global.component.domain.GridManInstrction;
import com.global.component.domain.PrePositionExecution;
import com.global.component.domain.PrePositionInstruction;
import gridman.rabbitmq.starter.processors.GridManExecutionSender;
import gridman.rabbitmq.starter.processors.GridManInstructionReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructionResolve extends GridManInstructionReceiver {
    private static final Logger logger = LoggerFactory.getLogger(InstructionResolve.class);
    @Autowired
    private GridManExecutionSender gridManExecutionSender;

    @Override
    public  void resolveInstruction(GridManInstrction gridManInstrction){
        logger.info("++++++++ 网格员系统接收到 指挥发来的指令 ++++++++++");
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
        Boolean isSuccess = gridManExecutionSender.sendInstructionExecution(new GridManExecution());
        logger.info("++++++ 指令状况发送{}",isSuccess? "成功":"失败");

    }
}
