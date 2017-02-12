/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.service.impl;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.service.ConfigConditions;
import com.spstudio.common.service.SystemConfigService;
import com.spstudio.common.service.entity.SystemConfig;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.entity.PackageProductMapping;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.sales.config.Configuration;
import com.spstudio.modules.sales.dao.SaleDAO;
import com.spstudio.modules.sales.dao.SaleDiscountDAO;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.entity.Sales;
import com.spstudio.modules.sales.exception.InsufficientBonusPointException;
import com.spstudio.modules.sales.exception.InsufficientDepositException;
import com.spstudio.modules.sales.service.PaymentMethodType;
import com.spstudio.modules.sales.service.SaleService;
import com.spstudio.modules.stock.exceptions.StockNotEnoughException;
import com.spstudio.modules.stock.service.StockService;
import org.apache.commons.lang3.Validate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class SaleServiceImpl implements SaleService {

    private final static String CASH_PAYMENT_METHOD = "CASH";
    private final static String DEPOSIT_PAYMENT_METHOD = "DEPOSIT";
    private final static String BONUSPOINT_PAYMENT_METHOD = "BONUSPOINT";

    private MemberService memberService;

    // For stock deduction
    private StockService stockService;

    private SaleDAO saleDAO;

    private SaleDiscountDAO saleDiscountDAO;

    public SaleDAO getSaleDAO() {
        return saleDAO;
    }

    public void setSaleDAO(SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
    }

    public SaleDiscountDAO getSaleDiscountDAO() {
        return saleDiscountDAO;
    }

    public void setSaleDiscountDAO(SaleDiscountDAO saleDiscountDAO) {
        this.saleDiscountDAO = saleDiscountDAO;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    /*
         * Config operations
         */
    SystemConfigService configService;

    public SystemConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(SystemConfigService configService) {
        this.configService = configService;
    }

    @Override
    // 获得全局积分百分比
    public Float getGlobalDepositRate(){
        SystemConfig config = configService.findModuleSingleConfig(
                Configuration.SALE_MODULE_NAME,
                Configuration.CONFIG_GLOBAL_DEPOSIT_BONUSRATE);
        if(config == null){
            return 0.0f;
        }
        try {
            float bonusRate = Float.valueOf(config.getConfigValue());
            return bonusRate;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return 0.0f;
        }
    }

    @Override
    public void createOrUpdateGlobalDepositRate(Float depositRate){
        SystemConfig config = configService.findModuleSingleConfig(
                Configuration.SALE_MODULE_NAME,
                Configuration.CONFIG_GLOBAL_DEPOSIT_BONUSRATE);
        if(config == null){
            config = new SystemConfig();

            config.setConfigModule(Configuration.SALE_MODULE_NAME);
            config.setConfigName(Configuration.CONFIG_GLOBAL_DEPOSIT_BONUSRATE);
        }
        config.setConfigValue(String.valueOf(depositRate));

        configService.addConfig(config);
    }

    @Override
    // 根据会员类型获得积分百分比
    public Float getDepositRate(MemberType memberType) {
        List<SystemConfig> configs = configService.findModuleConfig(
                Configuration.SALE_MODULE_NAME,
                Configuration.CONFIG_MEMBER_TYPE_DEPOSIT_BONUSRATE);

        if(configs == null || configs.size() == 0){
            return getGlobalDepositRate();
        }
        for (SystemConfig config : configs){
            String condition = config.getConfigCondition();
            if(condition.startsWith(ConfigConditions.EQUAL)){
                String memberTypeId = condition.replace(ConfigConditions.EQUAL, "");
                if(memberType.getMemberTypeId().equalsIgnoreCase(memberTypeId)){
                    try {
                        float bonusRate = Float.valueOf(config.getConfigValue());
                        return bonusRate;
                    }catch (NumberFormatException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }

        return getGlobalDepositRate();
    }

    @Override
    public void createOrUpdateDepositRate(MemberType memberType, Float depositRate) {
        SystemConfig config = configService.findModuleSingleConfig(
                Configuration.SALE_MODULE_NAME,
                Configuration.CONFIG_MEMBER_TYPE_DEPOSIT_BONUSRATE);

        if(config == null){
            config = new SystemConfig();

            config.setConfigModule(Configuration.SALE_MODULE_NAME);
            config.setConfigName(Configuration.CONFIG_MEMBER_TYPE_DEPOSIT_BONUSRATE);
        }
        config.setConfigCondition(ConfigConditions.EQUAL + memberType.getMemberTypeId());
        config.setConfigValue(String.valueOf(depositRate));

        configService.addConfig(config);
    }

    /*
     *  Sales operations
     */

    private Sales _getNewSaleRecord(Member member,
                                    int assetType ,
                                    Product product,
                                    SaleDiscount discount,
                                    ProductPackage pkg,
                                    int deposit,
                                    int count,
                                    float price,
                                    String saler,
                                    int paymentType){
        Sales returnSale = new Sales();

        returnSale.setSaleType(assetType);

        returnSale.setProduct(product);
        returnSale.setProductPackage(pkg);
        returnSale.setDeposit(deposit);

        returnSale.setDiscount(discount);
        returnSale.setPrice(price);

        returnSale.setMember(member);
        returnSale.setSalesCount(count);

        returnSale.setSaler(saler);

        returnSale.setPaymentMethodType(paymentType);

        return returnSale;
    }

    @Override
    public Sales findSaleRecordById(String saleId){
        return saleDAO.findSaleRecordsById(saleId);
    }

    @Override
    public List<Sales> findSaleRecordOfMember(Member member) {
        return saleDAO.findSaleRecordsByMember(member);
    }

    private Sales _addProductSaleRecord(Member member,
                                       Product product,
                                       int count,
                                       int deposit,
                                       float price,
                                       String saler,
                                       PaymentMethodType type) {
        SaleDiscount discount = _getDiscount(product, member.getMemberType());

        Sales saleRec = _getNewSaleRecord(member,
                                            AssetType.ASSET_PRODUCT_TYPE.ordinal(),
                                            product,
                                            discount,
                                            null,
                                            deposit,
                                            count,
                                            price,
                                            saler,
                                            type.ordinal());
        Sales retSale = saleDAO.addSalesRecord(saleRec);
        return retSale;
    }


    private Sales _addProductPackageSaleRecord(Member member,
                                               ProductPackage pkg,
                                               int count,
                                               float price,
                                               String saler,
                                               PaymentMethodType type) {
        Sales saleRec = _getNewSaleRecord(member, AssetType.ASSET_PACKAGE_TYPE.ordinal(), null, null, pkg, 0, count, price, saler, type.ordinal());
        Sales retSale = saleDAO.addSalesRecord(saleRec);
        return retSale;
    }

    private Sales _addDepositSaleRecord(Member member,
                                        int deposit,
                                        String saler,
                                        PaymentMethodType type) {
        Sales saleRec = _getNewSaleRecord(member, AssetType.ASSET_DEPOSIT_TYPE.ordinal(), null, null, null, deposit, 1, deposit, saler, type.ordinal());
        Sales retSale = saleDAO.addSalesRecord(saleRec);
        return retSale;
    }

    @Override
    public boolean removeSaleRecord(Sales salerec) {
        Sales sale = saleDAO.removeSalesRecord(salerec);
        return sale != null;
    }

    @Override
    public boolean removeSaleRecordList(List<String> assetIdList) {
        return saleDAO.removeSalesRecords(assetIdList);
    }

    @Override
    public boolean removeSaleRecordsOfMember(Member member) {
        List<Sales> sales = saleDAO.findSaleRecordsByMember(member);
        return sales.size() > 0;
    }

    @Override
    public Sales updateSaleRecord(Sales salerec) {
        return saleDAO.updateSalesRecord(salerec);
    }

    @Override
    public boolean zapSaleRecordOfMember(Member member) {
        return saleDAO.zapSalesRecordOfMember(member);
    }

    @Override
    public void zapSalesRecord(Sales sales) {
        saleDAO.zapSalesRecord(sales);
    }

    @Override
    public Page<Sales> querySalesForPage(int currentPage, int pageSize, SearchCriteria criteria) {
        //总记录数
        int allRow = saleDAO.getQueryCount(criteria);

        Page<Sales> page = new Page<Sales>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<Sales> list = saleDAO.queryForPage(currentPage, pageSize,criteria);

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    /*
     *  Discount operations
     */

    private SaleDiscount _getDiscount(Product product, MemberType memberType){
        SaleDiscount discount = saleDiscountDAO.getDiscountByProduct(product, memberType);
        if(discount == null){
            discount = saleDiscountDAO.getDiscountByProductType(product.getType(), memberType);
        }
        return discount;
    }

    @Override
    public SaleDiscount createDiscountForProduct(Product product, MemberType type, float discount) {
        return saleDiscountDAO.createDiscount(product, type, discount);
    }

    @Override
    public SaleDiscount createDiscountForProductType(ProductType productType, MemberType type, float discount) {
        return saleDiscountDAO.createDiscount(productType, type, discount);
    }

    @Override
    public SaleDiscount findDiscountById(String discountId){
        return saleDiscountDAO.getDiscountByProduct(discountId);
    }

    @Override
    public SaleDiscount findDiscount(Product product, MemberType type) {
        return saleDiscountDAO.getDiscountByProduct(product, type);
    }

    @Override
    public SaleDiscount findDiscount(ProductType productType, MemberType type) {
        return saleDiscountDAO.getDiscountByProductType(productType, type);
    }

    @Override
    public void updateDiscount(SaleDiscount discount) {
        saleDiscountDAO.updateDiscountByProduct(discount);
    }

    @Override
    public boolean deleteDiscount(SaleDiscount discount) {
        return saleDiscountDAO.zapDiscount(discount);
    }

    @Override
    public boolean deleteDiscountByProduct(Product product) {
        return saleDiscountDAO.zapDiscountsByProduct(product);
    }

    @Override
    public boolean deleteDiscountByProductType(ProductType productType) {
        return saleDiscountDAO.zapDiscountsByProductType(productType);
    }

    @Override
    public boolean deleteDiscountByMemberType(MemberType type){
        return saleDiscountDAO.zapDiscountsByMemberType(type);
    }

    @Override
    public Page<SaleDiscount> queryDiscountForPage(int currentPage, int pageSize, SearchCriteria criteria){
        //总记录数
        int allRow = saleDiscountDAO.getQueryCount(criteria);

        Page<SaleDiscount> page = new Page<SaleDiscount>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<SaleDiscount> list = saleDiscountDAO.queryForPage(currentPage, pageSize,criteria);

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    boolean _checkStock(Product product, int count){
        return stockService.isStockEnoughForDecrease(product, count);
    }

    boolean _checkStock(ProductPackage pkg, int count){
        boolean isEnoughStock = true;

        Iterator iter = pkg.getProductMappingSet().iterator();
        while (isEnoughStock && iter.hasNext()){
            PackageProductMapping mapping = (PackageProductMapping)iter.next();
            isEnoughStock = stockService.isStockEnoughForDecrease(mapping.getProduct(), mapping.getCount() * count);
        }

        return isEnoughStock;
    }

    boolean _checkDeposit(Member member, int price){
        MemberAsset depositAsset = memberService.getDepositAssetOfMember(member);
        int deposit = (depositAsset == null) ? 0: depositAsset.getDeposit();
        return (deposit >= price);
    }

    boolean _checkDeposit(Member member, ProductPackage pkg, int count){
        MemberAsset depositAsset = memberService.getDepositAssetOfMember(member);
        int deposit = (depositAsset == null) ? 0: depositAsset.getDeposit();
        return (deposit >= pkg.getUnitPrice() * count);
    }

    boolean _checkBonusPoint(Member member, Product product, int count){
        int restBp = memberService.getBonusPoint(member);
        int toSpendBp = product.getBonusePoint() * count;
        return (restBp >= toSpendBp);
    }

    /**
     * Buy Deposit, pay with cash
     */
    @Transactional(rollbackFor=Exception.class)
    private boolean _buyDepositPayWithCash(Member member, int deposit, String saler){
        // add record
        _addDepositSaleRecord(member, deposit, saler, PaymentMethodType.PAYMENT_METHOD_CASH);

        // add memeber asset
        memberService.increaseOrAddDepositAsset(member, deposit);

        // this will cause member type change
        MemberType newMemberType = memberService.getDepositMemberTypeRank(deposit);
        if(newMemberType != null){
            if(!member.getMemberType().getMemberTypeId().equalsIgnoreCase(
                    newMemberType.getMemberTypeId()
                )
            ){
                member.setMemberType(newMemberType);
                memberService.updateMember(member);
            }
        }else {
            // means this deposit charge does not tigger any rule,
            // so the member type won't change
        }

        // this will generate bonus point
        float bonusRate = getDepositRate(member.getMemberType());
        int bonusPoint = (int)(bonusRate * (float) deposit);
        if(bonusPoint > 0){
            memberService.increaseBonusPoint(member, bonusPoint);
        }

        return true;
    }

    /**
     * Buy product and pay with cash
     */
    private void _buyProductPayWithCash(Member member, Product product, int count, String saler)
            throws StockNotEnoughException{

        float price = this.GetProductPrice(
                PaymentMethodType.PAYMENT_METHOD_CASH,
                null,
                product,
                1);
        _buyProductPayWithCash(member, product, count, price, saler);
    }

    @Transactional(rollbackFor=Exception.class)
    private void _buyProductPayWithCash(Member member, Product product, int count, float price, String saler)
            throws StockNotEnoughException{
//        boolean isEnoughStock = _checkStock(product, count);
//        if(isEnoughStock){
        // decrease stock first
        stockService.decreaseStockNum(product, count);

        // add record
        _addProductSaleRecord(member, product, count, 0, price, saler, PaymentMethodType.PAYMENT_METHOD_CASH);
        // add memeber asset
        memberService.addProductAsset(member, product, count);
//        }else{
//            throw new StockNotEnoughException();
//        }
    }

    /**
     * Buy package and pay with cash
     */
    @Transactional(rollbackFor=Exception.class)
    private void _buyPackagePayWithCash(Member member, ProductPackage pkg, int count, String saler)
            throws StockNotEnoughException{
        float price = this.GetPackagePrice(pkg, count);
        _buyPackagePayWithCash(member, pkg, count, price, saler);
    }

    @Transactional(rollbackFor=Exception.class)
    private void _buyPackagePayWithCash(Member member, ProductPackage pkg, int count, float price, String saler)
            throws StockNotEnoughException{
//        boolean isEnoughStock = _checkStock(pkg, count);
//        if(isEnoughStock){
//          try {
        // decrease stock first
        Iterator iter = pkg.getProductMappingSet().iterator();
        while (iter.hasNext()){
            PackageProductMapping mapping = (PackageProductMapping)iter.next();
            stockService.decreaseStockNum(mapping.getProduct(), mapping.getCount() * count);
        }

        // add record
        _addProductPackageSaleRecord(member, pkg, count, price, saler, PaymentMethodType.PAYMENT_METHOD_CASH);
        // add memeber asset
        memberService.addPackageAsset(member, pkg, count);
//                return true;
//            } catch (StockNotEnoughException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
    }
    /**
     * Buy product and pay with deposit
     */
    private void _buyProductPayWithDeposit(Member member, Product product, int count, String saler)
            throws StockNotEnoughException, InsufficientDepositException{

        float price = this.GetProductPrice(
                PaymentMethodType.PAYMENT_METHOD_DEPOSIT,
                member,
                product,
                count
        );
        _buyProductPayWithDeposit(member, product, count, price, saler);
    }

    @Transactional(rollbackFor=Exception.class)
    private void _buyProductPayWithDeposit(Member member, Product product, int count, float price, String saler)
            throws StockNotEnoughException, InsufficientDepositException{

        //boolean isEnoughStock = _checkStock(product, count);
        boolean isEnoughDeposit = _checkDeposit(member, (int)price);

        if(isEnoughDeposit){
            //try {
            // decrease stock
            stockService.decreaseStockNum(product, count);

            // decrease deposit
            MemberAsset depositAsset = memberService.getDepositAssetOfMember(member);
            int deposit = (depositAsset == null) ? 0: depositAsset.getDeposit();
            depositAsset.setDeposit(deposit - (int)price);
            memberService.updateDepositAsset(depositAsset);

            // add record
            this._addProductSaleRecord(member, product, count, (int)price, 0.0f, saler, PaymentMethodType.PAYMENT_METHOD_DEPOSIT);

            // add memeber asset
            memberService.addProductAsset(member, product, count);

//            } catch (StockNotEnoughException e) {
//                e.printStackTrace();
//                return false;
//            }
        }else{
            throw new InsufficientDepositException();
        }
    }

    /**
     * Buy package and pay with deposit
     */
    @Transactional(rollbackFor=Exception.class)
    private void _buyPackagePayWithDeposit(Member member, ProductPackage pkg, int count, String saler)
        throws StockNotEnoughException, InsufficientDepositException{
        float price = GetPackagePrice(pkg, count);
        _buyPackagePayWithDeposit(member, pkg, count, price, saler);
    }

    @Transactional(rollbackFor=Exception.class)
    private void _buyPackagePayWithDeposit(Member member, ProductPackage pkg, int count, float price, String saler)
        throws StockNotEnoughException, InsufficientDepositException {
        //boolean isEnoughStock = _checkStock(pkg, count);
        boolean isEnoughDeposit = _checkDeposit(member, pkg, count);

        if(isEnoughDeposit){
//            try {
                // decrease stock first
                Iterator iter = pkg.getProductMappingSet().iterator();
                while (iter.hasNext()){
                    PackageProductMapping mapping = (PackageProductMapping)iter.next();
                    stockService.decreaseStockNum(mapping.getProduct(), mapping.getCount() * count);
                }

                // decrease deposit
                MemberAsset depositAsset = memberService.getDepositAssetOfMember(member);
                int deposit = (depositAsset == null) ? 0: depositAsset.getDeposit();
                int cost = this.GetPackagePrice(pkg, count);
                depositAsset.setDeposit(deposit - cost);
                memberService.updateDepositAsset(depositAsset);

                // add record
                _addProductPackageSaleRecord(member, pkg, count, price, saler, PaymentMethodType.PAYMENT_METHOD_DEPOSIT);

                // add memeber asset
                memberService.addPackageAsset(member, pkg, count);

//                return true;
//            } catch (StockNotEnoughException e) {
//                e.printStackTrace();
//                return false;
//            }
        }else{
            throw new InsufficientDepositException();
        }
    }

    /**
     * Buy product and pay with bonus point
     */
    @Transactional(rollbackFor=Exception.class)
    private void _buyProductPayWithBonusPoint(Member member, Product product, int count, String saler)
            throws StockNotEnoughException, InsufficientBonusPointException{
        int cost = this.GetProductBonusPoint(product, count);
        _buyProductPayWithBonusPoint(member, product, count, cost, saler);
    }

    @Transactional(rollbackFor=Exception.class)
    private void _buyProductPayWithBonusPoint(Member member, Product product, int count, int cost, String saler)
        throws StockNotEnoughException, InsufficientBonusPointException{

        boolean isEnoughBonusPoint = _checkBonusPoint(member, product, count);
        if(isEnoughBonusPoint){
                // decrease stock
                stockService.decreaseStockNum(product, count);

                // decrease bonus point
                int bp = memberService.getBonusPoint(member);
                int restBp = bp - cost;
                memberService.updateBonusPoint(member, restBp);

                // add record
                _addProductSaleRecord(member, product, count, 0, 0, saler, PaymentMethodType.PAYMENT_METHOD_BONUSPOINT);

                // add memeber asset
                memberService.addProductAsset(member, product, count);
        }else{
            throw new InsufficientBonusPointException();
        }
    }

    @Override
    public boolean BuyDeposit(Member member, int deposit, String saler) {
        return _buyDepositPayWithCash(member, deposit, saler);
    }

    @Override
    public void BuyProduct(PaymentMethodType type, Member member, Product product, int count, String saler)
        throws StockNotEnoughException, InsufficientDepositException, InsufficientBonusPointException{
        switch (type){
            case PAYMENT_METHOD_CASH:
                _buyProductPayWithCash(member, product, count, saler);
                break;
            case PAYMENT_METHOD_BONUSPOINT:
                _buyProductPayWithBonusPoint(member, product, count, saler);
                break;
            case PAYMENT_METHOD_DEPOSIT:
                _buyProductPayWithDeposit(member, product, count, saler);
                break;
        }
    }

    @Override
    public void BuyProduct(PaymentMethodType type, Member member, Product product, int count, float price, String saler)
            throws StockNotEnoughException, InsufficientDepositException, InsufficientBonusPointException{
        switch (type){
            case PAYMENT_METHOD_CASH:
                _buyProductPayWithCash(member, product, count, price, saler);
                break;
            case PAYMENT_METHOD_BONUSPOINT:
                int cost = (int)price;
                _buyProductPayWithBonusPoint(member, product, count, cost, saler);
                break;
            case PAYMENT_METHOD_DEPOSIT:
                _buyProductPayWithDeposit(member, product, count, price, saler);
                break;
        }
    }

    @Override
    public void BuyPackage(PaymentMethodType type, Member member, ProductPackage pkg, int count, String saler)
        throws StockNotEnoughException, InsufficientDepositException{
        switch (type){
            case PAYMENT_METHOD_CASH: {
                _buyPackagePayWithCash(member, pkg, count, saler);
                break;
            }
            case PAYMENT_METHOD_DEPOSIT: {
                _buyPackagePayWithDeposit(member, pkg, count, saler);
                break;
            }
        }
    }

    @Override
    public void BuyPackage(PaymentMethodType type, Member member, ProductPackage pkg, int count, float price, String saler)
            throws StockNotEnoughException, InsufficientDepositException{
        switch (type){
            case PAYMENT_METHOD_CASH: {
                _buyPackagePayWithCash(member, pkg, count, price, saler);
                break;
            }
            case PAYMENT_METHOD_DEPOSIT: {
                _buyPackagePayWithDeposit(member, pkg, count, price, saler);
                break;
            }
        }
    }

    @Override
    public float GetProductPrice(PaymentMethodType type, Member member, Product product, int count){
        Validate.notNull(product, "product should not be null");
        switch (type){
            case PAYMENT_METHOD_CASH: {
                // should not add discount logic
                float price = product.getUnitPrice() * count;
                return price;
            }
            case PAYMENT_METHOD_DEPOSIT: {
                Validate.notNull(member, "member should not be null");
                SaleDiscount discount = saleDiscountDAO.getDiscountByProduct(product, member.getMemberType());
                // Discount logic
                float rawPrice = product.getUnitPrice() * count;
                float price = rawPrice;
                if(discount != null && discount.getDiscountValue() < 1.0f){
                    price = rawPrice * discount.getDiscountValue();
                }
                return price;
            }
            default:
                return 0.0f;
        }
    }

    @Override
    public float GetProductDiscount(PaymentMethodType type, Member member, Product product){
        Validate.notNull(product, "product should not be null");
        switch (type){
            case PAYMENT_METHOD_DEPOSIT: {
                Validate.notNull(member, "member should not be null");
                SaleDiscount discount = saleDiscountDAO.getDiscountByProduct(product, member.getMemberType());
                return discount.getDiscountValue();
            }
            default:
                return 1.0f;
        }
    }

    @Override
    public int GetProductBonusPoint(Product product, int count){
        Validate.notNull(product, "product should not be null");
        return product.getBonusePoint() * count;
    }

    @Override
    public int GetPackagePrice(ProductPackage pkg, int count){
        Validate.notNull(pkg, "package should not be null");
        return pkg.getUnitPrice() * count;
    }
}
