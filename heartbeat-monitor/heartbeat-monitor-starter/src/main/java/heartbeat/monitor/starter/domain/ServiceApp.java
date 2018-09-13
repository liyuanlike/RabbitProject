package heartbeat.monitor.starter.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @apiNote 代表一项服务
 */
public class ServiceApp {
    private String serviceName; // 服务名
    private String flag;  // 服务标识
    private String  id = null;   // 对于前置系统id 不为空值

    private Set<InstanceItem> instances; // 表示所有实例的地址以及健康状态

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<InstanceItem> getInstances() {
        return instances;
    }

    public void setInstances(Set<InstanceItem> instances) {
        this.instances = instances;
    }
}
