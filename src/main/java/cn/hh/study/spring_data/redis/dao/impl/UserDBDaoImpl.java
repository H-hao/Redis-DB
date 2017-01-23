package cn.hh.study.spring_data.redis.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

@Repository("userDBDao")
public class UserDBDaoImpl extends HibernateDaoSupport implements IUserDao {

	@Override
	public void save(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public User findOne(Long id) {
		return getHibernateTemplate().get(User.class, id);
	}

}
