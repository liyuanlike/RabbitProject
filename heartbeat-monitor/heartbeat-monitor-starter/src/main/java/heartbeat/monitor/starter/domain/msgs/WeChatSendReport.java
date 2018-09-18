package heartbeat.monitor.starter.domain.msgs;


/**
 * @author liuxun
 * @apiNote 公众号 上报的监控
 */
public class WeChatSendReport extends WeChatMsgInfo {
    private String taskId; // 上报ID

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
