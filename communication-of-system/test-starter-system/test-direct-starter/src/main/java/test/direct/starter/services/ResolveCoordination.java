package test.direct.starter.services;

import com.global.component.domain.Coordination;
import com.global.component.domain.GridManInstrction;
import com.global.component.domain.PrePositionInstruction;
import direct.rabbitmq.starter.processors.InstructionSender;
import direct.rabbitmq.starter.processors.ReportCoorReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @apiNote 处理接收到的协调事件
 */
@Component
public class ResolveCoordination extends ReportCoorReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ResolveCoordination.class);

    @Autowired
    private InstructionSender instructionSender;

    @Override
    public void resolveCoordination(Coordination coordination) {
        logger.info("+++++++指挥系统接收到 协调事件 开始进行处理.....\n");
        try {
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
//        logger.info("+++++ 指挥系统 处理完毕协调事件 开始向 部委前置发送指令....");
//        Boolean isSuccess = instructionSender.sendInstructionToPrePosition(new PrePositionInstruction());
//        logger.info("+++++ 指挥系统发送指令{}\n", isSuccess? "成功":"失败");

        logger.info("+++++ 指挥系统 处理完毕协调事件 开始向 网格员系统发送指令....");
        Boolean isSuccess2 = instructionSender.sendInstructionToGridMan(new GridManInstrction());
        logger.info("+++++ 指挥系统发送指令{}\n", isSuccess2? "成功":"失败");

    }
}
