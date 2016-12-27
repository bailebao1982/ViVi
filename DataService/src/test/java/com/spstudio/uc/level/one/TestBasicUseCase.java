/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.uc.level.one;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.member.service.MemberTypeService;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sales.entity.Sales;
import com.spstudio.modules.sales.service.SaleService;
import com.spstudio.modules.workorder.entity.WorkOrder;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    
    private static MemberTypeService memberTypeService;
    
    private static MemberService memeberService;
    
    private static SaleService salesService;
    
     private static Product product;
     
     private static Member member;
     
     private static MemberType memberType;
     
     private static Sales saleRec;
     
     private static WorkOrder workOrder;
    
    public TestBasicUseCase() {
    }
    
    @BeforeClass
    public static void setUpClass() {
         ApplicationContext factory = new FileSystemXmlApplicationContext( new String[]{"src/main/java/com/spstudio/common/config/spring-*.xml","src/main/java/com/spstudio/modules/*/config/spring-*.xml"});
        sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        productService = (ProductService)factory.getBean("productService");
        memberTypeService = (MemberTypeService)factory.getBean("memberTypeService");
        memeberService = (MemberService)factory.getBean("memberService");
        salesService = (SaleService)factory.getBean("saleService");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
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
     @Test
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
     @Test
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
     
     @Test
     public void testAddSale(){
         saleRec = new Sales();
         saleRec.setMember(member);
         saleRec.setProduct(product);
         saleRec.setSaler("TestSaler");
         saleRec.setSalesCount(100);
         
         saleRec = salesService.addSalesRecord(saleRec);
         
         
         
     }
     
}
