package batch.update.starter.config;

import java.util.HashSet;
import java.util.Set;

/**
 * @apiNote 批量更新依赖的全局配置信息类
 */
public class BatchUpdateGlobalInfo {

    /**
     * ***************** 定义批量更新依赖中统一的主题 *******************
     */
    public static final String BATCH_UPDATE_TOPIC = "BATCH_UPDATE_TOPIC";


    /**
     * ***************** 定义routekey、binding、queueName 的统一后缀 *******************
     * @apiNote routekey = binding =${系统标志}+${suffix}
     */

    /**
     * @apiNote 所有的路由键以及绑定规则的统一后缀
     */
    private static final String INSTRUCTION_DATA_SUFFIX = ".INSTRUCTION.DATA";

    /**
     * @apiNote 所有队列名称的统一后缀
     */
    private static final String QUEUE_SUFFIX = "_QUEUE";


    /**
     * @apiNote 各个系统的标识符
     */
    public static final String PREPOSITION_FLAG = "PREPOSITION"; // 前置系统
    public static final String WECHAT_FLAG = "WECHAT"; // 公众号系统
    public static final String GRIDMAN_FLAG = "GRIDMAN"; // 网格员
    public static final String REPORTING_FLAG = "REPORTING"; // 接报
    public static final String DIRECT_FLAG = "DIRECT"; // 指挥

    public static Set<String> flags = new HashSet<>();
    static {
        flags.add(PREPOSITION_FLAG);
        flags.add(WECHAT_FLAG);
        flags.add(GRIDMAN_FLAG);
        flags.add(REPORTING_FLAG);
        flags.add(DIRECT_FLAG);
    }


    // 内部使用 获取自身系统路由键
    public static String getRouteKey(String flag){
        if (flag != null && flag.contains(flag)){
            return flag + INSTRUCTION_DATA_SUFFIX;
        }
        return null;
    }

    // 内部使用 获取自身系统路由绑定
    protected static String getBinding(String flag){
        return getRouteKey(flag);
    }

    // 内部使用 获取自身系统接收消息的队列名称
    protected static String getQueueName(String flag){
        if (flag != null && flags.contains(flag)){
            return flag+QUEUE_SUFFIX;
        }
        return null;
    }
}
