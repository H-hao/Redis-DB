package cn.hh.study.spring_data.redis.test;

import java.net.URL;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExampleTest {

	// inject the actual template
	@Autowired
	private RedisTemplate<String, String> template;

	// inject the template as ListOperations
	// can also inject as Value, Set, ZSet, and HashOperations
	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	public void testAddLink(String userId, URL url) {
		listOps.leftPush(userId, url.toExternalForm());
		// or use template directly
		// redisTemplate.boundListOps(userId).leftPush(url.toExternalForm());
		listOps.leftPop("userId");
	}
}