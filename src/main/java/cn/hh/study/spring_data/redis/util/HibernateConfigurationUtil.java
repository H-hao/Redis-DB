package cn.hh.study.spring_data.redis.util;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

@Component("HibernateConfigurationUtil")
@DependsOn("SpringApplicationContextUtil")
public class HibernateConfigurationUtil {
	private static Configuration configuration;

	public HibernateConfigurationUtil() {
		System.out.println("HibernateConfigurationUtil。。。初始化");
	}

	public static Configuration getConfiguration() {
		if (configuration == null) {
			synchronized (HibernateConfigurationUtil.class) {
				if (configuration == null) {
					// 取sessionFactory的时候要加上&
					LocalSessionFactoryBean factory = (LocalSessionFactoryBean) SpringApplicationContextUtil
							.getApplicationContext().getBean("&sessionFactory");
					configuration = factory.getConfiguration();
				}
			}
		}
		return configuration;
	}

	// 封装了每一个class映射
	private static <T> PersistentClass getPersistentClass(Class<T> clazz) {
		synchronized (HibernateConfigurationUtil.class) {
			PersistentClass pc = getConfiguration().getClassMapping(clazz.getSimpleName());
			if (pc == null) {
				configuration = configuration.addClass(clazz);
				pc = configuration.getClassMapping(clazz.getName());
			}
			return pc;
		}
	}

	// 获得实体类对应的表名
	public static <T> String getTableName(Class<T> clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	// 获得实体类对应表的主键字段名称
	public static <T> String getPKColumnName(Class<T> clazz) {
		return getPersistentClass(clazz).getTable().getPrimaryKey().getColumn(0).getName();
	}

	// 获得类属性对应的字段名
	public static <T> String getColumnName(Class<T> clazz, String propertyName) {
		String columnName = "";
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator<?> iterator = property.getColumnIterator();
		if (iterator.hasNext()) {
			Column column = (Column) iterator.next();
			columnName += column.getName();
		}
		return columnName;
	}
}
