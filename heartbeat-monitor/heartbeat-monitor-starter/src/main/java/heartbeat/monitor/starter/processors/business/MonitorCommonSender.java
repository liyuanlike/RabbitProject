package heartbeat.monitor.starter.processors.business;

import com.alibaba.fastjson.JSON;
import heartbeat.monitor.starter.domain.UserCount;
import heartbeat.monitor.starter.domain.msgs.ExceptionUser;
import heartbeat.monitor.starter.domain.msgs.FaultWaring;
import heartbeat.monitor.starter.properties.MonitorProperties;
import heartbeat.monitor.starter.utils.BusinessGeneralSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @apiNote 用于发送 在线离线用户数 以及故障用户和告警
 */
@Component
public class MonitorCommonSender {
    @Autowired
    private BusinessGeneralSender businessGeneralSender;
    @Autowired
    private MonitorProperties properties;

    /**
     * @param inLineUserCount  在线用户数
     * @param outLineUserCount 离线用户数
     * @apiNote 发送在线/离线用户数
     */
    public void sendUserCount(Long inLineUserCount, Long outLineUserCount) {
        UserCount userCount = new UserCount();
        userCount.setFlag(properties.getFlag());
        userCount.setPrepositionId(properties.getId());
        userCount.setInLineUsers(inLineUserCount);
        userCount.setOutLineUsers(outLineUserCount);
        businessGeneralSender.generalSender(JSON.toJSONString(userCount), UserCount.class);
    }

    /**
     * @apiNote 统一的发送异常用户
     * @param exceptionUser
     */
    public void sendExceptionUser(ExceptionUser exceptionUser) {
        exceptionUser.setFlag(properties.getFlag());
        exceptionUser.setPrepositionId(properties.getId());
        businessGeneralSender.generalSender(JSON.toJSONString(exceptionUser), ExceptionUser.class);
    }

    /**
     * @apiNote 统一的发送故障告警
     * @param faultWaring
     */
    public void sendFaultWarning(FaultWaring faultWaring){
        faultWaring.setFlag(properties.getFlag());
        faultWaring.setPrepositionId(properties.getId());
        businessGeneralSender.generalSender(JSON.toJSONString(faultWaring), FaultWaring.class);
    }
}
