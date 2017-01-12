/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.vender.dao;

import com.spstudio.modules.vender.entity.Vender;

/**
 *
 * @author wewezhu
 */
public interface VenderDAO {
    public Vender findVenderById(String venderId);

    public Vender findVenderByNo(String venderNo);

    public Vender addNewVender(Vender vender);
    
    public Vender updateVender(Vender vender);
    
    public Vender deleteVender(Vender vender);
    
    public void zapVender(Vender vender);
    
    public Vender confirmVender(Vender vender);
}
