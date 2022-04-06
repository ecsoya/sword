package com.github.ecsoya.sword.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.framework.interceptor.RepeatSubmitInterceptor;

/**
 * The Class ResourcesConfig.
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

	/** The index url. */
	@Value("${shiro.user.indexUrl}")
	private String indexUrl;

	/** The repeat submit interceptor. */
	@Autowired
	private RepeatSubmitInterceptor repeatSubmitInterceptor;

	/**
	 * Adds the view controllers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:" + indexUrl);
	}

	/**
	 * Adds the resource handlers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
		registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
				.addResourceLocations("file:" + GlobalConfig.getProfile() + "/");

		/** swagger配置 */
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Adds the interceptors.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
	}
}