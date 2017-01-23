package cn.hh.study.spring_data.redis.test;

import javax.annotation.Resource;

import org.junit.Test;

import cn.hh.study.spring_data.redis.util.CachePropUtil;

public class CachaPropTest extends BaseTest {

	@Resource
	CachePropUtil cachePropUtil;

	@Test
	public void testCacheProp() throws Exception {
		Object propValue = CachePropUtil.getPropValue("needCache");
		if (propValue != null) {
			System.out.println("propValue --" + propValue.toString());
		} else {
			System.out.println("没有此属性对应的值");
		}
	}
}
