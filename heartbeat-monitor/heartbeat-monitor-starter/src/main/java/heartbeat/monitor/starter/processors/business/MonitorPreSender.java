package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import heartbeat.monitor.starter.domain.MonitorFlags;
import heartbeat.monitor.starter.domain.msgs.PreReceiveInstruction;
import heartbeat.monitor.starter.domain.msgs.PreSendExecution;
import heartbeat.monitor.starter.domain.msgs.PreSendReport;
import heartbeat.monitor.starter.properties.MonitorProperties;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为部委前置系统封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.PREPOSITION_FLAG)
public class MonitorPreSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;
    @Autowired
    private MonitorProperties properties;

    /**
     * @param receiveInstruction
     * @return
     * @apiNote 为部委前置封装 发送接收指令的监控数据
     */
    public Boolean sendReceiveInstruction(PreReceiveInstruction receiveInstruction) {
        Boolean isSuccess = true;
        receiveInstruction.setPrepositionId(properties.getId());
        if (receiveInstruction.getInstructionId() == null) {
            throw new RuntimeException("instructionId 不能为空");
        }
        final String body = JSON.toJSONString(receiveInstruction);
        businessGeneralSender.generalSender(body, PreReceiveInstruction.class);
        return true;
    }

    /**
     * @apiNote 为部委前置封装 发送反馈的 监控数据
     * @param preSendExecution
     * @return
     */
    public Boolean sendSendExecution(PreSendExecution preSendExecution) {
        Boolean isSuccess = true;
        preSendExecution.setPrepositionId(properties.getId());
        if (preSendExecution.getExecutionId() == null) {
            throw new RuntimeException("executionId 不能为空");
        }
        final String body = JSON.toJSONString(preSendExecution);
        businessGeneralSender.generalSender(body, PreSendExecution.class);
        return true;
    }

    /**
     * @apiNote 为部委前置封装发送 上报的 监控数据
     * @param preSendReport
     * @return
     */
    public Boolean sendSendReport(PreSendReport preSendReport) {
        Boolean isSuccess = true;
        preSendReport.setPrepositionId(properties.getId());
        if (preSendReport.getTaskID() == null) {
            throw new RuntimeException("taskId 不能为空");
        }
        final String body = JSON.toJSONString(preSendReport);
        businessGeneralSender.generalSender(body, PreSendReport.class);
        return true;
    }

}
