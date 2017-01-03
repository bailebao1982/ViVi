package com.spstudio.modules.product.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.response.SuccessMessageBean;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.product.bean.request.*;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.product.service.ProductTypeService;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Soul on 2017/1/1.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * 日志对象
     */
    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    @Resource(name="productTypeService")
    private ProductTypeService productTypeService;

    @Resource(name="productService")
    private ProductService productService;

    @RequestMapping(value = "/list_product_type",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody
    ResponseBean listProductType(){
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        return ResponseMsgBeanFactory.getResponseBean(
                true,
                productTypes
        );
    }

    @RequestMapping(value = "/add_product_type",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addProductType(@RequestBody ProductTypeJsonBean productTypeJson){
        if(productTypeJson.getProduct_type_name().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2001",
                    "错误：新建产品分类失败，产品分类名称缺失"
            );
        }else {
            ProductType newProductType = ProductTypeJsonBeanUtil.toEntityBean(productTypeJson);
            productTypeService.addProductType(newProductType);
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品类型创建成功");
        }
    }

    @RequestMapping(value = "/del_product_type/{product_type_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean delProductType(@PathVariable String product_type_id){

        ProductType productType = productTypeService.findProductTypeByProductTypeId(product_type_id);
        if(productType != null){
            productTypeService.zapProductType(productType);
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品类型删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2004",
                    "未能发现该产品分类，产品分类ID：" + product_type_id
            );
        }
    }

    @RequestMapping(value = "/add_product",
            method = RequestMethod.PUT,
            headers = "Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addProduct(@RequestBody ProductJsonBean productJsonBean, Model model){
        if(productJsonBean.getProduct_name().isEmpty() ||
           productJsonBean.getProduct_serialno().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2009",
                    "错误：产品必要的信息丢失。请检查 产品名称，产品编码"
            );
        }else{
            Product product = ProductJsonBeanUtil.toEntityBean(productJsonBean, productTypeService);
            if(product == null){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "2020",
                        "错误：单位价格数字格式不正确"
                );
            }

            Map modelMap = model.asMap();
            Object userObj = modelMap.get("currentUser");
            if(userObj != null){
                product.setCreator(userObj.toString());
                product.setLastUpdateBy(userObj.toString());
            }

            try {
                productService.addProduct(product);
                return ResponseMsgBeanFactory.getSuccessResponseBean("创建产品成功");
            }catch (DataIntegrityViolationException ex){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "2010",
                        "错误：该产品编码已经被注册"
                );
            }
        }
    }

    @RequestMapping(value = "/del_product/{product_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delProduct(@PathVariable String product_id, Model model){
        Product product = productService.findProductByProductId(product_id);
        if(product == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2005",
                    "未能发现该产品，产品ID：" + product_id
            );
        }else{
            Map modelMap = model.asMap();
            Object userObj = modelMap.get("currentUser");
            if(userObj != null){
                product.setLastUpdateBy(userObj.toString());
            }

            productService.removeProduct(product);
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品删除成功");
        }
    }

    @RequestMapping(value = "/update_product/{product_id}",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean updateProduct(@RequestBody ProductJsonBean productJsonBean,
                                                    @PathVariable String product_id,
                                                    Model model){
        Product product = ProductJsonBeanUtil.toEntityBean(productJsonBean, productTypeService);
        if(product == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2020",
                    "错误：单位价格数字格式不正确"
            );
        }
        Map map = model.asMap();
        Object userObj = map.get("currentUser");
        if(userObj != null){
            product.setLastUpdateBy(userObj.toString());
        }
        product.setProductId(product_id);
        Product prdt = productService.updateProduct(product);
        if(prdt != null){
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品信息更新成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2006",
                    "产品信息更新失败，产品ID：" + product_id
            );
        }
    }

    @RequestMapping(value = "/batdel_products",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean batDelProducts(@RequestBody BatDelProductBean batDelProductBean, Model model){
        Map map = model.asMap();
        Object userObj = map.get("currentUser");
        String currentUser = "";
        if(userObj != null){
            currentUser = userObj.toString();
        }

        boolean result = productService.removeProductList(batDelProductBean.getProduct_id_list(), currentUser);
        if (result){
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品列表删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2007",
                    "产品列表删除失败，产品列表中会员无一存在"
            );
        }
    }

    @RequestMapping(value = "/list_products",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody ResponseBean listProducts(
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size,
            @RequestParam(value="product_name", required=false) String name,
            @RequestParam(value="product_type", required=false) String type,
            @RequestParam(value="product_serialno", required=false) String serialno,
            @RequestParam(value="price_start", required=false, defaultValue = "0") String price_start,
            @RequestParam(value="price_end", required=false, defaultValue = "0") String price_end){
        ProductQueryBean queryBean = new ProductQueryBean();
        queryBean.setName(name);
        queryBean.setPage(page);
        queryBean.setPage_size(page_size);
        queryBean.setType(type);
        queryBean.setSerialno(serialno);
        try {
            queryBean.setPrice_start(Float.valueOf(price_start));
        }catch (NumberFormatException ex){
            logger.error("listProducts price_start number format error ");
            queryBean.setPrice_start(0);
        }
        try {
            queryBean.setPrice_end(Float.valueOf(price_end));
        }catch (NumberFormatException ex){
            logger.error("listProducts price_end number format error ");
            queryBean.setPrice_end(0);
        }

        SearchCriteria sc = new SearchCriteria();
        if(queryBean.getName() != null &&
           !queryBean.getName().isEmpty()){
            sc.addSearchCriterialItem("name",
                    new SearchCriteriaItem("productName", queryBean.getName(), SearchConditionEnum.Like)
            );
        }

        if(queryBean.getSerialno() != null &&
           !queryBean.getSerialno().isEmpty()){
            sc.addSearchCriterialItem("serialno",
                    new SearchCriteriaItem("serialno", queryBean.getSerialno(), SearchConditionEnum.Like)
            );
        }

        if(queryBean.getPrice_start() > 0){
            sc.addSearchCriterialItem("price_start",
                    new SearchCriteriaItem("unitPrice", String.valueOf(queryBean.getPrice_start()), SearchConditionEnum.LargeOrEqual)
            );
        }

        if(queryBean.getPrice_end() > 0){
            sc.addSearchCriterialItem("price_end",
                    new SearchCriteriaItem("unitPrice", String.valueOf(queryBean.getPrice_end()), SearchConditionEnum.SmallOrEqual)
            );
        }

        if(queryBean.getType() != null &&
           !queryBean.getType().isEmpty()){
            ProductType productType = productTypeService.findProductTypeByProductTypeName(queryBean.getType());
            if(productType != null) {
                sc.addSearchCriterialItem("type",
                        new SearchCriteriaItem("productTypeId", productType.getProductTypeId(), SearchConditionEnum.Equal)
                );
            }
        }

        Page<Product> resultPageBean = productService.queryForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
        Page<ProductJsonBean> returnPage = new Page<ProductJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        List<ProductJsonBean> returnArray = new ArrayList<ProductJsonBean>();
        for (Product product : resultPageBean.getList()){
            returnArray.add(ProductJsonBeanUtil.toJsonBean(product));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/detail_product/{product_id}",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody ResponseBean detailProduct(@PathVariable String product_id){
        Product product = productService.findProductByProductId(product_id);
        if(product != null){
            ProductJsonBean responseProductJsonBean = ProductJsonBeanUtil.toJsonBean(product);
            return ResponseMsgBeanFactory.getResponseBean(
                    true,
                    responseProductJsonBean
            );
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2008",
                    "产品信息查找失败，无法找到产品ID：" + product_id
            );
        }
    }

    @RequestMapping(value = "/test",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean<SuccessMessageBean> test(){
        return ResponseMsgBeanFactory.getSuccessResponseBean("测试成功");
    }
}
