/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.uc.level.one;

import com.spstudio.common.image.ImageUtils;
import com.spstudio.junit.JUnit4ClassRunner;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.MemberService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wewezhu
 */
@RunWith(JUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:com/spstudio/common/config/spring-service.xml","classpath:com/spstudio/modules/member/config/spring-member.xml"})  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
@Transactional
public class TestJUnitTest {
    private static Member member;
    
    @Autowired
    @Qualifier("memberService")
    MemberService memberService;
    
    public TestJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    //1. Add New Member
     @Test
     @Rollback(true)
     public void testAddNewMember() {
         member = new Member();
         member.setMemberName("TestName");
         member.setAddress("TestAddress");
         member.setWeixinId("TestWxId");
         member.setSex("Male");
         member.setMobile("149994033");
         
         byte[] uncompressByte = ImageUtils.loadFile();
         System.out.println(uncompressByte.length);
         byte[] compressByte = ImageUtils.compress(uncompressByte);
         System.out.println(compressByte.length);
         member.setProfilePicture(compressByte);
        memberService.addMember(member);
        Member findMember = memberService.findMemberByMemberId(member.getMemberId());
        byte[] picByte = findMember.getProfilePicture();
        
        byte[] uncompressed = ImageUtils.uncompress(picByte);
        ImageUtils.writeFile(uncompressed);
        assertNotNull(findMember);
        
     }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //@Test
     public void hello() {}
}
