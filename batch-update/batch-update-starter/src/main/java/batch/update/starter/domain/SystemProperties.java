package batch.update.starter.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @apiNote 定义自定义的属性
 */

@ConfigurationProperties("batch.system")
public class SystemProperties {
    /**
     * @apiNote 系统的唯一标识
     */
    private String flag;

    /**
     * @apiNote 是否开启使用
     */
    private Boolean enabled;

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
}
