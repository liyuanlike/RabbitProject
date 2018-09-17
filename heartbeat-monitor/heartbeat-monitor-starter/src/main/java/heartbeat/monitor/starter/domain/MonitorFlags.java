package heartbeat.monitor.starter.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @apiNote 定义每个系统的唯一固定标识
 */
public class MonitorFlags {
    /**
     * @apiNote 各个系统的标识符
     */
    public static final String PREPOSITION_FLAG = "PREPOSITION"; // 前置系统
    public static final String WECHAT_FLAG = "WECHAT"; // 公众号系统
    public static final String GRIDMAN_FLAG = "GRIDMAN"; // 网格员
    public static final String REPORTING_FLAG = "REPORTING"; // 接报
    public static final String DIRECT_FLAG = "DIRECT"; // 指挥
    public static final String PLAN_FLAG = "PLAN"; // 预案

}
