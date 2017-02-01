package com.spstudio.modules.member.bean;

import com.spstudio.common.service.entity.SystemConfig;
import com.spstudio.modules.member.config.Configuration;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.MemberService;

/**
 * Created by Soul on 2017/1/20.
 */
public class DepositMemberTypeBeanUtil {
    public static DepositMemberTypeBean toBean(SystemConfig config, MemberService service){
        DepositMemberTypeBean typeBean = new DepositMemberTypeBean();

        String entityName = config.getConfigEntity();

        if(entityName.equalsIgnoreCase(Configuration.ENTITY_MEMBER_TYPE)){
            String deposit = config.getConfigCondition();

            try{
                typeBean.setDeposit(Integer.valueOf(deposit));
            }catch (NumberFormatException ex){
                ex.printStackTrace();
                return null;
            }

            String memberTypeId = config.getConfigValue();
            MemberType memberType = service.findMemberTypeById(memberTypeId);
            typeBean.setMemberType(memberType);
        }
        return null;
    }

    public static SystemConfig toEntity(DepositMemberTypeBean bean){
        SystemConfig config = new SystemConfig();

        config.setConfigModule(Configuration.MEMBER_MODULE_NAME);
        config.setConfigEntity(Configuration.ENTITY_MEMBER_TYPE);
        config.setConfigName(Configuration.CONFIG_DEPOST_MEMBERTYPE);

        config.setConfigValue(bean.getMemberType().getMemberTypeId());
        config.setConfigCondition(String.valueOf(bean.getDeposit()));
        return config;
    }
}
