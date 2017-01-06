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

        packageJsonBean.setId(pkg.getProductPackageId());
        packageJsonBean.setName(pkg.getPackageName());
        packageJsonBean.setSerialno(pkg.getSerialNo());
        packageJsonBean.setPrice(String.valueOf(pkg.getUnitPrice()));
        packageJsonBean.setStart_date(pkg.getEffectiveStartDate().toString());
        packageJsonBean.setEnd_date(pkg.getEffectiveEndDate().toString());
        packageJsonBean.setNote(pkg.getDescription());
        Iterator<PackageProductMapping> prdtIter = pkg.getProductMappingSet().iterator();

        List<PackageProductJsonBean> packageProducts = new ArrayList<PackageProductJsonBean>();
        while(prdtIter.hasNext()){
            PackageProductMapping productMapping = prdtIter.next();
            ProductJsonBean prdtJsonBean = ProductJsonBeanUtil.toJsonBean(productMapping.getProduct());

            PackageProductJsonBean pgkPrdtBean = new PackageProductJsonBean();
            pgkPrdtBean.setCount(productMapping.getCount());
            pgkPrdtBean.setProduct(prdtJsonBean);

            packageProducts.add(pgkPrdtBean);
        }
        packageJsonBean.setProducts(packageProducts);

        return packageJsonBean;
    }

    public static ProductPackage toEntityBean(PackageJsonBean pkgJsonBean, ProductService service){
        ProductPackage pkgBean = new ProductPackage();

        pkgBean.setPackageName(pkgJsonBean.getName());
        pkgBean.setDescription(pkgJsonBean.getNote());
        try{
            pkgBean.setUnitPrice(Integer.valueOf(pkgJsonBean.getPrice()));
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return null;
        }

        pkgBean.setSerialNo(pkgJsonBean.getSerialno());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(pkgJsonBean.getStart_date() != null &&
           !pkgJsonBean.getStart_date().isEmpty()){
            try {
                Date startDate = formatter.parse(pkgJsonBean.getStart_date());
                pkgBean.setEffectiveStartDate(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        if(pkgJsonBean.getEnd_date() != null &&
                !pkgJsonBean.getEnd_date().isEmpty()){
            try{
                Date endDate = formatter.parse(pkgJsonBean.getEnd_date());
                pkgBean.setEffectiveEndDate(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        List<PackageProductJsonBean> productsList = pkgJsonBean.getProducts();
        if(productsList != null &&
           productsList.size() > 0){
            Set<PackageProductMapping> prdtMapSet = new HashSet<PackageProductMapping>();
            for (PackageProductJsonBean pkgPrdtJsonBean : productsList){
                Product product = service.findProductByProductId(pkgPrdtJsonBean.getProduct().getProduct_id());

                PackageProductMapping mapping = new PackageProductMapping();
                mapping.setProduct(product);
                mapping.setProductPackage(pkgBean);
                mapping.setCount(pkgPrdtJsonBean.getCount());

                prdtMapSet.add(mapping);
            }

            pkgBean.setProductMappingSet(prdtMapSet);
        }

        return pkgBean;
    }
}
