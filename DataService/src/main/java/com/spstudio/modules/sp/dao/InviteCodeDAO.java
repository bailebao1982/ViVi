package com.spstudio.modules.sp.dao;

import com.spstudio.modules.sp.entity.SPInviteCode;
import com.spstudio.modules.sp.entity.ServiceProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by Soul on 2017/3/12.
 */
public interface InviteCodeDAO {
    public List<String> getAllSPInviteCodes(ServiceProvider sp);

    public String getEffectiveInviteCode(ServiceProvider sp, Date effectiveDate);

    public SPInviteCode getInviteCodeEntity(String inviteCode);
}
