package com.soyatec.sword.framework.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.framework.shiro.realm.UserRealm;
import com.soyatec.sword.framework.shiro.session.OnlineSessionFactory;
import com.soyatec.sword.framework.shiro.web.filter.AdminUserFilter;
import com.soyatec.sword.framework.shiro.web.filter.LogoutFilter;
import com.soyatec.sword.framework.shiro.web.filter.RestUserFilter;
import com.soyatec.sword.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.soyatec.sword.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.soyatec.sword.framework.shiro.web.session.CustomWebSessionManager;
import com.soyatec.sword.framework.shiro.web.session.SpringSessionValidationScheduler;

/**
 * 权限配置加载
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Configuration
public class ShiroConfig {

	private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

	private static final String CACHE_KEY = "shiro:cache:";
	private static final String SESSION_KEY = "shiro:session:";
	// Redis配置
	@Value("${spring.redis.host:localhost}")
	private String host;
	@Value("${spring.redis.port:6379}")
	private int port;
	@Value("${spring.redis.database:1}")
	private int database;
	@Value("${spring.redis.timeout:60000}")
	private int timeout;
	@Value("${spring.redis.password:}")
	private String password;

	/**
	 * Session超时时间，单位为毫秒（默认30分钟）
	 */
	@Value("${shiro.session.expireTime}")
	private int expireTime;

	/**
	 * 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
	 */
	@Value("${shiro.session.validationInterval}")
	private int validationInterval;

	/**
	 * 同一个用户最大会话数
	 */
	@Value("${shiro.session.maxSession}")
	private int maxSession;

	/**
	 * 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
	 */
	@Value("${shiro.session.kickoutAfter}")
	private boolean kickoutAfter;

	/**
	 * 验证码开关
	 */
	@Value("${shiro.user.captchaEnabled}")
	private boolean captchaEnabled;

	/**
	 * 验证码类型
	 */
	@Value("${shiro.user.captchaType}")
	private String captchaType;

	/**
	 * 设置Cookie的域名
	 */
	@Value("${shiro.cookie.domain}")
	private String domain;

	/**
	 * 设置cookie的有效访问路径
	 */
	@Value("${shiro.cookie.path}")
	private String path;

	/**
	 * 设置HttpOnly属性
	 */
	@Value("${shiro.cookie.httpOnly}")
	private boolean httpOnly;

	/**
	 * 设置Cookie的过期时间，秒为单位
	 */
	@Value("${shiro.cookie.maxAge}")
	private int maxAge;

	/**
	 * 设置cipherKey密钥
	 */
	@Value("${shiro.cookie.cipherKey}")
	private String cipherKey;

	/**
	 * 登录地址
	 */
	@Value("${shiro.user.loginUrl}")
	private String loginUrl;

	/**
	 * 权限认证失败地址
	 */
	@Value("${shiro.user.unauthorizedUrl}")
	private String unauthorizedUrl;

	@Autowired
	private ApplicationContext context;

	@PostConstruct
	public void init() {
		log.info("Init ShiroConfig");
		try {
			final Class<?> type = getClass().getClassLoader()
					.loadClass("at.pollux.thymeleaf.shiro.dialect.ShiroDialect");
			if (type != null && context instanceof AnnotationConfigServletWebServerApplicationContext) {
				((AnnotationConfigServletWebServerApplicationContext) context).registerBean("shiroDialect", type);
			}
			log.debug("Thymeleaf shiro extension started");
		} catch (final Exception e) {
			log.debug("Not support thymeleaf");
		}
	}

	@Bean
	public RedisManager redisManager() {
		log.info("redis={}:{}, password={}", host, port, password);
		final RedisManager redisManager = new RedisManager();
		redisManager.setHost(host + ":" + port);
		redisManager.setDatabase(database);
		redisManager.setTimeout(timeout);
		if (StringUtils.isNotEmpty(password)) {
			redisManager.setPassword(password);
		}
		return redisManager;
	}

	/**
	 * 缓存管理器 使用Ehcache实现
	 */
	@Bean
	public RedisCacheManager cacheManager() {
		final RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		redisCacheManager.setKeyPrefix(CACHE_KEY);
		// 配置缓存的话要求放在session里面的实体类必须有个id标识
		redisCacheManager.setPrincipalIdFieldName("userId");
		return redisCacheManager;
	}

	/**
	 * 自定义Realm
	 */
	@Bean
	public UserRealm userRealm(CacheManager cacheManager) {
		final UserRealm userRealm = new UserRealm();
		userRealm.setAuthorizationCacheName(Constants.SYS_AUTH_CACHE);
		userRealm.setCacheManager(cacheManager);
		return userRealm;
	}

	/**
	 * 配置RedisSessionDAO
	 *
	 * @Attention 使用的是shiro-redis开源插件
	 * @Author Sans
	 * @CreateTime 2019/6/12 13:44
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		final RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		redisSessionDAO.setKeyPrefix(SESSION_KEY);
		if (expireTime == -1) {
			redisSessionDAO.setExpire(-1);
		} else {
			redisSessionDAO.setExpire(expireTime * 60);
		}
		return redisSessionDAO;
	}

	/**
	 * 自定义sessionFactory会话
	 */
	@Bean
	public OnlineSessionFactory sessionFactory() {
		final OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
		return sessionFactory;
	}

	/**
	 * 会话管理器
	 */
	@Bean
	public CustomWebSessionManager sessionManager(CacheManager cacheManager) {
		final CustomWebSessionManager manager = new CustomWebSessionManager();
		// 加入缓存管理器
		manager.setCacheManager(cacheManager);
		// 删除过期的session
		manager.setDeleteInvalidSessions(true);
		// 设置全局session超时时间
		if (expireTime < 0) {
			manager.setGlobalSessionTimeout(expireTime);
		} else {
			manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
		}
		// 去掉 JSESSIONID
		manager.setSessionIdUrlRewritingEnabled(false);
		// 定义要使用的无效的Session定时调度器
		if (expireTime > 0) {
			manager.setSessionValidationScheduler(SpringUtils.getBean(SpringSessionValidationScheduler.class));
			// 是否定时检查session
			manager.setSessionValidationSchedulerEnabled(true);
		}
		// 自定义SessionDao
		manager.setSessionDAO(redisSessionDAO());
		// 自定义sessionFactory
		manager.setSessionFactory(sessionFactory());
		return manager;
	}

	/**
	 * 安全管理器
	 */
	@Bean
	public SecurityManager securityManager(UserRealm userRealm, CacheManager cacheManager,
			SessionManager sessionManager) {
		final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(userRealm);
		// 记住我
		securityManager.setRememberMeManager(rememberMeManager());
		// 注入缓存管理器;
		securityManager.setCacheManager(cacheManager);
		// session管理器
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}

	/**
	 * 退出过滤器
	 */
	public LogoutFilter logoutFilter() {
		final LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setLoginUrl(loginUrl);
		return logoutFilter;
	}

	/**
	 * Shiro过滤器配置
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, CacheManager cacheManager,
			SessionManager sessionManager) {
		final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// Shiro的核心安全接口,这个属性是必须的
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 身份认证失败，则跳转到登录页面的配置
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		// 权限认证失败，则跳转到指定页面
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
		// Shiro连接约束配置，即过滤链的定义
		final LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 对静态资源设置匿名访问
		filterChainDefinitionMap.put("/favicon.ico**", "anon");
		filterChainDefinitionMap.put("/sword.png**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/ajax/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/open/**", "anon");
		filterChainDefinitionMap.put("/api/open/**", "anon");
		// Swagger
		filterChainDefinitionMap.put("/v2/**", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");

		filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
		// 退出 logout地址，shiro去清除session
		filterChainDefinitionMap.put("/logout", "logout");
		// 不需要拦截的访问
		filterChainDefinitionMap.put("/login", "anon,captchaValidate");
		// 注册相关
		filterChainDefinitionMap.put("/register", "anon,captchaValidate");
		// 系统权限列表
		// filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

		final Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		// if (expireTime > 0) {
		filters.put("kickout", kickoutSessionFilter(cacheManager, sessionManager));
		// }
		filters.put("captchaValidate", captchaValidateFilter());
		// 注销成功，则跳转到指定页面
		filters.put("logout", logoutFilter());
		if (StringUtils.isEmpty(loginUrl)) {
			filters.put("user", new RestUserFilter());
		} else {
			filters.put("user", new AdminUserFilter());
		}

		shiroFilterFactoryBean.setFilters(filters);

		// 所有请求需要认证
		// if (expireTime > 0) {
		filterChainDefinitionMap.put("/**", "user,kickout");
		// } else {
		// filterChainDefinitionMap.put("/**", "user");
		// }
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 自定义验证码过滤器
	 */
	public CaptchaValidateFilter captchaValidateFilter() {
		final CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
		captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
		captchaValidateFilter.setCaptchaType(captchaType);
		return captchaValidateFilter;
	}

	/**
	 * cookie 属性设置
	 */
	public SimpleCookie rememberMeCookie() {
		final SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		return cookie;
	}

	/**
	 * 记住我
	 */
	public CookieRememberMeManager rememberMeManager() {
		final CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
		return cookieRememberMeManager;
	}

	/**
	 * 同一个用户多设备登录限制
	 */
	public KickoutSessionFilter kickoutSessionFilter(CacheManager cacheManager, SessionManager sessionManager) {
		final KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
		kickoutSessionFilter.setCacheManager(cacheManager);
		kickoutSessionFilter.setSessionManager(sessionManager);
		// 同一个用户最大的会话数，默认-1无限制；比如2的意思是同一个用户允许最多同时两个人登录
		kickoutSessionFilter.setMaxSession(maxSession);
		// 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序
		kickoutSessionFilter.setKickoutAfter(kickoutAfter);
		// 被踢出后重定向到的地址；
		kickoutSessionFilter.setKickoutUrl(loginUrl + "?kickout=1");
		return kickoutSessionFilter;
	}

	/**
	 * 开启Shiro注解通知器
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		final AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	public static void main(String[] args) {
		// 生成Cookie cipherKey
		AesCipherService cipherService = new AesCipherService();
		System.out.println(Base64.encodeToString(cipherService.generateNewKey().getEncoded()));
	}
}
