package com.github.ecsoya.sword.framework.config;

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

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.shiro.realm.UserRealm;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSessionFactory;
import com.github.ecsoya.sword.framework.shiro.web.filter.AdminUserFilter;
import com.github.ecsoya.sword.framework.shiro.web.filter.LogoutFilter;
import com.github.ecsoya.sword.framework.shiro.web.filter.RestUserFilter;
import com.github.ecsoya.sword.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.github.ecsoya.sword.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.github.ecsoya.sword.framework.shiro.web.session.CustomWebSessionManager;
import com.github.ecsoya.sword.framework.shiro.web.session.SpringSessionValidationScheduler;

/**
 * The Class ShiroConfig.
 */
@Configuration
public class ShiroConfig {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

	/** The Constant CACHE_KEY. */
	private static final String CACHE_KEY = "shiro:cache:";

	/** The Constant SESSION_KEY. */
	private static final String SESSION_KEY = "shiro:session:";

	/** The host. */
	// Redis配置
	@Value("${spring.redis.host:localhost}")
	private String host;

	/** The port. */
	@Value("${spring.redis.port:6379}")
	private int port;

	/** The database. */
	@Value("${spring.redis.database:1}")
	private int database;

	/** The timeout. */
	@Value("${spring.redis.timeout:60000}")
	private int timeout;

	/** The password. */
	@Value("${spring.redis.password:}")
	private String password;

	/** The expire time. */
	@Value("${shiro.session.expireTime}")
	private int expireTime;

	/** The validation interval. */
	@Value("${shiro.session.validationInterval}")
	private int validationInterval;

	/** The max session. */
	@Value("${shiro.session.maxSession}")
	private int maxSession;

	/** The kickout after. */
	@Value("${shiro.session.kickoutAfter}")
	private boolean kickoutAfter;

	/** The captcha enabled. */
	@Value("${shiro.user.captchaEnabled}")
	private boolean captchaEnabled;

	/** The captcha type. */
	@Value("${shiro.user.captchaType}")
	private String captchaType;

	/** The domain. */
	@Value("${shiro.cookie.domain}")
	private String domain;

	/** The path. */
	@Value("${shiro.cookie.path}")
	private String path;

	/** The http only. */
	@Value("${shiro.cookie.httpOnly}")
	private boolean httpOnly;

	/** The max age. */
	@Value("${shiro.cookie.maxAge}")
	private int maxAge;

	/** The cipher key. */
	@Value("${shiro.cookie.cipherKey}")
	private String cipherKey;

	/** The login url. */
	@Value("${shiro.user.loginUrl}")
	private String loginUrl;

	/** The unauthorized url. */
	@Value("${shiro.user.unauthorizedUrl}")
	private String unauthorizedUrl;

	/** The context. */
	@Autowired
	private ApplicationContext context;

	/**
	 * Inits the.
	 */
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

	/**
	 * Redis manager.
	 *
	 * @return the redis manager
	 */
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
	 * Cache manager.
	 *
	 * @return the redis cache manager
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
	 * User realm.
	 *
	 * @param cacheManager the cache manager
	 * @return the user realm
	 */
	@Bean
	public UserRealm userRealm(CacheManager cacheManager) {
		final UserRealm userRealm = new UserRealm();
		userRealm.setAuthorizationCacheName(Constants.SYS_AUTH_CACHE);
		userRealm.setCacheManager(cacheManager);
		return userRealm;
	}

	/**
	 * Redis session DAO.
	 *
	 * @return the redis session DAO
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
	 * Session factory.
	 *
	 * @return the online session factory
	 */
	@Bean
	public OnlineSessionFactory sessionFactory() {
		final OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
		return sessionFactory;
	}

	/**
	 * Session manager.
	 *
	 * @param cacheManager the cache manager
	 * @return the custom web session manager
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
	 * Security manager.
	 *
	 * @param userRealm      the user realm
	 * @param cacheManager   the cache manager
	 * @param sessionManager the session manager
	 * @return the security manager
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
	 * Logout filter.
	 *
	 * @return the logout filter
	 */
	public LogoutFilter logoutFilter() {
		final LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setLoginUrl(loginUrl);
		return logoutFilter;
	}

	/**
	 * Shiro filter factory bean.
	 *
	 * @param securityManager the security manager
	 * @param cacheManager    the cache manager
	 * @param sessionManager  the session manager
	 * @return the shiro filter factory bean
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
		filterChainDefinitionMap.put("/v3/**", "anon");
		filterChainDefinitionMap.put("/swagger-ui/**", "anon");
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
	 * Captcha validate filter.
	 *
	 * @return the captcha validate filter
	 */
	public CaptchaValidateFilter captchaValidateFilter() {
		final CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
		captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
		captchaValidateFilter.setCaptchaType(captchaType);
		return captchaValidateFilter;
	}

	/**
	 * Remember me cookie.
	 *
	 * @return the simple cookie
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
	 * Remember me manager.
	 *
	 * @return the cookie remember me manager
	 */
	public CookieRememberMeManager rememberMeManager() {
		final CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
		return cookieRememberMeManager;
	}

	/**
	 * Kickout session filter.
	 *
	 * @param cacheManager   the cache manager
	 * @param sessionManager the session manager
	 * @return the kickout session filter
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
	 * Authorization attribute source advisor.
	 *
	 * @param securityManager the security manager
	 * @return the authorization attribute source advisor
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		final AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// 生成Cookie cipherKey
		AesCipherService cipherService = new AesCipherService();
		System.out.println(Base64.encodeToString(cipherService.generateNewKey().getEncoded()));
	}
}
