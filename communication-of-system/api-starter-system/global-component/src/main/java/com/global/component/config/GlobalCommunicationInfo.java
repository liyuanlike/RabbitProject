package com.global.component.config;

/**
 * @apiNote 存放永远不会修改或值发生改变的信息
 * @author liuxun
 */
public class GlobalCommunicationInfo {
    /**
     * @apiNote 配置接报、公众号、前置、网格以及智慧系统的之间统一的交换机主题
     */
    public static final String COMMUNICATION_TOPIC = "COMMUNICATION_TOPIC";

    //*********************************************** MODE ************************************************
    /**
     * @apiNote 部委前置系统标志
     * 命名规范: {系统类型}_MODE
     */
    public static final String PREPOSITION_MODE = "PREPOSITION";

    /**
     * @apiNote 公众号系统标志
     */
    public static final String WECHAT_MODE = "WECHAT";

    /**
     * @apiNote 网格员系统标志
     */
    public static final String GRIDMAN_MODE = "GRIDMAN";

    /**
     * @apiNote 接报系统标志
     */
    public static final String REPORTING_MODE = "REPORTING";

    /**
     * @apiNote 指挥系统标志
     */
    public static final String DIRECT_MODE = "DIRECT";

    //*********************************************** SUFFIX ************************************************

    /**
     * @apiNote 对于接报系统 所接受的事件的路由键的统一后缀（协调事件除外）
     * 命名规范：{业务类型}_SUFFIX
     */
    private static final String EVENT_SUFFIX = ".EVENT";

    /**
     * @apiNote 协调事件路由键的后缀
     */
    private static final String COORDINATION_SUFFIX = ".COORDINATION";

    /**
     * @apiNote 所有指令路由键的后缀
     */
    private static final String INSTRUCTION_SUFFIX = ".INSTRUCTION";

    /**
     * @apiNote 所有执行状况路由键的后缀
     */
    private static final String EXECUTION_STATUS_SUFFIX = ".EXECUTION";


    //*********************************************** ROUTEKEY **********************************************
    //********************************** ROUTEKEY = MODE + SUFFIX  ******************************************
    // 命名规范: {发起方(系统)}_{业务类型}_ROUTEKEY
    /**
     * @apiNote 前置系统发送事件的路由键
     */
    public static final String PREPOSITION_EVENT_ROUTEKEY = PREPOSITION_MODE + EVENT_SUFFIX ;

    /**
     * @apiNote 公众号系统发送建议事件的路由键
     */
    public static final String WECHAT_EVENT_ROUTEKEY = WECHAT_MODE + EVENT_SUFFIX;

    /**
     * @apiNote 网格员发送事件的路由键
     */
    public static final String GRIDMAN_EVENT_ROUTEKEY = GRIDMAN_MODE + EVENT_SUFFIX;

    /**
     * @apiNote 接报系统发送协调事件的路由键
     */
    public static final String REPORTING_COORDINATION_ROUTEKEY = REPORTING_MODE + COORDINATION_SUFFIX;

    /**
     * @apiNote 指挥系统向部委前置系统发送指令的路由键
     */
    public static final String DIRECT_TO_PREPOSITION_INSTRUCTION_ROUTEKEY = DIRECT_MODE + INSTRUCTION_SUFFIX;

    /**
     * @apiNote 指挥系统向网格员系统发送指令的路由键
     */
    public static final String DIRECT_TO_GRIDMAN_INSTRUCTION_ROUTEKEY = DIRECT_MODE + INSTRUCTION_SUFFIX;

    /**
     * @apiNote 部委前置系统向指挥系统发送执行状况的路由键
     */
    public static final String PREPOSITION_TO_DIRECT_EXECUTION_ROUTEKEY = PREPOSITION_MODE + EXECUTION_STATUS_SUFFIX;

    /**
     * @apiNote 网格员系统向指挥系统发送执行状况的路由键
     */
    public static final String GRIDMAN_TO_DIRECT_EXECUTION_ROUTEKEY = GRIDMAN_MODE + EXECUTION_STATUS_SUFFIX;



    //******************************************** BINDING    **********************************************
    //********************************** BINDING = '#.' + SUFFIX  ******************************************
    // 命名规范：{接收消息的系统}_ACCEPT_{业务逻辑类型}_BINDING

    /**
     * @apiNote 接报系统接收事件消息的 交换机绑定标识
     */
    public static final String REPORTING_ACCEPT_EVENT_BINDING = "#" + EVENT_SUFFIX;

    /**
     * @apiNote 指挥系统接收协调事件的 交换机绑定标识
     * 点对点
     */
    public static final String DIRECT_ACCEPT_COORDINATION_BINDING = REPORTING_COORDINATION_ROUTEKEY;

    /**
     * @apiNote 部委前置系统接收指令的 交换机绑定标识
     * 点对点
     */
    public static final String PREPOSITION_ACCEPT_INSTRUCTION_BINDING = DIRECT_TO_PREPOSITION_INSTRUCTION_ROUTEKEY;

    /**
     * @apiNote 网格员系统接收指令的 交换机绑定标识
     * 点对点
     */
    public static final String  GRIDMAN_ACCEPT_INSTRUCTION_BINDING = DIRECT_TO_GRIDMAN_INSTRUCTION_ROUTEKEY;

    /**
     * @apiNote 指挥系统接收部委前置以及网格员系统执行状况的 交换机绑定标识
     */
    public static final String DIRECT_ACCEPT_EXECUTION_BINDING = "#" + EXECUTION_STATUS_SUFFIX;

    //******************************************** QUEUE NAMES    **********************************************
    private static final String QUEUE_SUFFIX = "_QUEUE";  // 所有队列名称的统一后缀

    /**
     * @apiNote 接报系统获取 部委前置系统、公众号、网格员 等系统发送事件消息的队列名称
     */
    public static final String REPORTING_ACCEPT_EVENT_QUEUE = REPORTING_MODE + "_ACCEPT_EVENT" +QUEUE_SUFFIX;

    /**
     * @apiNote 指挥系统获取 接报系统发送协调事件的 队列名称
     */
    public static final String DIRECT_ACCEPT_COORDINATION_QUEUE = DIRECT_MODE + "_ACCEPT_COORDINATION" + QUEUE_SUFFIX;

    /**
     * @apiNote 指挥系统获取部委前置以及网格员系统发送过来的执行状况的队列名称
     */
    public static final String DIRECT_ACCEPT_EXECUTION_QUEUE = DIRECT_MODE + "_ACCEPT_EXECUTION" +QUEUE_SUFFIX;


    /**
     * @apiNote 部委前置系统获取 指挥系统发送指令的队列名称
     */
    public static final String PREPOSITION_ACCEPT_INSTRUCTION_QUEUE = PREPOSITION_MODE + "_ACCEPT_INSTRUCTION" + QUEUE_SUFFIX;

    /**
     * @apiNote 网格员系统获取 指挥系统发送指令的队列名称
     */
    public static final String GRIDMAN_ACCEPT_INSRUCTION_QUEUE = GRIDMAN_MODE + "_ACCEPT_INSTRUCTION" +QUEUE_SUFFIX;

}
