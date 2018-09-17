package heartbeat.monitor.starter.domain.msgs;



/**
 * @apiNote 公众号 上诉的监控
 * @author liuxun
 */
public class WeChatSendAppeal  extends WeChatMsgInfo{
   private String  appealId; // 上诉ID

    public String getAppealId() {
        return appealId;
    }

    public void setAppealId(String appealId) {
        this.appealId = appealId;
    }
}
