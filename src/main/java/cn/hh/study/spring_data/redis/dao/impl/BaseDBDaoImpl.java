package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import cn.hh.study.spring_data.redis.dao.IBaseDao;

@Repository("baseDBDao")
@DependsOn(value = { "HibernateConfigurationUtil" })
public class BaseDBDaoImpl<T, ID extends Serializable> implements IBaseDao<T, ID> {

	private Class<T> clz;

	@Override
	public void setClz(Class<T> clz) {
		this.clz = clz;
	}

	@Override
	public void setIdName(String idName) {
	}

	@Resource
	private SessionFactory sessionFactory;

	public BaseDBDaoImpl() {
		System.out.println("初始化.....BaseDBDaoImpl");
	}

	@Override
	public void save(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public T findOne(ID id) {
		return (T) sessionFactory.getCurrentSession().get(clz, id);
	}

}
