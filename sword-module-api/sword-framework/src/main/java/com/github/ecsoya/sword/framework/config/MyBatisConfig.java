package com.github.ecsoya.sword.framework.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * The Class MyBatisConfig.
 */
@Configuration
public class MyBatisConfig {

	/** The env. */
	@Autowired
	private Environment env;

	/** The Constant DEFAULT_RESOURCE_PATTERN. */
	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * Sets the type aliases package.
	 *
	 * @param typeAliasesPackage the type aliases package
	 * @return the string
	 */
	public static String setTypeAliasesPackage(String typeAliasesPackage) {
		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		final List<String> allResult = new ArrayList<String>();
		try {
			for (String aliasesPackage : typeAliasesPackage.split(",")) {
				final List<String> result = new ArrayList<String>();
				aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/"
						+ DEFAULT_RESOURCE_PATTERN;
				final Resource[] resources = resolver.getResources(aliasesPackage);
				if (resources != null && resources.length > 0) {
					MetadataReader metadataReader = null;
					for (final Resource resource : resources) {
						if (resource.isReadable()) {
							metadataReader = metadataReaderFactory.getMetadataReader(resource);
							try {
								result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage()
										.getName());
							} catch (final ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (result.size() > 0) {
					final HashSet<String> hashResult = new HashSet<String>(result);
					allResult.addAll(hashResult);
				}
			}
			if (allResult.size() > 0) {
				typeAliasesPackage = String.join(",", allResult.toArray(new String[0]));
			} else {
				throw new RuntimeException(
						"mybatis typeAliasesPackage ??????????????????,??????typeAliasesPackage:" + typeAliasesPackage + "??????????????????");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return typeAliasesPackage;
	}

	/**
	 * Resolve mapper locations.
	 *
	 * @param mapperLocations the mapper locations
	 * @return the resource[]
	 */
	public Resource[] resolveMapperLocations(String[] mapperLocations) {
		final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		final List<Resource> resources = new ArrayList<Resource>();
		if (mapperLocations != null) {
			for (final String mapperLocation : mapperLocations) {
				try {
					final Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (final IOException e) {
					// ignore
				}
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}

	/**
	 * Sql session factory.
	 *
	 * @param dataSource the data source
	 * @return the sql session factory
	 * @throws Exception the exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		String typeAliasesPackage = env.getProperty("mybatis.typeAliasesPackage");
		final String mapperLocations = env.getProperty("mybatis.mapperLocations");
		final String configLocation = env.getProperty("mybatis.configLocation");
		typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
		VFS.addImplClass(SpringBootVFS.class);

		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
		sessionFactory.setMapperLocations(
				resolveMapperLocations(org.apache.commons.lang3.StringUtils.split(mapperLocations, ",")));
		sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
		return sessionFactory.getObject();
	}
}