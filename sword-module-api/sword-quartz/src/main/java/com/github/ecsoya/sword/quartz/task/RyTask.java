package com.github.ecsoya.sword.quartz.task;

import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class RyTask.
 */
@Component("ryTask")
public class RyTask {

	/**
	 * Ry multiple params.
	 *
	 * @param s the s
	 * @param b the b
	 * @param l the l
	 * @param d the d
	 * @param i the i
	 */
	public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
	}

	/**
	 * Ry params.
	 *
	 * @param params the params
	 */
	public void ryParams(String params) {
		System.out.println("执行有参方法：" + params);
	}

	/**
	 * Ry no params.
	 */
	public void ryNoParams() {
		System.out.println("执行无参方法");
	}
}
