package com.global.component.domain;

/**
 * @apiNote 微信公众号向接报系统发送的事件信息
 */
public class WeChatEventMessage extends EventMessage{
    /**
     * @apiNote 关注微信公众号消息发送者的唯一标识
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
