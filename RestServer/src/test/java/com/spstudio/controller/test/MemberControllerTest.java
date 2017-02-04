/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.controller.test;

import com.spstudio.modules.member.controller.MemberController;
import com.spstudio.modules.member.service.MemberService;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.web.context.WebApplicationContext;
/**
 *
 * @author wewezhu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:com/spstudio/config/spring-common.xml","classpath:com/spstudio/config/spring-mvc.xml"})
@WebAppConfiguration
public class MemberControllerTest {
    private MockMvc mockMvc;
    
    @Resource(name="memberService")
    private MemberService memberService2;
       
    @Resource
    MemberController memberController;
    
     @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }
    
     @Test
    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
        assertNotNull(memberService2);
        mockMvc.perform(get("/member/list_member_type"))
                .andExpect(status().isOk());
        /** mockMvc.perform(get("/"))
                 .andExpect(status().isOk())
                 .andExpect(view().name("todo/list"))
                 .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
                 .andExpect(model().attribute("todos", hasSize(2)))
                 .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("title", is("Foo"))
                        )
                )))
                 .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("title", is("Bar"))
                        )
                )));**/
    }
}
