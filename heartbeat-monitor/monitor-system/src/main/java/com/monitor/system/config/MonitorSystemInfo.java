package com.monitor.system.config;

import heartbeat.monitor.starter.domain.UserCount;

import java.util.concurrent.ConcurrentHashMap;

public class MonitorSystemInfo {
    /**
     * @apiNote 统一的在线离线 用户数信息
     */
    public static ConcurrentHashMap<String, UserCount> IN_OUT_LINE_USER_MAP;
}
