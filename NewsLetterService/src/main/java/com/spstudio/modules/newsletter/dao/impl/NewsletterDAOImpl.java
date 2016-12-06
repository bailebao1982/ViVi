/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.newsletter.dao.impl;

import com.spstudio.modules.newsletter.dao.NewsletterDAO;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class NewsletterDAOImpl implements NewsletterDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
}
