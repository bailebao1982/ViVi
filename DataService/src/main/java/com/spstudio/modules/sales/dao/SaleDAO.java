/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sales.entity.Sales;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface SaleDAO {
    public Sales findSaleRecordsById(String saleId);

    public List<Sales> findSaleRecordsByMember(Member member);

    public Sales addSalesRecord(Sales sales);

    public Sales updateSalesRecord(Sales sales);

    public Sales removeSalesRecord(Sales sales);

    public boolean removeSalesRecords(List<String> salesIdList);
    
    public boolean zapSalesRecordOfMember(Member member);

    public void zapSalesRecord(Sales saleRec);

    public int getQueryCount(SearchCriteria criteria);

    public List<Sales> queryForPage(int offset, int length, SearchCriteria criteria);
}
