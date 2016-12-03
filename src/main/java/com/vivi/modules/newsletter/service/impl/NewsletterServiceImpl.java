/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.newsletter.service.impl;

import com.vivi.modules.newsletter.dao.NewsletterDAO;
import com.vivi.modules.newsletter.service.NewsletterService;

/**
 *
 * @author wewezhu
 */
public class NewsletterServiceImpl implements NewsletterService{
    private NewsletterDAO newsletterDAO;

    public NewsletterDAO getNewsletterDAO() {
        return newsletterDAO;
    }

    public void setNewsletterDAO(NewsletterDAO newsletterDAO) {
        this.newsletterDAO = newsletterDAO;
    }
    
    
}
