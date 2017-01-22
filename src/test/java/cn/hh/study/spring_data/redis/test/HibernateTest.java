package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.hh.study.spring_data.redis.dao.IUserDao;
import cn.hh.study.spring_data.redis.domain.User;

/**
 * 使用 redisTemplate 进行数据的存取
 * @author a
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HibernateTest {

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Test
	public void testRedisSet() throws Exception {
		User user = new User();
		// user.setId(1L);
		user.setUsername("zz");
		user.setPassword("ss");
		userDao.save(user);

	}

	@Test
	public void testRedisGet() throws Exception {
		User user = userDao.findOne(1L);
		System.out.println(user);
	}
}