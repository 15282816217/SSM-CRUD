package com.myssm.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myssm.bean.Department;
import com.myssm.bean.Employee;
import com.myssm.dao.DepartmentMapper;
import com.myssm.dao.EmployeeMapper;

/**
 * ����Dao��Ĺ���
 * @author Echo
 *
 */
public class MapperTest {
		@Test
		public void testCRUD() {
			
		// ����springIOC����
			ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
			DepartmentMapper departmentMapper=applicationContext.getBean(DepartmentMapper.class);
			EmployeeMapper employeeMapper=applicationContext.getBean(EmployeeMapper.class);
			SqlSession sqlSession=applicationContext.getBean(SqlSession.class);
			EmployeeMapper mapper= sqlSession.getMapper(EmployeeMapper.class);
			
			//���벿��
			departmentMapper.insertSelective(new Department(null,"������"));
			departmentMapper.insertSelective(new Department(null,"���Բ�"));
			//����Ա�����ݡ�����Ա������
			
//			employeeMapper.insertSelective(new Employee(null,"zzh","M","798322134@qq.com",1));
			//��������Ա����ʹ�ÿ���ִ������������sqlsession
			for(int i=0;i<100;i++) {
				String uid=UUID.randomUUID().toString().substring(0, 5)+i;
				mapper.insertSelective(new Employee(null,uid,"M",uid+"@qq.com",1));
			}
			System.out.println("�������");
		}
}
