/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.search;

/**
 *
 * @author wewezhu
 */
public class SearchCriteriaItem {
    String leftItem;
    
    String rightItem;
    
    SearchConditionEnum sc;

    public SearchCriteriaItem(){
        
    }
    
    public SearchCriteriaItem(String leftItem,String rightItem,SearchConditionEnum sc){
        this.leftItem = leftItem;
        this.rightItem = rightItem;
        this.sc = sc;
}
    
    public String getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(String leftItem) {
        this.leftItem = leftItem;
    }

    public String getRightItem() {
        return rightItem;
    }

    public void setRightItem(String rightItem) {
        this.rightItem = rightItem;
    }

    public SearchConditionEnum getSc() {
        return sc;
    }

    public void setSc(SearchConditionEnum sc) {
        this.sc = sc;
    }
    
    public String getSearchCriteriaItem(){
        StringBuffer item = new StringBuffer();
        item.append(leftItem);
        item.append(sc.getCondition());
        
        if(!sc.equals(SearchConditionEnum.Like))
            item.append("'");
        else
            item.append("'%");
        item.append(rightItem);
        if(!sc.equals(SearchConditionEnum.Like))
            item.append("'");
        else
            item.append("%'");
        
        return item.toString();
    }
    
    
}
