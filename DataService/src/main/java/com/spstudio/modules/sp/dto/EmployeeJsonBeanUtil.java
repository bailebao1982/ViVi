package com.spstudio.modules.sp.dto;

import com.spstudio.common.image.ImageUtils;
import com.spstudio.common.utils.StringUtils;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.entity.ServiceProviderType;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.vender.entity.Vender;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Soul on 2017/1/12.
 */
public class EmployeeJsonBeanUtil {

    public static EmployeeJsonBean toJsonBean(ServiceProvider spBean){
        EmployeeJsonBean employeeJsonBean = new EmployeeJsonBean();

        employeeJsonBean.setEmployee_id(spBean.getServiceProviderId());
        employeeJsonBean.setEmployee_name(spBean.getSpName());
        employeeJsonBean.setEmployee_no(spBean.getSpWorkNo());
        employeeJsonBean.setEmployee_note(spBean.getSpNote());
        employeeJsonBean.setEmployee_sex(spBean.getSpSex());
        employeeJsonBean.setEmployee_telphone(spBean.getSpTelphone());
        employeeJsonBean.setEmployee_type(spBean.getSpType().getServiceProviderTypeName());
        employeeJsonBean.setEmployee_type_description(spBean.getSpType().getDescription());
        employeeJsonBean.setEmployee_birthday(spBean.getSpBirthDay().toString());
        employeeJsonBean.setEmployee_address(spBean.getSpAddress());

        byte[] profilePics = spBean.getProfilePicture();
        if(profilePics!= null && profilePics.length > 0) {
            employeeJsonBean.setEmployee_profilePic(ImageUtils.byteToString(profilePics));
        }
        return employeeJsonBean;
    }

    public static ServiceProvider toEntityBean(EmployeeJsonBean employeeJsonBean, Vender vender, ServiceProviderService service){

        ServiceProvider spBean = new ServiceProvider();
        spBean.setSpName(employeeJsonBean.getEmployee_name());
        spBean.setSpNote(employeeJsonBean.getEmployee_note());
        spBean.setSpAddress(employeeJsonBean.getEmployee_address());
        String profilePic = employeeJsonBean.getEmployee_profilePic();
        if(profilePic != null && !profilePic.isEmpty())
            spBean.setProfilePicture(ImageUtils.stringToByte(profilePic));

        Date employeeBirthday = null;
        try {
            employeeBirthday = StringUtils.str2Date(
                    employeeJsonBean.getEmployee_birthday()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spBean.setSpBirthDay(employeeBirthday);

        spBean.setSpSex(employeeJsonBean.getEmployee_sex());
        spBean.setSpTelphone(employeeJsonBean.getEmployee_telphone());
        spBean.setSpWorkNo(employeeJsonBean.getEmployee_no());

        ServiceProviderType type = service.findServiceProviderTypeByType(employeeJsonBean.getEmployee_type());
        spBean.setSpType(type);

        Set<Vender> venders = new HashSet<>();
        venders.add(vender);
        spBean.setVenders(venders);

        return spBean;
    }
}
