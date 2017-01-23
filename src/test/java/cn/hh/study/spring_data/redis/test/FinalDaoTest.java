package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Test;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

/**
 * 使用 redisTemplate 进行数据的存取
 * @author a
 * 
 */
public class FinalDaoTest extends BaseTest {

	@Resource
	// resource 注解默认以名称（userFinalDao）查询依赖对象bean
	private IUserDao userFinalDao;

	@Test
	public void testRedisSet() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setUsername("xxx");
		user.setPassword("yy");
		userFinalDao.save(user);
	}

	@Test
	public void testRedisGet() throws Exception {
		User user = userFinalDao.findOne(4L);
		System.out.println(user);
	}
}
