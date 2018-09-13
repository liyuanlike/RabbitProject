package heartbeat.monitor.starter.domain;

/**
 * @apiNote 用于统计某一服务实例的一次指标数据
 */
public class Metrics {
    private String flag; // 服务标识
    private String id;  // 对于部委前置 具有唯一的ID
    private String ip;  // 服务实例的当前IP地址
    private Double memPercent; // 服务实例所在系统的内存占用率
    private Long timestamp; // 系统取样的时间戳
    private String name; //当前服务名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getMemPercent() {
        return memPercent;
    }

    public void setMemPercent(Double memPercent) {
        this.memPercent = memPercent;
    }
}
