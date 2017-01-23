package cn.hh.study.spring_data.redis.dao;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface IBaseDao<T, ID extends Serializable> {

	void setClz(Class<T> clz);

	void setIdName(String idName);

	void setIdReadMethod(Method idReadMethod);

	void save(T t);

	T findOne(ID id);

}
