package com.spstudio.wxserver.modules.member.handler;

import com.spstudio.wxserver.common.handler.AbstractHandler;
import com.spstudio.wxserver.common.handler.annotation.BtnHandler;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Soul on 2016/12/9.
 */
@Component
@BtnHandler(key="help")
public class HelpBtnHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMessage,
            Map<String, Object> context,
            WxMpService wxMpService,
            WxSessionManager sessionManager)
            throws WxErrorException {
        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT()
                .content("您点击了帮助按钮")
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
        logger.info("help btn Handler triggered" + m.getContent());
        return m;
    }
}