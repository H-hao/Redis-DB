package cn.hh.study.spring_data.redis.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hh.study.spring_data.redis.dao.IDepartmentDao;
import cn.hh.study.spring_data.redis.domain.Department;
import cn.hh.study.spring_data.redis.service.IDepartmentService;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

	@Resource
	IDepartmentDao departmentDao;

	@Override
	public void save(Department t) {
		departmentDao.save(t);
	}

	@Override
	public Department findOne(Long id) {
		return departmentDao.findOne(id);
	}

}
