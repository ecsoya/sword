package com.github.ecsoya.sword.generator.util;

import java.util.Arrays;

import org.apache.commons.lang3.RegExUtils;

import com.github.ecsoya.sword.common.constant.GenConstants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.generator.config.GenConfig;
import com.github.ecsoya.sword.generator.domain.GenTable;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;

/**
 * 代码生成器 工具类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class GenUtils {
	/**
	 * 初始化表信息
	 */
	public static void initTable(GenTable genTable, String operName) {
		genTable.setClassName(convertClassName(genTable.getTableName()));
		genTable.setPackageName(GenConfig.getPackageName());
		genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
		genTable.setBusinessName(getBusinessName(genTable.getTableName()));
		genTable.setFunctionName(replaceText(genTable.getTableComment()));
		genTable.setFunctionAuthor(GenConfig.getAuthor());
		genTable.setCreateBy(operName);
	}

	/**
	 * 初始化列属性字段
	 */
	public static void initColumnField(GenTableColumn column, GenTable table) {
		final String dataType = getDbType(column.getColumnType());
		final String columnName = column.getColumnName();
		column.setTableId(table.getTableId());
		column.setCreateBy(table.getCreateBy());
		// 设置java字段名
		column.setJavaField(StringUtils.toCamelCase(columnName));
		// 设置默认类型
		column.setJavaType(GenConstants.TYPE_STRING);

		if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType)
				|| arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
			// 字符串长度超过500设置为文本域
			final Integer columnLength = getColumnLength(column.getColumnType());
			final String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)
					? GenConstants.HTML_TEXTAREA
					: GenConstants.HTML_INPUT;
			column.setHtmlType(htmlType);
		} else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
			column.setJavaType(GenConstants.TYPE_DATE);
			column.setHtmlType(GenConstants.HTML_DATETIME);
		} else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
			column.setHtmlType(GenConstants.HTML_INPUT);

			// 如果是浮点型
			final String[] str = org.apache.commons.lang3.StringUtils.split(
					org.apache.commons.lang3.StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
			if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
				column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
			}
			// 如果是整形
			else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
				column.setJavaType(GenConstants.TYPE_INTEGER);
			}
			// 长整形
			else {
				column.setJavaType(GenConstants.TYPE_LONG);
			}
		}

		// 插入字段（默认所有字段都需要插入）
		column.setIsInsert(GenConstants.REQUIRE);

		// 编辑字段
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
			column.setIsEdit(GenConstants.REQUIRE);
		}
		// 列表字段
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
			column.setIsList(GenConstants.REQUIRE);
		}
		// 查询字段
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
			column.setIsQuery(GenConstants.REQUIRE);
		}

		// 查询字段类型
		if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "name")) {
			column.setQueryType(GenConstants.QUERY_LIKE);
		}
		// 状态字段设置单选框
		if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "status")) {
			column.setHtmlType(GenConstants.HTML_RADIO);
		}
		// 类型&性别字段设置下拉框
		else if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "type")
				|| org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "sex")) {
			column.setHtmlType(GenConstants.HTML_SELECT);
		}
		// 文件字段设置上传控件
		else if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "file")) {
			column.setHtmlType(GenConstants.HTML_UPLOAD);
		}
		// 内容字段设置富文本控件
		else if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(columnName, "content")) {
			column.setHtmlType(GenConstants.HTML_SUMMERNOTE);
		}
	}

	/**
	 * 校验数组是否包含指定值
	 *
	 * @param arr         数组
	 * @param targetValue 值
	 * @return 是否包含
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 获取模块名
	 *
	 * @param packageName 包名
	 * @return 模块名
	 */
	public static String getModuleName(String packageName) {
		final int lastIndex = packageName.lastIndexOf(".");
		final int nameLength = packageName.length();
		final String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
		return moduleName;
	}

	/**
	 * 获取业务名
	 *
	 * @param tableName 表名
	 * @return 业务名
	 */
	public static String getBusinessName(String tableName) {
		final int lastIndex = tableName.lastIndexOf("_");
		final int nameLength = tableName.length();
		final String businessName = StringUtils.substring(tableName, lastIndex + 1, nameLength);
		return businessName;
	}

	/**
	 * 表名转换成Java类名
	 *
	 * @param tableName 表名称
	 * @return 类名
	 */
	public static String convertClassName(String tableName) {
		final boolean autoRemovePre = GenConfig.getAutoRemovePre();
		final String tablePrefix = GenConfig.getTablePrefix();
		if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
			final String[] searchList = org.apache.commons.lang3.StringUtils.split(tablePrefix, ",");
			tableName = replaceFirst(tableName, searchList);
		}
		return StringUtils.convertToCamelCase(tableName);
	}

	/**
	 * 批量替换前缀
	 *
	 * @param replacementm 替换值
	 * @param searchList   替换列表
	 * @return
	 */
	public static String replaceFirst(String replacementm, String[] searchList) {
		String text = replacementm;
		for (final String searchString : searchList) {
			if (replacementm.startsWith(searchString)) {
				text = replacementm.replaceFirst(searchString, "");
				break;
			}
		}
		return text;
	}

	/**
	 * 关键字替换
	 *
	 * @param text 需要被替换的名字
	 * @return 替换后的名字
	 */
	public static String replaceText(String text) {
		if (StringUtils.isEmpty(text)) {
			return text;
		}
		int index = text.indexOf("\n");
		if (index != -1) {
			return text.substring(0, index);
		}
		return RegExUtils.replaceAll(text, "(?:表|蜜蜂Plus)", "");
	}

	/**
	 * 获取数据库类型字段
	 *
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static String getDbType(String columnType) {
		if (org.apache.commons.lang3.StringUtils.indexOf(columnType, "(") > 0) {
			return org.apache.commons.lang3.StringUtils.substringBefore(columnType, "(");
		} else {
			return columnType;
		}
	}

	/**
	 * 获取字段长度
	 *
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static Integer getColumnLength(String columnType) {
		if (org.apache.commons.lang3.StringUtils.indexOf(columnType, "(") > 0) {
			final String length = org.apache.commons.lang3.StringUtils.substringBetween(columnType, "(", ")");
			return Integer.valueOf(length);
		} else {
			return 0;
		}
	}

	/**
	 * 获取空数组列表
	 *
	 * @param length 长度
	 * @return 数组信息
	 */
	public static String[] emptyList(int length) {
		final String[] values = new String[length];
		for (int i = 0; i < length; i++) {
			values[i] = org.apache.commons.lang3.StringUtils.EMPTY;
		}
		return values;
	}
}