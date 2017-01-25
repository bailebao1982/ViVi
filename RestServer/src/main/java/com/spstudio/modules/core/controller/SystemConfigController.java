package com.spstudio.modules.core.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.service.SystemConfigService;
import com.spstudio.common.service.entity.SystemConfig;
import com.spstudio.modules.core.bean.SysConfigJsonBean;
import com.spstudio.modules.core.bean.SysConfigJsonBeanUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/23.
 */
@RestController
@RequestMapping("/sysconfig")
public class SystemConfigController {
    @Resource(name="configService")
    private SystemConfigService configService;

    @RequestMapping(value = "/list_config",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody
    ResponseBean listConfigs(@RequestParam(value="module", required=true) String config_module){
        List<SystemConfig> configs = configService.findModuleConfigs(config_module);
        List<SysConfigJsonBean> sysConfigJsonBeenList = new ArrayList<SysConfigJsonBean>();
        for (SystemConfig sysCfg : configs){
            sysConfigJsonBeenList.add(SysConfigJsonBeanUtil.toJsonBean(sysCfg));
        }
        return ResponseMsgBeanFactory.getResponseBean(
                true,
                sysConfigJsonBeenList
        );
    }

    @RequestMapping(value = "/update_config",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody
    ResponseBean updateConfig(@RequestBody SysConfigJsonBean sysConfigJson){
        SystemConfig sysConfig = SysConfigJsonBeanUtil.toEntityBean(sysConfigJson);
        List<SystemConfig> targetCfgs = configService.findModuleConfig(sysConfig.getConfigModule(),
                                             sysConfig.getConfigKey());
        boolean updated = false;
        for (SystemConfig cfg: targetCfgs){
            if(cfg.getConfigCondition().equalsIgnoreCase(
                    sysConfigJson.getConfig_condition()
            )){
                cfg.setConfigValue(sysConfigJson.getConfig_value());
                configService.updateConfig(cfg);
                updated = true;
                break;
            }
        }

        if(updated){
            return ResponseMsgBeanFactory.getSuccessResponseBean("系统配置修改成功!");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "0002",
                    "系统配置修改失败!"
            );
        }
    }
}
