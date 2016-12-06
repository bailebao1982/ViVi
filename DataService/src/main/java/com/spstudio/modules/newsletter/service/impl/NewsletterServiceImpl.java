/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.newsletter.service.impl;

import com.spstudio.modules.newsletter.service.NewsletterService;
import com.spstudio.modules.newsletter.dao.NewsletterDAO;

/**
 *
 * @author wewezhu
 */
public class NewsletterServiceImpl implements NewsletterService {
    private NewsletterDAO newsletterDAO;

    public NewsletterDAO getNewsletterDAO() {
        return newsletterDAO;
    }

    public void setNewsletterDAO(NewsletterDAO newsletterDAO) {
        this.newsletterDAO = newsletterDAO;
    }
    
    
}
