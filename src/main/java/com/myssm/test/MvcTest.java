package com.myssm.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.myssm.bean.Employee;

/**
 * ʹ��spring����ģ���ṩ�Ĳ���������
 * @author Echo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {
		@Autowired
		WebApplicationContext context;
		//����Mvc���󣬻�ȡ��������
		MockMvc mockMvc;
		@Before
		public void initMokcMvc() {
			mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
			
		}
		@Test
		public void testPage() throws Exception {
			
			//ģ��һ���������õ�����ֵ
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
		//����ɹ����������л���pageInfo;ȡ��pageInfo������֤
		MockHttpServletRequest httpServletRequest= result.getRequest();
		PageInfo pi= (PageInfo) httpServletRequest.getAttribute("PageInfo");
		System.out.println("��ǰҳ�룺"+pi.getPageNum());
		System.out.println("��ҳ�룺"+pi.getPages());
		System.out.println("�ܼ�¼����"+pi.getTotal());
		//��ȡԱ������
		List<Employee> list=pi.getList();
		for (Employee employee : list) {
			System.out.println("ID:"+employee.getEmpId()+"==>name:"+employee.getEmpName());
		}
		}
		
}
