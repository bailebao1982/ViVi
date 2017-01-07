package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.product.entity.PackageProductMapping;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.service.ProductService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageJsonBeanUtil {
    public static PackageJsonBean toJsonBean(ProductPackage pkg) {
        PackageJsonBean packageJsonBean = new PackageJsonBean();

        packageJsonBean.setPackage_id(pkg.getProductPackageId());
        packageJsonBean.setPackage_name(pkg.getPackageName());
        packageJsonBean.setPackage_serialno(pkg.getSerialNo());
        packageJsonBean.setPackage_price(String.valueOf(pkg.getUnitPrice()));
        if(pkg.getEffectiveStartDate() != null)
            packageJsonBean.setPackage_start_date(pkg.getEffectiveStartDate().toString());
        if(pkg.getEffectiveEndDate() != null)
            packageJsonBean.setPackage_end_date(pkg.getEffectiveEndDate().toString());
        packageJsonBean.setPackage_note(pkg.getDescription());
        Iterator<PackageProductMapping> prdtIter = pkg.getProductMappingSet().iterator();

        List<PackageProductJsonBean> packageProducts = new ArrayList<PackageProductJsonBean>();
        while(prdtIter.hasNext()){
            PackageProductMapping productMapping = prdtIter.next();
            ProductJsonBean prdtJsonBean = ProductJsonBeanUtil.toJsonBean(productMapping.getProduct());

            PackageProductJsonBean pgkPrdtBean = new PackageProductJsonBean();
            pgkPrdtBean.setProduct_count(productMapping.getCount());
            pgkPrdtBean.setProduct(prdtJsonBean);

            packageProducts.add(pgkPrdtBean);
        }
        packageJsonBean.setPackage_products(packageProducts);

        return packageJsonBean;
    }

    public static ProductPackage toEntityBean(PackageJsonBean pkgJsonBean, ProductService service){
        ProductPackage pkgBean = new ProductPackage();

        pkgBean.setPackageName(pkgJsonBean.getPackage_name());
        pkgBean.setDescription(pkgJsonBean.getPackage_note());
        try{
            pkgBean.setUnitPrice(Integer.valueOf(pkgJsonBean.getPackage_price()));
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return null;
        }

        pkgBean.setSerialNo(pkgJsonBean.getPackage_serialno());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(pkgJsonBean.getPackage_start_date() != null &&
           !pkgJsonBean.getPackage_start_date().isEmpty()){
            try {
                Date startDate = formatter.parse(pkgJsonBean.getPackage_start_date());
                pkgBean.setEffectiveStartDate(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        if(pkgJsonBean.getPackage_end_date() != null &&
                !pkgJsonBean.getPackage_end_date().isEmpty()){
            try{
                Date endDate = formatter.parse(pkgJsonBean.getPackage_end_date());
                pkgBean.setEffectiveEndDate(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        List<PackageProductJsonBean> productsList = pkgJsonBean.getPackage_products();
        if(productsList != null &&
           productsList.size() > 0){
            Set<PackageProductMapping> prdtMapSet = new HashSet<PackageProductMapping>();
            for (PackageProductJsonBean pkgPrdtJsonBean : productsList){
                ProductJsonBean prdtJsonBean = pkgPrdtJsonBean.getProduct();
                Product product = null;
                if(prdtJsonBean.getProduct_id() != null &&
                   !prdtJsonBean.getProduct_id().isEmpty()){
                    product = service.findProductByProductId(prdtJsonBean.getProduct_id());
                }
                if(product == null &&
                   prdtJsonBean.getProduct_serialno() != null &&
                   !prdtJsonBean.getProduct_serialno().isEmpty()){
                    product = service.findProductByProductSerialno(prdtJsonBean.getProduct_serialno());
                }

                if(product == null) continue;

                PackageProductMapping mapping = new PackageProductMapping();
                mapping.setProduct(product);
                mapping.setProductPackage(pkgBean);
                mapping.setCount(pkgPrdtJsonBean.getProduct_count());

                prdtMapSet.add(mapping);
            }

            pkgBean.setProductMappingSet(prdtMapSet);
        }

        return pkgBean;
    }
}
