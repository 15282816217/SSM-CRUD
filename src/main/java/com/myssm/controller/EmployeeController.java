package com.myssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myssm.bean.Employee;
import com.myssm.bean.Msg;
import com.myssm.service.EmployeeService;
@Controller
public class EmployeeController {	
		@Autowired
		EmployeeService employeeService;
		
		@ResponseBody
		@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
		public Msg deleteEmp(@PathVariable("ids")String ids) {
			if(ids.contains("-")) {
				List<Integer> del_ids=new ArrayList<Integer>();
				String[] str_ids=ids.split("-");
				for (String string : str_ids) {
					del_ids.add(Integer.parseInt(string));
				}
				employeeService.deleteBatch(del_ids);
				return Msg.sucess();
			}else {
				Integer id=Integer.parseInt(ids);
				employeeService.deleteEmp(id);
				return Msg.sucess();
			}
			
		}
		
		/**
		 * Ա����Ϣ����
		 * @param employee
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/emps/{empId}",method=RequestMethod.PUT)
		public Msg saveEmp(Employee employee) {
			employeeService.updateEmp(employee);
			return Msg.sucess();
		}
		/**
		 * ���Ա����Ϣ
		 * @param id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
		public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee=employeeService.getEmp(id);
			return Msg.sucess().add("emp", employee);
		}
		/**
		 * У���û����Ƿ����
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/checkUser")
		public Msg checkUser(@RequestParam("empName")String empName) {
			//У���û����Ƿ���Ϲ淶
			String regex="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
			if(!empName.matches(regex)) {
				return Msg.fail().add("va_msg", "�û���������2-5λ���Ļ�6-16λӢ�ĺ����ֵ����");
			};
			boolean b=employeeService.checkUser(empName);
			if(b) {
				return Msg.sucess();
			}else {
				return Msg.fail().add("va_msg", "�û����ظ�");
			}
		}
		/**
		 * ������ύ��Ա����Ϣ
		 */
		@RequestMapping(value="/emp",method=RequestMethod.POST)
		@ResponseBody
		public Msg saveEmp(@Valid Employee employee,BindingResult result) {
			if(result.hasErrors()) {
				//У��ʧ�ܣ���ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
				Map<Object, Object> map=new HashMap<Object, Object>();
				List<FieldError> fieldErrors = result.getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					System.out.println("�����ֶΣ�"+fieldError.getField());
					System.out.println("������Ϣ��"+fieldError.getDefaultMessage());
					map.put(fieldError.getField(),fieldError.getDefaultMessage());
				}
				return Msg.fail().add("errorFields", map);
			}else {
				employeeService.saveAll(employee);
				return Msg.sucess();
			}
			
		}	
		
		/**
		 * ��ѯԱ�����ݡ���ҳ��ѯ����Ҫ����service�㣩
		 * @return
		 */
		@RequestMapping("/emps")
		@ResponseBody
		public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn) {
			//�ڲ�ѯǰʹ��pageHelper;����ҳ���Լ�ÿҳ��ѯ������¼
			PageHelper.startPage(pn, 5);			
		List<Employee> emps=employeeService.getAll();
		//�����Ժ�ʹ��pageInfo��װ���ݼ�¼;���Դ���������ʾ��ҳ��
		PageInfo page=new PageInfo(emps,5);
		return Msg.sucess().add("pageInfo", page);
		}
		
		
		//@RequestMapping("/emps")
//		public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,Model model) {
//			
//			//�ڲ�ѯǰʹ��pageHelper;����ҳ���Լ�ÿҳ��ѯ������¼
//			PageHelper.startPage(pn, 5);
//		List<Employee> emps=employeeService.getAll();
//		//�����Ժ�ʹ��pageInfo��װ���ݼ�¼;���Դ���������ʾ��ҳ��
//		PageInfo page=new PageInfo(emps,5);
//		model.addAttribute("pageInfo",page);		
//		
//			return "list";
//		}
}
