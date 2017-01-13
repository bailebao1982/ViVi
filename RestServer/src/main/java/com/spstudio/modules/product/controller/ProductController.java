package com.spstudio.modules.product.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.response.SuccessMessageBean;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.product.bean.request.*;
import com.spstudio.modules.product.entity.PackageProductMapping;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.product.service.ProductTypeService;

import com.spstudio.modules.stock.entity.Stock;
import com.spstudio.modules.stock.service.StockService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource(name="stockService")
    private StockService stockService;

    /**
     *
     * product functions
     *
     */
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
    public @ResponseBody ResponseBean batDelProducts(@RequestBody ProductIdListBean productIdListBean, Model model){
        Map map = model.asMap();
        Object userObj = map.get("currentUser");
        String currentUser = "";
        if(userObj != null){
            currentUser = userObj.toString();
        }

        boolean result = productService.removeProductList(productIdListBean.getProduct_id_list(), currentUser);
        if (result){
            return ResponseMsgBeanFactory.getSuccessResponseBean("产品列表删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2007",
                    "产品列表删除失败，产品列表中产品无一存在"
            );
        }
    }

    @RequestMapping(value = "/list_products_for_select",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody List<SimpleProductJsonBean> listProducts(){
        List<Product> allProducts = productService.getAllProducts();
        return SimpleProductJsonBeanUtil.toJsonBeanList(allProducts);
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

        Page<Product> resultPageBean = productService.queryProductsForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
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

    /**
     *
     * package functions
     *
     */
    @RequestMapping(value = "/add_package",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addPackage(@RequestBody PackageJsonBean packageJson){
        if(packageJson.getPackage_products().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2101",
                    "新建套餐失败，该套餐不包含任何产品"
            );
        }else if(packageJson.getPackage_name().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2102",
                    "新建套餐失败，该套餐名称为空"
            );
        }else if(packageJson.getPackage_serialno().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2103",
                    "新建套餐失败，该套餐编号为空"
            );
        }else {
            ProductPackage pkgBean = PackageJsonBeanUtil.toEntityBean(packageJson, productService);
            productService.addProductPackage(pkgBean);
            return ResponseMsgBeanFactory.getSuccessResponseBean("套餐创建成功");
        }
    }

    private void mergeProductPackage(ProductPackage currentPkg, ProductPackage postPkg){
        if(postPkg.getProductPackageId().equalsIgnoreCase(currentPkg.getProductPackageId())){
            if(postPkg.getSerialNo() != null &&
               !postPkg.getSerialNo().isEmpty()){
                currentPkg.setSerialNo(postPkg.getSerialNo());
            }

            if(postPkg.getPackageName() != null &&
                    !postPkg.getPackageName().isEmpty()){
                currentPkg.setPackageName(postPkg.getPackageName());
            }

            if(postPkg.getEffectiveStartDate() != null){
                currentPkg.setEffectiveStartDate(postPkg.getEffectiveStartDate());
            }

            if(postPkg.getEffectiveEndDate() != null){
                currentPkg.setEffectiveEndDate(postPkg.getEffectiveEndDate());
            }

            if(postPkg.getDescription() != null &&
               !postPkg.getDescription().isEmpty()){
                currentPkg.setDescription(postPkg.getDescription());
            }

            if(postPkg.getUnitPrice() > 0){
                currentPkg.setUnitPrice(postPkg.getUnitPrice());
            }

            if(postPkg.getProductMappingSet() != null &&
               postPkg.getProductMappingSet().size() > 0){
                currentPkg.setProductMappingSet(postPkg.getProductMappingSet());
            }

            if(currentPkg.isAvailable() !=  postPkg.isAvailable()){
                currentPkg.setAvailable(postPkg.isAvailable());
            }
        }
    }

    @RequestMapping(value = "/update_package/{package_id}",
            method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody ResponseBean updatePackage(@PathVariable String package_id, @RequestBody PackageJsonBean packageJson, Model model){
        ProductPackage pkg = PackageJsonBeanUtil.toEntityBean(packageJson, productService);
        if(pkg == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2120",
                    "错误：单位价格数字格式不正确"
            );
        }
        Map map = model.asMap();
        Object userObj = map.get("currentUser");
        if(userObj != null){
            pkg.setLastUpdateBy(userObj.toString());
        }
        pkg.setProductPackageId(package_id);

        ProductPackage tobeUpdatePkg = productService.findProductPackageByPackageId(package_id);
        mergeProductPackage(tobeUpdatePkg, pkg);
        ProductPackage updatedPkg = productService.updateProdctPackage(tobeUpdatePkg);
        if(updatedPkg != null){
            return ResponseMsgBeanFactory.getSuccessResponseBean("套餐信息更新成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2106",
                    "套餐信息更新失败，套餐ID：" + package_id
            );
        }
    }

    @RequestMapping(value = "/update_package_products/{package_id}",
            method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody ResponseBean updatePackageProducts(@PathVariable String package_id, @RequestBody PackageProductListBean productsListBean){
        ProductPackage pkg = productService.findProductPackageByPackageId(package_id);
        if(pkg != null){
            Set<PackageProductMapping> newMappingSet = new HashSet<PackageProductMapping>();
            for (PackageProductJsonBean pkgProduct : productsListBean.getProducts()){
                Product product = productService.findProductByProductId(pkgProduct.getProduct().getProduct_id());

                PackageProductMapping newMapping = new PackageProductMapping();
                newMapping.setCount(pkgProduct.getProduct_count());
                newMapping.setProduct(product);
                newMapping.setProductPackage(pkg);

                newMappingSet.add(newMapping);
            }
            pkg.setProductMappingSet(newMappingSet);

            productService.updateProdctPackage(pkg);

            return ResponseMsgBeanFactory.getSuccessResponseBean("套餐内容更新成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2110",
                    "该套餐不存在，套餐ID：" + package_id
            );
        }
    }

    @RequestMapping(value = "/detail_package/{package_id}",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody ResponseBean detailPackage(@PathVariable String package_id){
        ProductPackage pkg = productService.findProductPackageByPackageId(package_id);
        if(pkg != null){
            PackageJsonBean pkgJsonBean = PackageJsonBeanUtil.toJsonBean(pkg);
            return ResponseMsgBeanFactory.getResponseBean(
                    true,
                    pkgJsonBean
            );
        }else {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2110",
                    "该套餐不存在，套餐ID：" + package_id
            );
        }
    }

    @RequestMapping(value = "/batdel_packages",
            method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody ResponseBean batDelPackages(@RequestBody PackageIdListBean packageIdList, Model model){
        Map map = model.asMap();
        Object userObj = map.get("currentUser");
        String currentUser = "";
        if(userObj != null){
            currentUser = userObj.toString();
        }

        boolean result = productService.removePackageList(packageIdList.getPackage_id_list(), currentUser);
        if (result){
            return ResponseMsgBeanFactory.getSuccessResponseBean("套餐列表删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2107",
                    "套餐列表删除失败，套餐列表中套餐无一存在"
            );
        }
    }

    @RequestMapping(value = "/del_package/{package_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delPackage(@PathVariable String package_id){
        ProductPackage pkg = productService.findProductPackageByPackageId(package_id);
        if(pkg != null){
            productService.removePackage(pkg);
            return ResponseMsgBeanFactory.getSuccessResponseBean("套餐删除成功!");
        }else {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2110",
                    "该套餐不存在，套餐ID：" + package_id
            );
        }
    }

    @RequestMapping(value = "/list_packages_for_select",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody List<SimplePackageJsonBean> listPackagesForSelect(){
        List<ProductPackage> packages = productService.getAllPackages();
        return SimplePackageJsonBeanUtil.toJsonBeanList(packages);
    }

    @RequestMapping(value = "/list_packages",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody ResponseBean listPackages(@RequestParam(value="page", required=true) int page,
                                                   @RequestParam(value="page_size", required=true) int page_size,
                                                   @RequestParam(value="package_name", required=false) String name,
                                                   @RequestParam(value="package_serialno", required=false) String serialno,
                                                   @RequestParam(value="price_start", required=false, defaultValue = "0") String price_start,
                                                   @RequestParam(value="price_end", required=false, defaultValue = "0") String price_end,
                                                   @RequestParam(value="start_date", required=false) String start_date,
                                                   @RequestParam(value="start_end", required=false) String end_date){

        PackageQueryBean queryBean = new PackageQueryBean();
        queryBean.setName(name);
        queryBean.setPage(page);
        queryBean.setPage_size(page_size);
        queryBean.setSerialno(serialno);
        queryBean.setStart_date(start_date);
        queryBean.setEnd_date(end_date);
        try {
            queryBean.setPrice_start(Float.valueOf(price_start));
        }catch (NumberFormatException ex){
            logger.error("list_packages price_start number format error ");
            queryBean.setPrice_start(0);
        }
        try {
            queryBean.setPrice_end(Float.valueOf(price_end));
        }catch (NumberFormatException ex){
            logger.error("list_packages price_end number format error ");
            queryBean.setPrice_end(0);
        }

        SearchCriteria sc = new SearchCriteria();
        if(queryBean.getName() != null &&
           !queryBean.getName().isEmpty()){
            sc.addSearchCriterialItem("name",
                    new SearchCriteriaItem("packageName", queryBean.getName(), SearchConditionEnum.Like)
            );
        }

        if(queryBean.getSerialno() != null &&
           !queryBean.getSerialno().isEmpty()){
            sc.addSearchCriterialItem("serialNo",
                    new SearchCriteriaItem("serialNo", queryBean.getSerialno(), SearchConditionEnum.Equal)
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

        if(queryBean.getStart_date() != null &&
           !queryBean.getStart_date().isEmpty()){
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("effectiveStartDate", String.valueOf(queryBean.getPrice_end()), SearchConditionEnum.SmallOrEqual)
            );
        }

        if(queryBean.getEnd_date() != null &&
           !queryBean.getEnd_date().isEmpty()){
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("effectiveEndDate", String.valueOf(queryBean.getPrice_end()), SearchConditionEnum.LargeOrEqual)
            );
        }

        Page<ProductPackage> resultPageBean = productService.queryPackagesForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
        Page<PackageJsonBean> returnPage = new Page<PackageJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        List<PackageJsonBean> returnArray = new ArrayList<PackageJsonBean>();
        for (ProductPackage pkg : resultPageBean.getList()){
            returnArray.add(PackageJsonBeanUtil.toJsonBean(pkg));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/update_stock/{product_id}",
            method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody ResponseBean updateStock(@PathVariable String product_id, @RequestBody StockJsonBean stockJsonBean){
        Stock stock = StockJsonBeanUtil.toEntityBean(stockJsonBean, stockService);
        if(stock == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "2210",
                    "该产品不存在，产品ID：" + product_id
            );
        }else{
            stockService.updateStock(stock);
            return ResponseMsgBeanFactory.getSuccessResponseBean("库存更新成功!");
        }
    }

    @RequestMapping(value = "/list_stocks",
            method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody ResponseBean listStocks(@RequestParam(value="page", required=true) int page,
                                                 @RequestParam(value="page_size", required=true) int page_size,
                                                 @RequestParam(value="product_name", required=false) String name,
                                                 @RequestParam(value="product_type", required=false) String type,
                                                 @RequestParam(value="product_serialno", required=false) String serialno){
        ProductQueryBean queryBean = new ProductQueryBean();
        queryBean.setPage(page);
        queryBean.setPage_size(page_size);

        queryBean.setName(name);
        queryBean.setType(type);
        queryBean.setSerialno(serialno);

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

        if(queryBean.getType() != null &&
                !queryBean.getType().isEmpty()){
            ProductType productType = productTypeService.findProductTypeByProductTypeName(queryBean.getType());
            if(productType != null) {
                sc.addSearchCriterialItem("type",
                        new SearchCriteriaItem("productTypeId", productType.getProductTypeId(), SearchConditionEnum.Equal)
                );
            }
        }

        Page<Stock> resultPageBean = stockService.queryStocksForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
        Page<StockJsonBean> returnPage = new Page<StockJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        List<StockJsonBean> returnArray = new ArrayList<StockJsonBean>();
        for (Stock stock : resultPageBean.getList()){
            returnArray.add(StockJsonBeanUtil.toJsonBean(stock));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }
}
