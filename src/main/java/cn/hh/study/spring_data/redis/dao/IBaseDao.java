package cn.hh.study.spring_data.redis.dao;

import java.io.Serializable;

public interface IBaseDao<T, ID extends Serializable> {

	void setClz(Class<T> clz);

	void setIdName(String idName);

	void save(T t);

	T findOne(ID id);

}
