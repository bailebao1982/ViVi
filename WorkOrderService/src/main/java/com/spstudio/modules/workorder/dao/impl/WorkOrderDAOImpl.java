/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.dao.impl;

import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class WorkOrderDAOImpl implements WorkOrderDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
}
