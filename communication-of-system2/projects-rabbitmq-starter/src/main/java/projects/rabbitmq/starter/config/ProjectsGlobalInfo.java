package projects.rabbitmq.starter.config;

import projects.rabbitmq.starter.domain.ProjectsFlags;

/**
 * @apiNote 定义全局的配置信息
 * @author liuxun
 */
public class ProjectsGlobalInfo {
    /**
     * @apiNote 为所有的系统 封装统一的 topic
     */
    public static final String PROJECTS_TOPIC = "PROJECTS_TOPIC";

    /**
     * @apiNote 定义拼接的前缀信息 内部使用
     */
    private static final String EVENT_SUFFIX = ".EVENT";
    private static final String COORDINATION_SUFFIX = ".COORDINATION";
    private static final String EXECUTION_SUFFIX = ".EXECUTION";
    private static final String INSTRUCTION_SUFFIX = ".INSTRUCTION";

    private static final String PLAN_SUFFIX = ".PLAN";   // 预案后缀标识
    private static final String PROGRAMME_SUFFIX = ".PROGRAMME"; // 方案后缀标识

    /**
     * @apiNote 根据系统标志返回绑定标识
     * @param flag
     * @return
     */
    public static String getBinding(String flag){

        switch (flag){
            case ProjectsFlags.WECHAT_FLAG:
                return null;
            case ProjectsFlags.REPORTING_FLAG:
                return "#"+EVENT_SUFFIX;
            case ProjectsFlags.DIRECT_FLAG:
                String coordinationBinding = "#" + COORDINATION_SUFFIX;
                String executionBinding = "#" + EXECUTION_SUFFIX;
                String planBinding = "#" + PLAN_SUFFIX;
                return coordinationBinding+"-"+executionBinding+"-"+planBinding;
            case ProjectsFlags.GRIDMAN_FLAG:
                return flag + INSTRUCTION_SUFFIX;
            case ProjectsFlags.PREPOSITION_FLAG:
                return flag +INSTRUCTION_SUFFIX;
            case ProjectsFlags.PLAN_FLAG:
                return "#"+ PROGRAMME_SUFFIX;
        }
        return null;
    }

    /**
     * @apiNote 根据系统标识返回路由键
     * @param flag
     * @return
     */
    public static String getRouteKey(String flag){
        switch (flag){
            case ProjectsFlags.WECHAT_FLAG:
                return flag + EVENT_SUFFIX;
            case ProjectsFlags.REPORTING_FLAG:
                return flag + COORDINATION_SUFFIX;
            case ProjectsFlags.DIRECT_FLAG:
                String preposition_routeKey = getBinding(ProjectsFlags.PREPOSITION_FLAG);
                String gridman_routeKey = getBinding(ProjectsFlags.GRIDMAN_FLAG);
                String plan_routeKey = flag + PROGRAMME_SUFFIX;
                return preposition_routeKey+"-"+gridman_routeKey+"-"+plan_routeKey;
            case ProjectsFlags.GRIDMAN_FLAG:
                String gridmanEventRouteKey = flag + EVENT_SUFFIX;
                String gridmanExecutionRouteKey = flag + EXECUTION_SUFFIX;
                return gridmanEventRouteKey + "-" +gridmanExecutionRouteKey;
            case ProjectsFlags.PREPOSITION_FLAG:
                String prePositionEventRouteKey = flag + EVENT_SUFFIX;
                String prePositionExecutionRouteKey = flag + EXECUTION_SUFFIX;
                return prePositionEventRouteKey+ "-" + prePositionExecutionRouteKey;
            case ProjectsFlags.PLAN_FLAG:
                return flag + PLAN_SUFFIX;
        }
        return null;
    }

    /**
     * @apiNote  根据系统标志获取对应的 接收消息队列名称
     * @param flag 系统唯一标识
     * @return
     */
    public static String getQueueName(String flag){
        switch (flag){
            case ProjectsFlags.WECHAT_FLAG:
                return null;
            case ProjectsFlags.REPORTING_FLAG:
                return flag + "_ACCEPT_EVENT_QUEUE" ;
            case ProjectsFlags.DIRECT_FLAG:
                String accept_coordination_queue = flag + "_ACCEPT_COORDINATION_QUEUE";
                String accept_execution_queue = flag + "_ACCEPT_EXECUTION_QUEUE";
                String accept_plan_queue = flag + "_ACCEPT_PLAN_QUEUE";
                return accept_coordination_queue +"-"+ accept_execution_queue +"-"+ accept_plan_queue;
            case ProjectsFlags.PREPOSITION_FLAG:
                return flag + "_ACCEPT_INSTRUCTION_QUEUE";
            case ProjectsFlags.GRIDMAN_FLAG:
                return flag + "_ACCEPT_INSTRUCTION_QUEUE";
            case ProjectsFlags.PLAN_FLAG:
                return flag + "_ACCEPT_PROGRAMME_QUEUE";
        }
        return null;

    }

//    public static void main(String[] args){
//        System.out.println(getRouteKey(ProjectsFlags.DIRECT_FLAG));
//    }
}
