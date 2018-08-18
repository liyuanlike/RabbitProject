package com.liuxun.datasource.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @apiNote 对需要引入组件的系统 必须配置的属性进行封装
 */

@ConfigurationProperties("instruct.resolve")
public class ResolveInstructPropertity {
    private String routeKey;

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
}
