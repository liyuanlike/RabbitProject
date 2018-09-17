package heartbeat.monitor.starter.domain.msgs;

import java.io.Serializable;
import java.util.Date;

/**
 * @apiNote 异常用户
 * @author liuxun
 */
public class ExceptionUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public ExceptionUser() {
    }

    private Date warnBeginTime; // 预警开始时间

    private String warnIp; // 预警IP

    private String warningContent; //预警内容


    private String flag; // 系统标识

    private String  prepositionId; // 如果非部委前置系统，此字段是空值

    public Date getWarnBeginTime() {
        return warnBeginTime;
    }

    public void setWarnBeginTime(Date warnBeginTime) {
        this.warnBeginTime = warnBeginTime;
    }

    public String getWarnIp() {
        return warnIp;
    }

    public void setWarnIp(String warnIp) {
        this.warnIp = warnIp;
    }

    public String getWarningContent() {
        return warningContent;
    }

    public void setWarningContent(String warningContent) {
        this.warningContent = warningContent;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPrepositionId() {
        return prepositionId;
    }

    public void setPrepositionId(String prepositionId) {
        this.prepositionId = prepositionId;
    }
}
