package heartbeat.monitor.starter.domain;

import java.util.Objects;

/**
 * @apiNote 代表某个服务的实例
 */
public class InstanceItem {
    /**
     * @apiNote 表示此服务实例的IP地址
     */
    private String ip;

    /**
     * @apiNote 代表此服务实例的健康状况  0:正常 1:遇见 2:故障
     */
    private Integer level;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceItem that = (InstanceItem) o;
        return Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}
