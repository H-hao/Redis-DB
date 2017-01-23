# spring-data-redis

* spring-data-redis 的集成
* 数据库访问与缓访问的集成


还存在问题：<br>
1. baseRedisDao和baseDBDao无法实例化,应该是他们与baseFinalDao之间关系的原因（去掉他们之间的关系）
1. applicationContext获取不到----添加了@Component
<br>
第一个问题<br>
需要将baseDbDao和baseRedisDao整合成一个baseFinalDao,final类中需要结合使用另两个dao的方法
第二个问题<br>
测试类中无法实例化util包下的类,要首先有实例化的applicationContext<br>
* ApplicationContetAware Interface to be implemented by any object that wishes to be notified of the ApplicationContext that it runs in.
* ApplicationContextHolder will now have the reference of current ApplicationContext.
<br>
解决：因为这两个util（hibernate和spring）都未实例化，提示没有相应的 bean name，所以为 component 添加对应的名称 
<br>
如：@Component("SpringApplicationContextUtil")和@Component("HibernateConfigurationUtil")


是否使用缓存的配置问题（needCache属性）<br>
使用 properties 来决定要使用缓存的类，多个类已逗号分隔


还存在的问题：clz、idName、idReadMethod的重复性问题