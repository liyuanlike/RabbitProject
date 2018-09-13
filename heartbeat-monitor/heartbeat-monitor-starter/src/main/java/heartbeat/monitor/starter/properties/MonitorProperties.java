package heartbeat.monitor.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @apiNote 定义自定义的属性
 */

@Component
@ConfigurationProperties("projects.system")
public class MonitorProperties {
    /**
     * @apiNote 系统的唯一标识
     */
    private String flag;

    /**
     * @apiNote 是否开启使用
     */
    private Boolean enabled;


    /**
     * @apiNote 如果是统一类型系统的多个部部门 需要设置ID
     */
    private String id;

    /**
     * @apiNote 服务名称(中文名称)
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
