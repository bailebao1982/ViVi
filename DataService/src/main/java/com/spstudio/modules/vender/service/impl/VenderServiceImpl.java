/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.vender.service.impl;

import com.spstudio.modules.vender.dao.VenderDAO;
import com.spstudio.modules.vender.entity.Vender;
import com.spstudio.modules.vender.service.VenderService;

/**
 *
 * @author wewezhu
 */
public class VenderServiceImpl implements VenderService{

    VenderDAO venderDAO;

    public VenderDAO getVenderDAO() {
        return venderDAO;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    @Override
    public Vender findVenderById(String venderId) {
        return venderDAO.findVenderById(venderId);
    }

    @Override
    public Vender findVenderByNo(String venderNo) {
        return venderDAO.findVenderByNo(venderNo);
    }

    @Override
    public Vender addNewVender(Vender vender) {
        return venderDAO.addNewVender(vender);
    }

    @Override
    public Vender updateVender(Vender vender) {
        return venderDAO.updateVender(vender);
    }

    @Override
    public Vender deleteVender(Vender vender) {
        return venderDAO.deleteVender(vender);
    }

    @Override
    public void zapVender(Vender vender) {
        venderDAO.zapVender(vender);
    }

    @Override
    public Vender confirmVender(Vender vender) {
        return venderDAO.confirmVender(vender);
    }
    
}
