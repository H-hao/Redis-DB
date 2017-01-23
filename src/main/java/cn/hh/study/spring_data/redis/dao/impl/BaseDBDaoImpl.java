package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.hh.study.spring_data.redis.dao.IBaseDao;

//@Repository("baseDBDao")
@DependsOn(value = { "HibernateConfigurationUtil" })
// 注解不能注入到父类中sessionfactory,只能使用xml配置
public class BaseDBDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements IBaseDao<T, ID> {

	private Class<T> clz;

	@Override
	public void setClz(Class<T> clz) {
		this.clz = clz;
	}

	@Override
	public void setIdName(String idName) {
	}

	@Override
	public void setIdReadMethod(Method idReadMethod) {
	}

	public BaseDBDaoImpl() {
		System.out.println("初始化.....BaseDBDaoImpl");
	}

	@Override
	public void save(T t) {
		getHibernateTemplate().save(t);
	}

	@Override
	public T findOne(ID id) {
		return getHibernateTemplate().get(clz, id);
	}

}
