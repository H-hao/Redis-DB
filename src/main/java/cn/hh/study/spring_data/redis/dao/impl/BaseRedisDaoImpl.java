package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;

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

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	public BaseRedisDaoImpl() {
		System.out.println("初始化.....BaseRedisDaoImpl");
	}

	@Override
	public void save(final T t) {
		final String singleObjkey = clz.getSimpleName() + ":" + idName + ":";
		redisTemplate.execute(new RedisCallback<Void>() {

			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(redisTemplate.getStringSerializer().serialize(singleObjkey),
						redisTemplate.getStringSerializer().serialize(new Gson().toJson(t)));
				return null;
			}
		});
	}

	@Override
	public T findOne(final ID id) {
		final String singleObjkey = clz.getSimpleName() + ":" + idName + ":";
		return (T) redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(singleObjkey + id);
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
