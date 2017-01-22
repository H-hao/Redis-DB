package cn.hh.study.spring_data.redis.dao.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

//@Repository
public class UserRedisDaoImpl implements IUserDao {

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public void save(final User user) {
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				// 以一定的格式进行存储
				connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + user.getId()),
						redisTemplate.getStringSerializer().serialize(user.getUsername()));
				return null;
			}
		});
	}

	@Override
	public User findOne(final Long id) {
		return redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String name = redisTemplate.getStringSerializer().deserialize(value);
					User user = new User();
					user.setUsername(name);
					user.setId(id);
					return user;
				}
				return null;
			}
		});
	}

}
