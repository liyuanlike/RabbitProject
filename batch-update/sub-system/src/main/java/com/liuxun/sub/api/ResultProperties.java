package com.liuxun.sub.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("instruct.result")
public class ResultProperties {
    private String routeKey;
    private Boolean enabled;

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
