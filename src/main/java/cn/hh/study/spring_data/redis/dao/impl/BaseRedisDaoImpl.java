package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import cn.hh.study.spring_data.redis.dao.IBaseDao;

@Repository("baseRedisDao")
@DependsOn(value = { "HibernateConfigurationUtil" })
public class BaseRedisDaoImpl<T, ID extends Serializable> implements IBaseDao<T, ID> {
	// 以一定的格式进行存储(对象类型:对象主键:对象json)
	// clz.getSimpleName+":"+idName+
	// 存储单个对象时的key

	private Class<T> clz;

	public void setClz(Class<T> clz) {
		this.clz = clz;
	}

	private String idName;

	@Override
	public void setIdName(String idName) {
		this.idName = idName;
	}

	private Method idReadMethod;

	@Override
	public void setIdReadMethod(Method idReadMethod) {
		this.idReadMethod = idReadMethod;
	}

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	public BaseRedisDaoImpl() {
		System.out.println("初始化.....BaseRedisDaoImpl");
	}

	private Object getIdValue(T t) {
		// 通过返回获取主键值
		Object object = null;
		try {
			object = idReadMethod.invoke(t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public void save(final T t) {
		final String singleObjkey = clz.getSimpleName() + ":" + idName + ":";
		redisTemplate.execute(new RedisCallback<Void>() {

			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				// 保存时还应该添加上主键
				// Department:id:2
				Object keyValue = getIdValue(t);
				if (keyValue != null) {
					connection.set(redisTemplate.getStringSerializer().serialize(singleObjkey + keyValue),
							redisTemplate.getStringSerializer().serialize(new Gson().toJson(t)));
				}
				return null;
			}
		});
	}

	@Override
	public T findOne(final ID id) {
		final String singleObjkey = clz.getSimpleName() + ":" + idName + ":";
		System.out.println("从缓存中查询数据的依据 -- " + singleObjkey + id);
		return (T) redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(singleObjkey + id);
				System.err.println("查询缓存的 key -- " + new String(key));
				Set<byte[]> keys = connection.keys("*".getBytes());
				for (Iterator<byte[]> iterator = keys.iterator(); iterator.hasNext();) {
					byte[] bs = (byte[]) iterator.next();
					System.out.println("迭代所有的 key -- " + new String(bs));
				}
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String json = redisTemplate.getStringSerializer().deserialize(value);
					return new Gson().fromJson(json, clz);
				}
				return null;
			}
		});
	}

}
