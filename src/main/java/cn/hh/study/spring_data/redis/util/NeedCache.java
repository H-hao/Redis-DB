package cn.hh.study.spring_data.redis.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注明是否需要使用缓存技术的注解,需要使用缓存的才进行配置
 * @author a
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedCache {
	boolean needCache() default true;
}
