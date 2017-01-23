package cn.hh.study.spring_data.redis.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class CachePropUtil {

	private static Properties prop;

	static {
		try {
			prop = new Properties();
			prop.load(ClassLoader.getSystemResourceAsStream("cache.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CachePropUtil() {
		System.out.println("CachePropUtil...实例化");
	}

	public static List<String> getPropValue(String name) {
		String allNeedCache = prop.getProperty(name);
		String[] needCaches = allNeedCache.split(",");
		return new ArrayList<>(Arrays.asList(needCaches));
	}

}
