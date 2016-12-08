package com.spstudio.wxserver.modules.core.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by FirenzesEagle on 2016/5/30 0030.
 * Email:liumingbo2008@gmail.com
 * 
 * @author FirenzesEagle
 * @author BinaryWang
 */
@Configuration
public class MainConfig {

    @Value("#{wxProperties.appid}")
    //private String appid = "wxc0fd9420c747f228";
    private String appid;

    @Value("#{wxProperties.appsecret}")
    //private String appsecret = "83a37bedd3f28b0e33ec06ae624f65fd";
    private String appsecret;

    @Value("#{wxProperties.token}")
    //private String token = "spstudiowxservertoken19821989";
    private String token;

    @Value("#{wxProperties.aeskey}")
    //private String aesKey = "98912891nekotrevresxwoidutsps98912891nekotr";
    private String aesKey;

    @Value("#{wxProperties.partener_id}")
    //private String partenerId = "111111";
    private String partenerId;

    @Value("#{wxProperties.partener_key}")
    //private String partenerKey = "111111";
    private String partenerKey;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.appid);
        configStorage.setSecret(this.appsecret);
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        configStorage.setPartnerId(this.partenerId);
        configStorage.setPartnerKey(this.partenerKey);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

}
