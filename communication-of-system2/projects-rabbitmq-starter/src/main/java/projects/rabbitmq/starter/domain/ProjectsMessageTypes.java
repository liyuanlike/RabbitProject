package projects.rabbitmq.starter.domain;

/**
 * @apiNote  定义各个系统 整体上所有的消息类型 对应的值
 * @author liuxun
 */
public class ProjectsMessageTypes {
    public static final String WECHAT_EVENT_TYPE = "WECHAT_EVENT"; //公众号发送到接报系统的事件
    public static final String PREPOSITION_EVENT_TYPE = "PREPOSITION_EVENT"; // 部委前置发送到接报系统的事件
    public static final String GRIDMAN_EVENT_TYPE = "GRIDMAN_EVENT"; // 网格员发送到接报系统的事件

    public static final String COORDINATION_TYPE = "COORDINATION";  // 接报系统 发送到指挥系统的协调事件

    public static final String DIRECT_INSTRUCTION_TYPE = "DIRECT_INSTRUCTION"; // 指挥系统发送到 部委前置以及网格员的指令消息

    public static final String PREPOSITION_EXECUTION_TYPE = "PREPOSITION_EXECUTION"; // 部委前置发送到指挥的执行状况
    public static final String GRIDMAN_EXECUTION_TYPE = "GRIDMAN_EXECUTION";    // 网格员发送到指挥的执行状况

    public static final String PLAN_TYPE = "PLAN_DATA"; // 预案系统向指挥发送预案
    public static final String PROGRAMME_TYPE = "PROGRAM_DATA"; // 指挥系统向预案发送方案

}
