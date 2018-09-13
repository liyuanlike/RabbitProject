package heartbeat.monitor.starter.domain;

import java.util.List;

/**
 * @apiNote 表示整个系统所有服务的健康状况
 */
public class Health {
    /**
     * @apiNote 所有部委前置的健康状况
     */
    private List<ServiceApp> preApps;

    /**
     * @apiNote 所有非部委前置系统的各个服务的健康状态
     */
    private List<ServiceApp> otherApps;

    public List<ServiceApp> getPreApps() {
        return preApps;
    }

    public void setPreApps(List<ServiceApp> preApps) {
        this.preApps = preApps;
    }

    public List<ServiceApp> getOtherApps() {
        return otherApps;
    }

    public void setOtherApps(List<ServiceApp> otherApps) {
        this.otherApps = otherApps;
    }
}
