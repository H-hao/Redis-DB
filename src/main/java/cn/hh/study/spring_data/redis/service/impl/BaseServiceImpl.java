package cn.hh.study.spring_data.redis.service.impl;

import java.io.Serializable;

import cn.hh.study.spring_data.redis.dao.IBaseDao;
import cn.hh.study.spring_data.redis.service.IBaseService;

public class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {

	private Class<T> clz; // 泛型类型

	private String idName;// 主键名称

	private IBaseDao<T, ID> baseFinalDao;

	@Override
	public void save(T t) {
		baseFinalDao.save(t);
	}

	@Override
	public T findOne(ID id) {
		return baseFinalDao.findOne(id);
	}

}
