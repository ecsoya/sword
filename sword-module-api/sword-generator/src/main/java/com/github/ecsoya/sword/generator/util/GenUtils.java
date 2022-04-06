package com.github.ecsoya.sword.generator.util;

import java.util.Arrays;

import org.apache.commons.lang3.RegExUtils;

import com.github.ecsoya.sword.common.constant.GenConstants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.generator.config.GenConfig;
import com.github.ecsoya.sword.generator.domain.GenTable;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;

/**
 * The Class GenUtils.
 */
public class GenUtils {

	/**
	 * Inits the table.
	 *
	 * @param genTable the gen table
	 * @param operName the oper name
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
	 * Inits the column field.
	 *
	 * @param column the column
	 * @param table  the table
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
	 * Arrays contains.
	 *
	 * @param arr         the arr
	 * @param targetValue the target value
	 * @return true, if successful
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * Gets the module name.
	 *
	 * @param packageName the package name
	 * @return the module name
	 */
	public static String getModuleName(String packageName) {
		final int lastIndex = packageName.lastIndexOf(".");
		final int nameLength = packageName.length();
		final String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
		return moduleName;
	}

	/**
	 * Gets the business name.
	 *
	 * @param tableName the table name
	 * @return the business name
	 */
	public static String getBusinessName(String tableName) {
		final int lastIndex = tableName.lastIndexOf("_");
		final int nameLength = tableName.length();
		final String businessName = StringUtils.substring(tableName, lastIndex + 1, nameLength);
		return businessName;
	}

	/**
	 * Convert class name.
	 *
	 * @param tableName the table name
	 * @return the string
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
	 * Replace first.
	 *
	 * @param replacementm the replacementm
	 * @param searchList   the search list
	 * @return the string
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
	 * Replace text.
	 *
	 * @param text the text
	 * @return the string
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
	 * Gets the db type.
	 *
	 * @param columnType the column type
	 * @return the db type
	 */
	public static String getDbType(String columnType) {
		if (org.apache.commons.lang3.StringUtils.indexOf(columnType, "(") > 0) {
			return org.apache.commons.lang3.StringUtils.substringBefore(columnType, "(");
		} else {
			return columnType;
		}
	}

	/**
	 * Gets the column length.
	 *
	 * @param columnType the column type
	 * @return the column length
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
	 * Empty list.
	 *
	 * @param length the length
	 * @return the string[]
	 */
	public static String[] emptyList(int length) {
		final String[] values = new String[length];
		for (int i = 0; i < length; i++) {
			values[i] = org.apache.commons.lang3.StringUtils.EMPTY;
		}
		return values;
	}
}