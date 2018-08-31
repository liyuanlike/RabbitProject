package projects.rabbitmq.starter.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @apiNote 定义各个交互系统 消息需要携带的头信息
 */
public class ProjectsHeaders {
    /**
     * @apiNote 消息的发起方
     */
    public static final String  SOURCE = "SOURCE";
    /**
     * @apiNote 消息的接收方
     */
    public static final String  DESTINATION = "DESTINATION";

    /**
     * @apiNote 消息的类型
     */
    public static final String  CONTENT_TYPE = "CONTENT_TYPE";

    /**
     * @apiNote 消息体的对象类型
     */
    public static final String  OBJECT_TYPE = "OBJECT_TYPE";


    /**
     * @apiNote 消息的数据类型 例如DATA和INSTRUCTION
     */
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";

    /**
     * @apiNote 专门为自定义Headers Exchanger的部委系统提供的
     */
    public static final String SOURCE_ID = "SOURCE_ID";

    public static final Set<String> BUSINESS_KEYS = new HashSet<>();

    static {
        BUSINESS_KEYS.add(SOURCE);
        BUSINESS_KEYS.add(DESTINATION);
        BUSINESS_KEYS.add(CONTENT_TYPE);
        BUSINESS_KEYS.add(OBJECT_TYPE);
        BUSINESS_KEYS.add(MESSAGE_TYPE);
    }

}
