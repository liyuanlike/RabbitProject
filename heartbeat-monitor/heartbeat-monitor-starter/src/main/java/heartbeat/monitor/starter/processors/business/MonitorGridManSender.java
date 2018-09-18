package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.MonitorFlags;
import heartbeat.monitor.starter.domain.msgs.*;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @apiNote 为网格员系统(综治通)封装 监控数据发送类
 */

@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorFlags.GRIDMAN_FLAG)
public class MonitorGridManSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;

    /**
     * @param receiveInstruction
     * @apiNote 为网格员系统封装发送 接收指令的监控
     */
    public void sendReceivedInstruction(GridManReceiveInstruction receiveInstruction) {
        if (receiveInstruction.getInstructionId() == null) {
            throw new RuntimeException("instructionId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(receiveInstruction), GridManReceiveInstruction.class);
    }

    /**
     * @param sendExecution
     * @apiNote 为网格员系统封装发送  发出反馈的监控
     */
    public void sendSendedExecution(GridManSendExecution sendExecution) {
        if (sendExecution.getExecutionId() == null) {
            throw new RuntimeException("executionId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(sendExecution), GridManSendExecution.class);
    }

    /**
     * @param receiveTask
     * @apiNote 为网格员封装发送 接收任务的监控数据
     */
    public void sendReceivedTask(GridManReceiveTask receiveTask) {
        if (receiveTask.getTaskId() == null) {
            throw new RuntimeException("taskId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(receiveTask), GridManReceiveTask.class);
    }

    /**
     * @param sendTask
     * @apiNote 为网格员封装发送 发出任务的监控数据
     */
    public void sendSendedTask(GridManSendTask sendTask) {
        if (sendTask.getTaskId() == null) {
            throw new RuntimeException("taskId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(sendTask), GridManSendTask.class);
    }

    /**
     * @param sendReport
     * @apiNote 为网格员封装发送 发出上报的监控数据
     */
    public void sendSendedReport(GridManSendReport sendReport) {
        if (sendReport.getTaskId() == null) {
            throw new RuntimeException("taskId不能为null");
        }
        businessGeneralSender.generalSender(JSON.toJSONString(sendReport), GridManSendReport.class);
    }

}
