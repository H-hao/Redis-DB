package cn.hh.study.spring_data.redis.dao;

import cn.hh.study.spring_data.redis.domain.User;

public interface IUserDao {

	void save(User user);

	User findOne(Long id);
}
