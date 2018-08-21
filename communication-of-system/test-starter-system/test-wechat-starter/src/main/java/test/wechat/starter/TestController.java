package test.wechat.starter;

import com.global.component.domain.WeChatEventMessage;
import com.wechat.rabbit.processors.WechatEventSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class TestController {
    @Autowired
    private WechatEventSender wechatEventSender;

    @GetMapping("/wc_send")
    public Object send(){
        WeChatEventMessage message = new WeChatEventMessage();
        message.setDescription("这是一条公众号发送的事件消息");
        message.setEventID(UUID.randomUUID().toString().replace("-",""));
        message.setOccurTime(new Date());
        message.setOpenId("AAABBBCCC");
        Boolean isSuccess = wechatEventSender.sendWeChatEvent(message);
        return isSuccess;
    }
}
