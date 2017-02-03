/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.search;

/**
 *
 * @author wewezhu
 */
public enum SearchConditionEnum{
    Equal("="),
    LargeOrEqual(">="),
    Large(">"),
    SmallOrEqual("<="),
    Small("<"),
    Like(" like "),
    In(" in ");
    
    private final String condition;
    
    private SearchConditionEnum(String condition){
        this.condition = condition;
    }
    
    public String getCondition(){
        return condition;
    }
}
