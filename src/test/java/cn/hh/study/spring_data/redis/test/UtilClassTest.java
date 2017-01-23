package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Test;

import cn.hh.study.spring_data.redis.util.SpringApplicationContextUtil;

public class UtilClassTest extends BaseTest {

	@Resource
	SpringApplicationContextUtil applicationContextUtil;

	@Test
	public void testApplication() throws Exception {
		System.out.println(applicationContextUtil);
	}
}
