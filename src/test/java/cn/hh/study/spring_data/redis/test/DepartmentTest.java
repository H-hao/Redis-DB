package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Test;

import cn.hh.study.spring_data.redis.domain.Department;
import cn.hh.study.spring_data.redis.service.IDepartmentService;

public class DepartmentTest extends BaseTest {

	@Resource
	IDepartmentService departmentService;

	@Test
	public void testSave() throws Exception {
		Department department = new Department();
		department.setName("行政部");
		departmentService.save(department);
	}

	@Test
	public void testGet() throws Exception {
		Department department = departmentService.findOne(3L);
		System.out.println(department);
	}
}
