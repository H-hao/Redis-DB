package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;

import cn.hh.study.spring_data.redis.dao.IBaseDao;

public abstract class BaseAbstractDao<T, ID extends Serializable> implements IBaseDao<T, ID> {

	protected Class<T> clz;

	public void setClz(Class<T> clz) {
		this.clz = clz;
	}

	protected String idName;

	public void setIdName(String idName) {
		this.idName = idName;
	}
}
