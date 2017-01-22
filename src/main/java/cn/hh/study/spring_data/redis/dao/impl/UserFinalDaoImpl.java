package cn.hh.study.spring_data.redis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

@Repository("userFinalDao")
public class UserFinalDaoImpl implements IUserDao {
	@Autowired
	private IUserDao userRedisDao;

	@Autowired
	private IUserDao userDBDao;

	@Override
	public void save(User user) {
		// 增删改：先对缓存中数据进行修改,再同步到数据库中
		// 如果出现异常,通过redis的持久化恢复数据
		// redis 缓存中不应该有很重要的数据，或者说容易出现安全性问题的数据

		userRedisDao.save(user);

		userDBDao.save(user);
	}

	@Override
	public User findOne(Long id) {
		// 查询--先从缓存中获取,如果没有获取到,则从db中获取，并存储在缓存中
		// 从缓存中查询
		User user = userRedisDao.findOne(id);
		System.err.println("从缓存中查询----" + user);
		if (user == null) {
			// 从数据库中查询
			user = userDBDao.findOne(id);
			System.err.println("从数据库中查询----" + user);
			// 存储在缓存中
			userRedisDao.save(user);
			System.err.println("放入缓存中");
		}
		return user;
	}

}
