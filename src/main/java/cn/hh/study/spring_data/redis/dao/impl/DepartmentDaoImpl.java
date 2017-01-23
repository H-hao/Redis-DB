package cn.hh.study.spring_data.redis.dao.impl;

import org.springframework.stereotype.Repository;

import cn.hh.study.spring_data.redis.dao.IDepartmentDao;
import cn.hh.study.spring_data.redis.domain.Department;
import cn.hh.study.spring_data.redis.util.NeedCache;

@Repository
@NeedCache
public class DepartmentDaoImpl extends BaseFinalDaoImpl<Department, Long> implements IDepartmentDao {

	public DepartmentDaoImpl() {
		System.out.println("DepartmentDaoImpl.....实例化");
	}

}
