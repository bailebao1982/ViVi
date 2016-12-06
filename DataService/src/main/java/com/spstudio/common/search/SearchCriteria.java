/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.search;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wewezhu
 */
public class SearchCriteria {
    
    Map<String,SearchCriteriaItem> itemMap; 
    
    public SearchCriteria(){
        itemMap = new HashMap<String,SearchCriteriaItem>();
    }

    public Map<String, SearchCriteriaItem> getItemMap() {
        return itemMap;
    }

    public void addSearchCriterialItem(String key,SearchCriteriaItem item){
        if(!itemMap.containsKey(key)){
            itemMap.put(key, item);
        }
        //else
        // Add exception logic.
    }
}
