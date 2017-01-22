package cn.hh.study.spring_data.redis.service;

import java.io.Serializable;

public interface IBaseService<T, ID extends Serializable> {

	void save(T t);

	T findOne(ID id);
}
