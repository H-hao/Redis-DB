package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import cn.hh.study.spring_data.redis.dao.IBaseDao;
import cn.hh.study.spring_data.redis.util.HibernateConfigurationUtil;

//@Repository("baseFinalDao")
//@DependsOn(value = { "baseDBDao", "baseRedisDao", "HibernateConfigurationUtil" })
public abstract class BaseFinalDaoImpl<T, ID extends Serializable> implements IBaseDao<T, ID> {

	// 是否需要使用缓存 -- 可以通过构造方法类配置,或者通过配置文件来进行配置
	protected boolean needCache = false;

	private Class<T> clz;

	private String idName;

	private IBaseDao<T, Serializable> baseDBDao;

	private IBaseDao<T, Serializable> baseRedisDao;

	public BaseFinalDaoImpl() {
		System.out.println("初始化。。。。baseFinaldaoIml");
		// ------------------初始化为 clz 赋值
		// 获取当前运行时类
		Class<?> clazz = getClass();
		// 获取父类( 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。)
		Type superclass = clazz.getGenericSuperclass();
		// 获取父类的泛型的类型
		if (superclass instanceof ParameterizedType) {
			System.out.println(((ParameterizedType) superclass).getActualTypeArguments()[0]);
			clz = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
		}
		System.out.println("获取到泛型类型名称------" + clz.getSimpleName());
		// ------------------初始化为 idName 赋值
		// hibernate 的 configuration 对象
		idName = HibernateConfigurationUtil.getPKColumnName(clz);
		System.out.println("获取到主键名称------" + idName);
	}

	@Override
	public void save(T t) {
		// 增删改：先对缓存中数据进行修改,再同步到数据库中
		// 如果出现异常,通过redis的持久化恢复数据
		// redis 缓存中不应该有很重要的数据，或者说容易出现安全性问题的数据
		if (needCache) {
			baseRedisDao.save(t);
		}
		baseDBDao.save(t);
	}

	@Override
	public T findOne(ID id) {
		// 查询--先从缓存中获取,如果没有获取到,则从db中获取，并存储在缓存中
		// 从缓存中查询
		T t = null;
		if (needCache) {
			t = baseRedisDao.findOne(id);
			System.err.println("从缓存中查询----" + t);
		}
		if (t == null) {
			// 从数据库中查询
			t = baseDBDao.findOne(id);
			System.err.println("从数据库中查询----" + t);
			if (needCache) {
				// 存储在缓存中
				baseRedisDao.save(t);
				System.err.println("放入缓存中");
			}
		}
		return t;
	}

	// --------------------getter/setter------------------------

	@Override
	public void setClz(Class<T> clz) {
		this.clz = clz;
	}

	@Override
	public void setIdName(String idName) {
	}

	public void setNeedRedis(boolean needRedis) {
		this.needCache = needRedis;
	}

	@Resource
	public void setBaseRedisDao(IBaseDao<T, Serializable> baseRedisDao) {
		this.baseRedisDao = baseRedisDao;
		this.baseRedisDao.setClz(clz);
		this.baseRedisDao.setIdName(idName);
	}

	@Resource
	public void setBaseDBDao(IBaseDao<T, Serializable> baseDBDao) {
		this.baseDBDao = baseDBDao;
		this.baseDBDao.setClz(clz);
	}

}
