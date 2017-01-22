package cn.hh.study.spring_data.redis.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

@Repository("userDBDao")
public class UserDBDaoImpl implements IUserDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User findOne(Long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

}
