package com.monitor.system.config;

import com.alibaba.fastjson.JSON;
import com.monitor.system.entity.CommonHeartBeat;
import com.monitor.system.repository.GeneralService;
import heartbeat.monitor.starter.config.MonitorGlobalInfo;
import heartbeat.monitor.starter.domain.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuxun
 * @apiNote 启动之前做一些操作
 */
@Component
public class StartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private GeneralService generalService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("<<<<<<<<<<<< 启动初始化操作开始 <<<<<<<<<<<<<<<");
        CommonHeartBeat heartBeat = (CommonHeartBeat) generalService.findById(CommonHeartBeat.class, 1L);
        if (heartBeat != null) {
            ConcurrentHashMap preMap= JSON.parseObject(heartBeat.getPreInfos(), ConcurrentHashMap.class);
            ConcurrentHashMap notPreMap = JSON.parseObject(heartBeat.getNotPreInfos(), ConcurrentHashMap.class);
            for(Object key:preMap.keySet()){
                preMap.put(key,JSON.parseObject(JSON.toJSONString(preMap.get(key)),Metrics.class));
            }
            for(Object key:notPreMap.keySet()){
                notPreMap.put(key,JSON.parseObject(JSON.toJSONString(notPreMap.get(key)),Metrics.class));
            }
            MonitorGlobalInfo.PRE_INFOS = preMap;
            MonitorGlobalInfo.NOT_PRE_INFOS = notPreMap;
        }
        logger.info("<<<<<<<<<<<< 启动初始化操作结束 <<<<<<<<<<<<<<<");
    }

    @PreDestroy
    public void destory() {
        logger.info("<<<<<<<<<<<< 关闭时操作开始 <<<<<<<<<<<<<<<");
        CommonHeartBeat heartBeat = (CommonHeartBeat) generalService.findById(CommonHeartBeat.class, 1L);
        if (heartBeat == null) {
            heartBeat = new CommonHeartBeat();
        }
        heartBeat.setId(1L);
        heartBeat.setPreInfos(JSON.toJSONString(MonitorGlobalInfo.PRE_INFOS));
        heartBeat.setNotPreInfos(JSON.toJSONString(MonitorGlobalInfo.NOT_PRE_INFOS));
        generalService.merge(heartBeat);
        logger.info("<<<<<<<<<<<< 关闭时操作结束 <<<<<<<<<<<<<<<");
    }
}
