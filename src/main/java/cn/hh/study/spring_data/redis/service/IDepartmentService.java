package cn.hh.study.spring_data.redis.service;

import cn.hh.study.spring_data.redis.domain.Department;

public interface IDepartmentService {
	void save(Department t);

	Department findOne(Long id);
}
