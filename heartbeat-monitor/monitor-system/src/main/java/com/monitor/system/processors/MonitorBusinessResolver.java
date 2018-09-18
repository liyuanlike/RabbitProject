package com.monitor.system.processors;

import com.alibaba.fastjson.JSON;
import com.monitor.system.config.MonitorSystemInfo;
import com.monitor.system.entity.*;
import com.monitor.system.repository.GeneralService;
import heartbeat.monitor.starter.domain.UserCount;
import heartbeat.monitor.starter.processors.AbstractBusinessReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @apiNote 处理监控系统接收到的 业务数据
 */
@Component
public class MonitorBusinessResolver extends AbstractBusinessReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MonitorBusinessResolver.class);
    @Autowired
    private GeneralService generalService;

    @Override
    public void resolveBusiness(String body, String objectType) {
        if (objectType.equals(UserCount.class.getSimpleName())) { // 处理 用户离线在线数统计  不持久化
            UserCount userCount = JSON.parseObject(body, UserCount.class);
            String key = userCount.getFlag() + (userCount.getPrepositionId() == null ? "" : "_" + userCount.getPrepositionId());
            MonitorSystemInfo.IN_OUT_LINE_USER_MAP.put(key, userCount);
        } else if (objectType.equals(ExceptionUser.class.getSimpleName())) { // 处理异常用户 直接保存
            ExceptionUser exceptionUser = JSON.parseObject(body, ExceptionUser.class);
            generalService.persisent(exceptionUser);
        } else if (objectType.equals(FaultWaring.class.getSimpleName())) { // 处理 故障告警
            FaultWaring faultWaring = JSON.parseObject(body, FaultWaring.class);
            generalService.persisent(faultWaring);
        }

        // 处理部委前置的逻辑
        if (objectType.equals(PreReceiveInstruction.class.getSimpleName())) {
            PreReceiveInstruction pre = JSON.parseObject(body, PreReceiveInstruction.class);
            generalService.saveOrUpdateByField(PreMsgInfo.class, PreReceiveInstruction.class, "instructionId", pre.getInstructionId(), pre);
            logger.info("+++++++部委前置覆盖成功 ++++++++++");
        } else if (objectType.equals(PreSendExecution.class.getSimpleName())) {
            PreSendExecution pre = JSON.parseObject(body, PreSendExecution.class);
            generalService.saveOrUpdateByField(PreMsgInfo.class, PreSendExecution.class, "executionId", pre.getExecutionId(), pre);
            logger.info("+++++++部委前置覆盖成功 ++++++++++");
        } else if (objectType.equals(PreSendReport.class.getSimpleName())) {
            PreSendReport pre = JSON.parseObject(body, PreSendReport.class);
            generalService.saveOrUpdateByField(PreMsgInfo.class, PreSendReport.class, "taskID", pre.getTaskID(), pre);
            logger.info("+++++++部委前置覆盖成功 ++++++++++");
        }

        // 处理接报系统的 监控逻辑
        if (objectType.equals(ReportReceiveMsg.class.getSimpleName())) {
            ReportReceiveMsg report = JSON.parseObject(body, ReportReceiveMsg.class);
            generalService.saveOrUpdateByField(ReportMsgInfo.class, ReportReceiveMsg.class, "msgId", report.getMsgId(), report);
            logger.info("+++++++接报监控覆盖成功 ++++++++++");
        } else if (objectType.equals(ReportSendEvent.class.getSimpleName())) {
            ReportSendEvent report = JSON.parseObject(body, ReportSendEvent.class);
            generalService.saveOrUpdateByField(ReportMsgInfo.class, ReportSendEvent.class, "eventId", report.getEventId(), report);
            logger.info("+++++++接报监控覆盖成功 ++++++++++");
        }

        // 处理指挥系统的 监控逻辑
        if (objectType.equals(DirectReceiveEvent.class.getSimpleName())) {
            DirectReceiveEvent direct = JSON.parseObject(body, DirectReceiveEvent.class);
            generalService.saveOrUpdateByField(DirectMsgInfo.class, DirectReceiveEvent.class, "eventId", direct.getEventId(), direct);
            logger.info("+++++++指挥监控覆盖成功 ++++++++++");
        } else if (objectType.equals(DirectSendInstruction.class.getSimpleName())) {
            DirectSendInstruction direct = JSON.parseObject(body, DirectSendInstruction.class);
            generalService.saveOrUpdateByField(DirectMsgInfo.class, DirectSendInstruction.class, "instructionId", direct.getInstructionId(), direct);
            logger.info("+++++++指挥监控覆盖成功 ++++++++++");
        } else if (objectType.equals(DirectReceiveExecution.class.getSimpleName())) {
            DirectReceiveExecution direct = JSON.parseObject(body, DirectReceiveExecution.class);
            generalService.saveOrUpdateByField(DirectMsgInfo.class, DirectReceiveExecution.class, "executionId", direct.getExecutionId(), direct);
            logger.info("+++++++指挥监控覆盖成功 ++++++++++");
        }

        // 处理公众号系统的 监控逻辑
        if (objectType.equals(WeChatSendReport.class.getSimpleName())) {
            WeChatSendReport wechat = JSON.parseObject(body, WeChatSendReport.class);
            generalService.saveOrUpdateByField(WeChatMsgInfo.class, WeChatSendReport.class, "taskId", wechat.getTaskId(), wechat);
            logger.info("+++++++公众号系统监控覆盖成功 ++++++++++");
        } else if (objectType.equals(WeChatSendAppeal.class.getSimpleName())) {
            WeChatSendAppeal wechat = JSON.parseObject(body, WeChatSendAppeal.class);
            generalService.saveOrUpdateByField(WeChatMsgInfo.class, WeChatSendAppeal.class, "appealId", wechat.getAppealId(), wechat);
            logger.info("+++++++公众号系统监控覆盖成功 ++++++++++");
        }

        // 处理 网格员系统的 监控逻辑
        if (objectType.equals(GridManReceiveInstruction.class.getSimpleName())) {
            GridManReceiveInstruction gridMan = JSON.parseObject(body, GridManReceiveInstruction.class);
            generalService.saveOrUpdateByField(GridManMsgInfo.class, GridManReceiveInstruction.class, "instructionId", gridMan.getInstructionId(), gridMan);
            logger.info("+++++++网格员系统监控覆盖成功 ++++++++++");
        } else if (objectType.equals(GridManSendExecution.class.getSimpleName())) {
            GridManSendExecution gridMan = JSON.parseObject(body, GridManSendExecution.class);
            generalService.saveOrUpdateByField(GridManMsgInfo.class, GridManSendExecution.class, "executionId", gridMan.getExecutionId(), gridMan);
            logger.info("+++++++网格员系统监控覆盖成功 ++++++++++");
        } else if (objectType.equals(GridManReceiveTask.class.getSimpleName())) {
            GridManReceiveTask gridMan = JSON.parseObject(body, GridManReceiveTask.class);
            generalService.saveOrUpdateByField(GridManMsgInfo.class, GridManReceiveTask.class, "taskId", gridMan.getTaskId(), gridMan);
            logger.info("+++++++网格员系统监控覆盖成功 ++++++++++");
        } else if (objectType.equals(GridManSendTask.class.getSimpleName())) {
            GridManSendTask gridMan = JSON.parseObject(body, GridManSendTask.class);
            generalService.saveOrUpdateByField(GridManMsgInfo.class, GridManSendTask.class, "taskId", gridMan.getTaskId(), gridMan);
            logger.info("+++++++网格员系统监控覆盖成功 ++++++++++");
        } else if (objectType.equals(GridManSendReport.class.getSimpleName())) {
            GridManSendReport gridMan = JSON.parseObject(body, GridManSendReport.class);
            generalService.saveOrUpdateByField(GridManMsgInfo.class, GridManSendReport.class, "taskId", gridMan.getTaskId(), gridMan);
            logger.info("+++++++网格员系统监控覆盖成功 ++++++++++");
        }

    }
}
