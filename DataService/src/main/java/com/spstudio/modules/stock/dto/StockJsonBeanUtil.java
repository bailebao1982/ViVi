package com.spstudio.modules.stock.dto;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.entity.Stock;
import com.spstudio.modules.stock.service.StockService;

/**
 * Created by Soul on 2017/1/12.
 */
public class StockJsonBeanUtil {
    public static StockJsonBean toJsonBean(Stock stock) {
        StockJsonBean jsonBean = new StockJsonBean();
        Product product = stock.getProduct();
        jsonBean.setProduct_id(product.getProductId());
        jsonBean.setProduct_name(product.getProductName());
        jsonBean.setProduct_serialno(product.getSerialno());
        jsonBean.setProduct_stock(stock.getInventory());
        jsonBean.setProduct_stock_isinfinite(stock.isinfinite());
        jsonBean.setProduct_type(product.getType().getTypeName());
        jsonBean.setProduct_type_dscrp(product.getType().getDescription());
        return jsonBean;
    }

    public static Stock toEntityBean(StockJsonBean jsonBean, StockService stockService){
        // Only set stock & isInfinite
        String prdtId = jsonBean.getProduct_id();
        Stock stock = stockService.findStockByProductId(prdtId);
        if(stock != null){
            stock.setIsinfinite(jsonBean.isProduct_stock_isinfinite());
            stock.setInventory(jsonBean.getProduct_stock());
        }
        return stock;
    }
}
