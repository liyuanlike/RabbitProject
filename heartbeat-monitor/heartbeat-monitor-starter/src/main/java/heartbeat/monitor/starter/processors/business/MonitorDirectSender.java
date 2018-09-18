package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.MonitorFlags;
import heartbeat.monitor.starter.domain.msgs.DirectReceiveEvent;
import heartbeat.monitor.starter.domain.msgs.DirectReceiveExecution;
import heartbeat.monitor.starter.domain.msgs.DirectSendInstruction;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为指挥系统封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.DIRECT_FLAG)
public class MonitorDirectSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;

    /**
     * @param directReceiveEvent
     * @apiNote 为指挥系统封装 发送接收事件的监控数据
     */
    public void sendReceivedEvent(DirectReceiveEvent directReceiveEvent) {
        if (directReceiveEvent.getEventId() == null) {
            throw new RuntimeException("eventId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(directReceiveEvent), DirectReceiveEvent.class);
    }

    /**
     * @param directSendInstruction
     * @apiNote 为指挥系统封装 发送 发出指令的监控数据
     */
    public void sendSendedInstruction(DirectSendInstruction directSendInstruction) {
        if (directSendInstruction.getInstructionId() == null) {
            throw new RuntimeException("instructionId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(directSendInstruction), DirectSendInstruction.class);
    }

    /**
     * @param execution
     * @apiNote 为指挥系统封装发送 接收反馈(执行状况)的监控数据
     */
    public void sendReceivedExecution(DirectReceiveExecution execution) {
        if (execution.getExecutionId() == null) {
            throw new RuntimeException("executionId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(execution), DirectReceiveExecution.class);
    }

}
