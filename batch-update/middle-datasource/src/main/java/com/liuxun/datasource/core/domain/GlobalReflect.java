package com.liuxun.datasource.core.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiNote 全局映射关系 包括处理指令的系统引入此组件后 能处理那些指令
 * @author liuxun
 */
public class GlobalReflect {
    /**
     * @apiNote 操作ID 与 任务信息相关的映射
     */
    public static final Map<String,InstructTask> operationIDToInstructTaskMap = new HashMap<>();

    /**
     * @apiNote routekey与操作ID之间的映射
     */
    public static final Map<String, List<String>> resolveKeyOperationIdsBindingsMap = new HashMap<>();
    static {
        String operationID1 = "query_user";
        String operationID2 = "query_orders_100";
        String operationID3 = "query_orders_all";
        final InstructTask instructTask1 = new InstructTask(operationID1, "select * from user");
        final InstructTask instructTask2 = new InstructTask(operationID2, "select * from orders limit 0,100");
        final InstructTask instructTask3 = new InstructTask(operationID3, "select * from orders");
        operationIDToInstructTaskMap.put(operationID1,instructTask1);
        operationIDToInstructTaskMap.put(operationID2,instructTask2);
        operationIDToInstructTaskMap.put(operationID3,instructTask3);

         List<String> operationIDs1 = new ArrayList<>();
         List<String> operationIDs2 = new ArrayList<>();
        operationIDs1.add(operationID1);
        operationIDs2.add(operationID2);
        operationIDs2.add(operationID3);

        String  resolveRoutekey1 = "system1";
        String  resolveRoutekey2 = "system2";

        resolveKeyOperationIdsBindingsMap.put(resolveRoutekey1,operationIDs1);
        resolveKeyOperationIdsBindingsMap.put(resolveRoutekey2,operationIDs2);
    }
}
