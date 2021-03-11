package com.soyatec.sword.config;

import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.soyatec.sword.**.mapper")
public class ApplicationConfig {
	@Bean
	public RedisCacheManager getCacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		RedisManager redisManager = new RedisManager();
		redisCacheManager.setRedisManager(redisManager);
		return redisCacheManager;
	}

}
