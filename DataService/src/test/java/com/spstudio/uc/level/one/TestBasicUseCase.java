/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.uc.level.one;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.entity.PackageProductMapping;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sales.entity.Sales;
import com.spstudio.modules.sales.service.SaleService;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author wewezhu
 * This test case will cover a very typical test case of WorkOrderOnline Service
 * 1. Add New Member
 * 2. Add New Product
 * 2.a Add New ProductPackage
 * 3. Add New Sales, verify member asset has create as well.
 * 4. Add New member Asset.
 * 5. Add new Work Order
 * 6. Confirm New Work Order
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBasicUseCase {
    
    private static SessionFactory sessionFactory;
    
    private static ProductService productService;

    private static MemberService memeberService;
    
    private static SaleService salesService;
    
    private static WorkOrderService workOrderService; 
    
     private static Product product;
     
     private static Member member;
     
     private static MemberType memberType;
     
     private static PackageProductMapping packageProductMapping;
     
     private static ProductPackage productPackage;
     
     private static MemberAsset asset;
     
     private static Sales saleRec;
     
     private static WorkOrder workOrder;
    
    public TestBasicUseCase() {
    }
    
    @BeforeClass
    public static void setUpClass() {
         ApplicationContext factory = new FileSystemXmlApplicationContext( new String[]{"src/main/java/com/spstudio/common/config/spring-*.xml","src/main/java/com/spstudio/modules/*/config/spring-*.xml"});
        sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        productService = (ProductService)factory.getBean("productService");
        memeberService = (MemberService)factory.getBean("memberService");
        salesService = (SaleService)factory.getBean("saleService");
        workOrderService = (WorkOrderService)factory.getBean("workOrderService");
    }
    
    @AfterClass
    public static void tearDownClass() {
        //workOrderService.zapWorkOrder(workOrder);
        //salesService.zapSalesRecord(saleRec);
        //memberAssetService.zapMemberAsset(asset);
        //productService.zapProduct(product);
        //memeberService.zapMember(member);
        productService.zapProductPackage(productPackage);
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    //1. Add New Member
     //@Test
     public void testAddNewMember() {
         member = new Member();
         member.setMemberName("TestName");
         member.setAddress("TestAddress");
         member.setWeixinId("TestWxId");
         member.setSex("Male");
         member.setMobile("149994033");
         
        memeberService.addMember(member);
        Member findMember = memeberService.findMemberByMemberId(member.getMemberId());
        assertNotNull(findMember);
        
     }
     
     //2. Add New Product
     //@Test
     public void testAddNewProduct() {
         product = new Product();
         product.setProductName("TestProduct");
            product.setUnitPrice(280);
        product.setUnitBounce(1);
        product.setDescription("TestDescription");
        product.setBonusePoint(1);
         
        productService.addProduct(product);
        Product findProduct = productService.findProductByProductId(product.getProductId());
        assertNotNull(findProduct);
        
     }
     
     //* 2.a Add New ProductPackage
      //@Test
     public void testAddNewProductPackage() {
         packageProductMapping = new PackageProductMapping();
         packageProductMapping.setProduct(product);
         packageProductMapping.setCount(5);
         
         Set<PackageProductMapping> packageProductMappings = new LinkedHashSet<PackageProductMapping>();
         packageProductMappings.add(packageProductMapping);
         
        //productPackage = productService.addProductPackage(packageProductMappings,49, "TestPackageDesc", "TestPackage", new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()));
        //ProductPackage findProductPackage = productService.findProductPackageByPackageId(productPackage.getProductPackageId());
        //assertNotNull(findProductPackage);
          assertNotNull(null);
     }
     
     //3. Add New Sales, verify member asset has create as well.
     //@Test
     public void testAddNewSale(){
         /*
         saleRec = new Sales();
         saleRec.setMember(member);
         saleRec.setProduct(product);
         saleRec.setSaler("TestSaler");
         saleRec.setSalesCount(100);

         saleRec = salesService.addSalesRecord(saleRec);
         assertNotNull(saleRec);
          */
         
     }
     
     //4. Add New member Asset
     //@Test
     public void testAddNewMemberSet(){
         
//         asset = memeberService.addMemeberDepositAsset(member, 1000);
//         assertNotNull(asset);
     }
     
     //@Test
     public void testAddNewWorkOrder(){
//         workOrder = new WorkOrder();
//         workOrder.setConsumeProduct(product);
//         Set<Member> customers = new LinkedHashSet<Member>();
//         customers.add(member);
//         workOrder.setCustomers(customers);
//         workOrder.setCreateDate(new Date(System.currentTimeMillis()));
//         workOrderService.addWorkOrder(workOrder);
//
//         assertNotNull(workOrder);
     }
}
