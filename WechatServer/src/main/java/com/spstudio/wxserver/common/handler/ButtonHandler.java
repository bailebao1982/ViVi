package com.spstudio.wxserver.common.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Soul on 2016/12/7.
 */
@Component
public class ButtonHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMpXmlMessage,
            Map<String, Object> map,
            WxMpService wxMpService,
            WxSessionManager wxSessionManager) throws WxErrorException {

        if(wxMpXmlMessage.getEventKey().equalsIgnoreCase("help")){
            WxMpXmlOutTextMessage m
                    = WxMpXmlOutMessage.TEXT()
                    .content("您点击了帮助按钮")
                    .fromUser(wxMpXmlMessage.getToUser())
                    .toUser(wxMpXmlMessage.getFromUser())
                    .build();
            logger.info("buttonMessageHandler" + m.getContent());
            return m;
        }else{
            WxMpXmlOutTextMessage m
                    = WxMpXmlOutMessage.TEXT()
                    .content("不知道您点击了什么按钮")
                    .fromUser(wxMpXmlMessage.getToUser())
                    .toUser(wxMpXmlMessage.getFromUser())
                    .build();
            logger.info("buttonMessageHandler" + m.getContent());
            return m;
        }
    }
}
