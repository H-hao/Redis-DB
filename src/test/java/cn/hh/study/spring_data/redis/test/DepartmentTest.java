package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.hh.study.spring_data.redis.dao.IDepartmentDao;
import cn.hh.study.spring_data.redis.domain.Department;

public class DepartmentTest extends BaseTest {

	@Resource
	IDepartmentDao deptFinalDao;

	@Before
	public void newApplicationContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		// ApplicationContext contextFromHolder =
		// SpringApplicationContextUtil.getApplicationContext();
	}

	@Test
	public void testSave() throws Exception {
		Department department = new Department();
		department.setName("人事部");
		deptFinalDao.save(department);
	}
}
