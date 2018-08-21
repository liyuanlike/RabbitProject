package test.reporting.starter.services;

import com.global.component.domain.GridManEventMessage;
import com.global.component.domain.PrePositionEventMessage;
import com.global.component.domain.WeChatEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reporting.rabbitmq.starter.processors.ReportEventReceiver;

@Component
public class ResolveEventMessage extends ReportEventReceiver {

    private static final Logger logger = LoggerFactory.getLogger(ReportEventReceiver.class);

    @Override
    public void resolvePrepositionEvent(PrePositionEventMessage prePositionEventMessage) {
        try {
            logger.info("==== 部委前置事件消息正在处理中......");
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("==== 部委前置事件消息处理完毕  ");
        }
    }

    @Override
    public void resolveWeChatEvent(WeChatEventMessage weChatEventMessage) {
        try {
            logger.info("==== 公众号事件消息正在处理中......");
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("==== 公众号事件消息处理完毕  ");
        }
    }

    @Override
    public void resolveGridManEvent(GridManEventMessage gridManEventMessage) {
        try {
            logger.info("==== 网格员事件消息正在处理中......");
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("==== 网格员事件消息处理完毕  ");
        }
    }
}
