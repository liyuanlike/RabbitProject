package test.reporting.starter.services;

import com.global.component.domain.Coordination;
import com.global.component.domain.GridManEventMessage;
import com.global.component.domain.PrePositionEventMessage;
import com.global.component.domain.WeChatEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reporting.rabbitmq.starter.processors.ReportCoordinationSender;
import reporting.rabbitmq.starter.processors.ReportEventReceiver;

@Component
public class ResolveEventMessage extends ReportEventReceiver {

    private static final Logger logger = LoggerFactory.getLogger(ReportEventReceiver.class);

    @Autowired
    private ReportCoordinationSender reportCoordinationSender;

    @Override
    public void resolvePrepositionEvent(PrePositionEventMessage prePositionEventMessage) {
        try {
            logger.info("==== 部委前置事件消息正在处理中......");
            Thread.sleep(3000L);
            logger.info("==== 开始向指挥系统发送协调事件......");
            Thread.sleep(3000L);
            Boolean isSuccess = reportCoordinationSender.sendCoordination(new Coordination());
            logger.info("====发送协调事件{}",isSuccess? "成功":"失败");
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
            logger.info("==== 开始向指挥系统发送协调事件......");
            Thread.sleep(3000L);
            Boolean isSuccess = reportCoordinationSender.sendCoordination(new Coordination());
            logger.info("====发送协调事件{}",isSuccess? "成功":"失败");
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
            logger.info("==== 开始向指挥系统发送协调事件......");
            Thread.sleep(3000L);
            Boolean isSuccess = reportCoordinationSender.sendCoordination(new Coordination());
            logger.info("====发送协调事件{}",isSuccess? "成功":"失败");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            logger.info("==== 网格员事件消息处理完毕  ");
        }
    }
}
