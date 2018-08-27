package projects.rabbitmq.starter.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @apiNote 定义自定义的属性
 */

@ConfigurationProperties("projects.system")
public class ProjectsProperties {
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
}
