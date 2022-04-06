package com.github.ecsoya.sword.generator.domain;

import javax.validation.constraints.NotBlank;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class GenTableColumn.
 */
public class GenTableColumn extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The column id. */
	private Long columnId;

	/** The table id. */
	private Long tableId;

	/** The column name. */
	private String columnName;

	/** The column comment. */
	private String columnComment;

	/** The column type. */
	private String columnType;

	/** The java type. */
	private String javaType;

	/** The java field. */
	@NotBlank(message = "Java属性不能为空")
	private String javaField;

	/** The is pk. */
	private String isPk;

	/** The is increment. */
	private String isIncrement;

	/** The is required. */
	private String isRequired;

	/** The is insert. */
	private String isInsert;

	/** The is edit. */
	private String isEdit;

	/** The is list. */
	private String isList;

	/** The is query. */
	private String isQuery;

	/** The query type. */
	private String queryType;

	/** The html type. */
	private String htmlType;

	/** The dict type. */
	private String dictType;

	/** The sort. */
	private Integer sort;

	/**
	 * Sets the 编号.
	 *
	 * @param columnId the new 编号
	 */
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	/**
	 * Gets the 编号.
	 *
	 * @return the 编号
	 */
	public Long getColumnId() {
		return columnId;
	}

	/**
	 * Sets the 归属表编号.
	 *
	 * @param tableId the new 归属表编号
	 */
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	/**
	 * Gets the 归属表编号.
	 *
	 * @return the 归属表编号
	 */
	public Long getTableId() {
		return tableId;
	}

	/**
	 * Sets the 列名称.
	 *
	 * @param columnName the new 列名称
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Gets the 列名称.
	 *
	 * @return the 列名称
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Sets the 列描述.
	 *
	 * @param columnComment the new 列描述
	 */
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	/**
	 * Gets the 列描述.
	 *
	 * @return the 列描述
	 */
	public String getColumnComment() {
		return columnComment;
	}

	/**
	 * Sets the 列类型.
	 *
	 * @param columnType the new 列类型
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	/**
	 * Gets the 列类型.
	 *
	 * @return the 列类型
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * Sets the jAVA类型.
	 *
	 * @param javaType the new jAVA类型
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	/**
	 * Gets the jAVA类型.
	 *
	 * @return the jAVA类型
	 */
	public String getJavaType() {
		return javaType;
	}

	/**
	 * Sets the jAVA字段名.
	 *
	 * @param javaField the new jAVA字段名
	 */
	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	/**
	 * Gets the jAVA字段名.
	 *
	 * @return the jAVA字段名
	 */
	public String getJavaField() {
		return javaField;
	}

	/**
	 * Gets the cap java field.
	 *
	 * @return the cap java field
	 */
	public String getCapJavaField() {
		return org.apache.commons.lang3.StringUtils.capitalize(javaField);
	}

	/**
	 * Sets the 是否主键（1是）.
	 *
	 * @param isPk the new 是否主键（1是）
	 */
	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	/**
	 * Gets the 是否主键（1是）.
	 *
	 * @return the 是否主键（1是）
	 */
	public String getIsPk() {
		return isPk;
	}

	/**
	 * Checks if is pk.
	 *
	 * @return true, if is pk
	 */
	public boolean isPk() {
		return isPk(this.isPk);
	}

	/**
	 * Checks if is pk.
	 *
	 * @param isPk the is pk
	 * @return true, if is pk
	 */
	public boolean isPk(String isPk) {
		return isPk != null && org.apache.commons.lang3.StringUtils.equals("1", isPk);
	}

	/**
	 * Gets the 是否自增（1是）.
	 *
	 * @return the 是否自增（1是）
	 */
	public String getIsIncrement() {
		return isIncrement;
	}

	/**
	 * Sets the 是否自增（1是）.
	 *
	 * @param isIncrement the new 是否自增（1是）
	 */
	public void setIsIncrement(String isIncrement) {
		this.isIncrement = isIncrement;
	}

	/**
	 * Checks if is increment.
	 *
	 * @return true, if is increment
	 */
	public boolean isIncrement() {
		return isIncrement(this.isIncrement);
	}

	/**
	 * Checks if is increment.
	 *
	 * @param isIncrement the is increment
	 * @return true, if is increment
	 */
	public boolean isIncrement(String isIncrement) {
		return isIncrement != null && org.apache.commons.lang3.StringUtils.equals("1", isIncrement);
	}

	/**
	 * Sets the 是否必填（1是）.
	 *
	 * @param isRequired the new 是否必填（1是）
	 */
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Gets the 是否必填（1是）.
	 *
	 * @return the 是否必填（1是）
	 */
	public String getIsRequired() {
		return isRequired;
	}

	/**
	 * Checks if is required.
	 *
	 * @return true, if is required
	 */
	public boolean isRequired() {
		return isRequired(this.isRequired);
	}

	/**
	 * Checks if is required.
	 *
	 * @param isRequired the is required
	 * @return true, if is required
	 */
	public boolean isRequired(String isRequired) {
		return isRequired != null && org.apache.commons.lang3.StringUtils.equals("1", isRequired);
	}

	/**
	 * Sets the 是否为插入字段（1是）.
	 *
	 * @param isInsert the new 是否为插入字段（1是）
	 */
	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	/**
	 * Gets the 是否为插入字段（1是）.
	 *
	 * @return the 是否为插入字段（1是）
	 */
	public String getIsInsert() {
		return isInsert;
	}

	/**
	 * Checks if is insert.
	 *
	 * @return true, if is insert
	 */
	public boolean isInsert() {
		return isInsert(this.isInsert);
	}

	/**
	 * Checks if is insert.
	 *
	 * @param isInsert the is insert
	 * @return true, if is insert
	 */
	public boolean isInsert(String isInsert) {
		return isInsert != null && org.apache.commons.lang3.StringUtils.equals("1", isInsert);
	}

	/**
	 * Sets the 是否编辑字段（1是）.
	 *
	 * @param isEdit the new 是否编辑字段（1是）
	 */
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * Gets the 是否编辑字段（1是）.
	 *
	 * @return the 是否编辑字段（1是）
	 */
	public String getIsEdit() {
		return isEdit;
	}

	/**
	 * Checks if is edits the.
	 *
	 * @return true, if is edits the
	 */
	public boolean isEdit() {
		return isInsert(this.isEdit);
	}

	/**
	 * Checks if is edits the.
	 *
	 * @param isEdit the is edit
	 * @return true, if is edits the
	 */
	public boolean isEdit(String isEdit) {
		return isEdit != null && org.apache.commons.lang3.StringUtils.equals("1", isEdit);
	}

	/**
	 * Sets the 是否列表字段（1是）.
	 *
	 * @param isList the new 是否列表字段（1是）
	 */
	public void setIsList(String isList) {
		this.isList = isList;
	}

	/**
	 * Gets the 是否列表字段（1是）.
	 *
	 * @return the 是否列表字段（1是）
	 */
	public String getIsList() {
		return isList;
	}

	/**
	 * Checks if is list.
	 *
	 * @return true, if is list
	 */
	public boolean isList() {
		return isList(this.isList);
	}

	/**
	 * Checks if is list.
	 *
	 * @param isList the is list
	 * @return true, if is list
	 */
	public boolean isList(String isList) {
		return isList != null && org.apache.commons.lang3.StringUtils.equals("1", isList);
	}

	/**
	 * Sets the 是否查询字段（1是）.
	 *
	 * @param isQuery the new 是否查询字段（1是）
	 */
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	/**
	 * Gets the 是否查询字段（1是）.
	 *
	 * @return the 是否查询字段（1是）
	 */
	public String getIsQuery() {
		return isQuery;
	}

	/**
	 * Checks if is query.
	 *
	 * @return true, if is query
	 */
	public boolean isQuery() {
		return isQuery(this.isQuery);
	}

	/**
	 * Checks if is query.
	 *
	 * @param isQuery the is query
	 * @return true, if is query
	 */
	public boolean isQuery(String isQuery) {
		return isQuery != null && org.apache.commons.lang3.StringUtils.equals("1", isQuery);
	}

	/**
	 * Sets the 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）.
	 *
	 * @param queryType the new 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * Gets the 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）.
	 *
	 * @return the 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * Gets the
	 * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、upload上传控件、summernote富文本控件）.
	 *
	 * @return the
	 *         显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、upload上传控件、summernote富文本控件）
	 */
	public String getHtmlType() {
		return htmlType;
	}

	/**
	 * Sets the
	 * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、upload上传控件、summernote富文本控件）.
	 *
	 * @param htmlType the new
	 *                 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、upload上传控件、summernote富文本控件）
	 */
	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	/**
	 * Sets the 字典类型.
	 *
	 * @param dictType the new 字典类型
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	/**
	 * Gets the 字典类型.
	 *
	 * @return the 字典类型
	 */
	public String getDictType() {
		return dictType;
	}

	/**
	 * Sets the 排序.
	 *
	 * @param sort the new 排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * Gets the 排序.
	 *
	 * @return the 排序
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * Checks if is super column.
	 *
	 * @return true, if is super column
	 */
	public boolean isSuperColumn() {
		return isSuperColumn(this.javaField);
	}

	/**
	 * Checks if is super column.
	 *
	 * @param javaField the java field
	 * @return true, if is super column
	 */
	public static boolean isSuperColumn(String javaField) {
		return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(javaField,
				// BaseEntity
				"createBy", "createTime", "updateBy", "updateTime", "remark",
				// TreeEntity
				"parentName", "parentId", "orderNum", "ancestors");
	}

	/**
	 * Checks if is usable column.
	 *
	 * @return true, if is usable column
	 */
	public boolean isUsableColumn() {
		return isUsableColumn(javaField);
	}

	/**
	 * Checks if is usable column.
	 *
	 * @param javaField the java field
	 * @return true, if is usable column
	 */
	public static boolean isUsableColumn(String javaField) {
		// isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
		return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
	}

	/**
	 * Read converter exp.
	 *
	 * @return the string
	 */
	public String readConverterExp() {
		final String remarks = org.apache.commons.lang3.StringUtils.substringBetween(this.columnComment, "（", "）");
		final StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(remarks)) {
			for (final String value : remarks.split(" ")) {
				if (StringUtils.isNotEmpty(value)) {
					final Object startStr = value.subSequence(0, 1);
					final String endStr = value.substring(1);
					sb.append("").append(startStr).append("=").append(endStr).append(",");
				}
			}
			return sb.deleteCharAt(sb.length() - 1).toString();
		} else {
			return this.columnComment;
		}
	}
}