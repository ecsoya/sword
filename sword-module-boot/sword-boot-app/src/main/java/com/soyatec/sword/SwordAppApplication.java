package com.soyatec.sword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author Jin Liu (angryred@qq.com)
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SwordAppApplication {
	public static void main(String[] args) {
		// System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(SwordAppApplication.class, args);
		System.out.println("sword App Started...");
	}
}