/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.vender.dao.impl;

import com.spstudio.modules.vender.dao.VenderDAO;
import com.spstudio.modules.vender.entity.Vender;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class VenderDAOImpl implements VenderDAO{

     private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public Vender addNewVender(Vender vender) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(vender);
        return vender;
    }

    @Override
    public Vender updateVender(Vender vender) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(vender);
        return vender;
    }

    @Override
    public Vender deleteVender(Vender vender) {
       vender.setDeleteFlag(1);
       this.sessionFactory.getCurrentSession().update(vender);
       return vender;
    }

    @Override
    public void zapVender(Vender vender) {
       this.sessionFactory.getCurrentSession().delete(vender);
    }

    @Override
    public Vender confirmVender(Vender vender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
