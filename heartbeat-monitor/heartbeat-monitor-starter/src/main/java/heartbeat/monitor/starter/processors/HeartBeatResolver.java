package heartbeat.monitor.starter.processors;

import heartbeat.monitor.starter.config.MonitorGlobalInfo;
import heartbeat.monitor.starter.domain.Health;
import heartbeat.monitor.starter.domain.InstanceItem;
import heartbeat.monitor.starter.domain.Metrics;
import heartbeat.monitor.starter.domain.ServiceApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @apiNote 为监控系统封装 健康信息获取类
 */
@Component
@ConditionalOnProperty(prefix = "projects.system", value = "flag", havingValue = MonitorGlobalInfo.MONITOR_FLAG)
public class HeartBeatResolver {
    public Health getHealthInfo() {
        ConcurrentHashMap<String, Metrics> preInfos = MonitorGlobalInfo.PRE_INFOS;
        ConcurrentHashMap<String, Metrics> notPreInfos = MonitorGlobalInfo.NOT_PRE_INFOS;
        Health health = new Health();
        health.setPreApps(new ArrayList<>());
        health.setOtherApps(new ArrayList<>());
        List<ServiceApp> preApps = health.getPreApps();
        List<ServiceApp> otherApps = health.getOtherApps();

        Map<String, ServiceApp> preAppsMap = new HashMap<>(); // entry<id,app>
        Map<String, ServiceApp> otherAppsMap = new HashMap<>(); // entry<flag,app>

        long minTime = System.currentTimeMillis() - 15 * 1000; // 统计周期是15秒
        for (Entry<String, Metrics> entry : preInfos.entrySet()) {
            final String key = entry.getKey();
            final Metrics metrics = entry.getValue();
            final String id = key.split("_")[1];
            if (!preAppsMap.keySet().contains(id)) {
                ServiceApp serviceApp = new ServiceApp();
                serviceApp.setFlag(metrics.getFlag());
                serviceApp.setId(metrics.getId());
                serviceApp.setServiceName(metrics.getName());
                serviceApp.setInstances(new HashSet<>());
                InstanceItem item = new InstanceItem();
                setItem(metrics, item, minTime);
                serviceApp.getInstances().add(item);
                preAppsMap.put(id, serviceApp);
            } else {
                final ServiceApp serviceApp = preAppsMap.get(id);
                serviceApp.setServiceName(metrics.getName());
                InstanceItem item = new InstanceItem();
                setItem(metrics, item, minTime);
                serviceApp.getInstances().add(item);
            }
        }

        preApps.addAll(preAppsMap.values());

        for (Entry<String, Metrics> entry : notPreInfos.entrySet()) {
            final String key = entry.getKey();
            final Metrics metrics = entry.getValue();
            final String flag = key.split("_")[0]; // 判断依据在此是 flag

            if (!otherAppsMap.keySet().contains(flag)) {
                ServiceApp serviceApp = new ServiceApp();
                serviceApp.setFlag(metrics.getFlag());
                serviceApp.setServiceName(metrics.getName());
                serviceApp.setInstances(new HashSet<>());
                InstanceItem item = new InstanceItem();
                setItem(metrics, item, minTime);
                serviceApp.getInstances().add(item);
                otherAppsMap.put(flag, serviceApp);
            } else {
                final ServiceApp otherServiceApp = otherAppsMap.get(flag);
                otherServiceApp.setServiceName(metrics.getName());
                InstanceItem otherItem = new InstanceItem();
                setItem(metrics, otherItem, minTime);
                otherServiceApp.getInstances().add(otherItem);
            }
        }

        otherApps.addAll(otherAppsMap.values());
        return health;
    }

    private void setItem(Metrics metrics, InstanceItem item, long minTime) {
        item.setIp(metrics.getIp());
        if (metrics.getTimestamp() < minTime) { // 出现故障
            item.setLevel(2); // 服务器出现故障
        } else {
            if (metrics.getMemPercent() > 0.9) {
                item.setLevel(1); // 预警
            } else {
                item.setLevel(0); // 正常运行
            }
        }
    }
}
