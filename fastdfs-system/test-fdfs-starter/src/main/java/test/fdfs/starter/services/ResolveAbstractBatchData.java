package test.fdfs.starter.services;

import batch.update.starter.processors.AbstractBatchReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResolveAbstractBatchData extends AbstractBatchReceiver {

    private static final Logger logger = LoggerFactory.getLogger(ResolveAbstractBatchData.class);


    @Override
    public void resolveData(String dataContent, String objectType, String source) {
        logger.info("++++++++ 收到数据 ++++++++++++");
    }

    @Override
    public void resolveInstruction(String instructionContent, String objectType, String source) {
        logger.info("++++++++ 收到指令 ++++++++++++");

    }
}
