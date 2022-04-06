package com.github.ecsoya.sword.common.constant;

/**
 * The Class GenConstants.
 */
public class GenConstants {

	/** The Constant TPL_CRUD. */
	public static final String TPL_CRUD = "crud";

	/** The Constant TPL_TREE. */
	public static final String TPL_TREE = "tree";

	/** The Constant TPL_SUB. */
	public static final String TPL_SUB = "sub";

	/** The Constant TREE_CODE. */
	public static final String TREE_CODE = "treeCode";

	/** The Constant TREE_PARENT_CODE. */
	public static final String TREE_PARENT_CODE = "treeParentCode";

	/** The Constant TREE_NAME. */
	public static final String TREE_NAME = "treeName";

	/** The Constant PARENT_MENU_ID. */
	public static final String PARENT_MENU_ID = "parentMenuId";

	/** The Constant PARENT_MENU_NAME. */
	public static final String PARENT_MENU_NAME = "parentMenuName";

	/** The Constant COLUMNTYPE_STR. */
	public static final String[] COLUMNTYPE_STR = { "char", "varchar", "nvarchar", "varchar2" };

	/** The Constant COLUMNTYPE_TEXT. */
	public static final String[] COLUMNTYPE_TEXT = { "tinytext", "text", "mediumtext", "longtext" };

	/** The Constant COLUMNTYPE_TIME. */
	public static final String[] COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };

	/** The Constant COLUMNTYPE_NUMBER. */
	public static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
			"bit", "bigint", "float", "double", "decimal" };

	/** The Constant COLUMNNAME_NOT_EDIT. */
	public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };

	/** The Constant COLUMNNAME_NOT_LIST. */
	public static final String[] COLUMNNAME_NOT_LIST = { "id", "create_by", "create_time", "del_flag", "update_by",
			"update_time" };

	/** The Constant COLUMNNAME_NOT_QUERY. */
	public static final String[] COLUMNNAME_NOT_QUERY = { "id", "create_by", "create_time", "del_flag", "update_by",
			"update_time", "remark" };

	/** The Constant BASE_ENTITY. */
	public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };

	/** The Constant TREE_ENTITY. */
	public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors" };

	/** The Constant HTML_INPUT. */
	public static final String HTML_INPUT = "input";

	/** The Constant HTML_TEXTAREA. */
	public static final String HTML_TEXTAREA = "textarea";

	/** The Constant HTML_SELECT. */
	public static final String HTML_SELECT = "select";

	/** The Constant HTML_RADIO. */
	public static final String HTML_RADIO = "radio";

	/** The Constant HTML_CHECKBOX. */
	public static final String HTML_CHECKBOX = "checkbox";

	/** The Constant HTML_DATETIME. */
	public static final String HTML_DATETIME = "datetime";

	/** The Constant HTML_UPLOAD. */
	public static final String HTML_UPLOAD = "upload";

	/** The Constant HTML_SUMMERNOTE. */
	public static final String HTML_SUMMERNOTE = "summernote";

	/** The Constant TYPE_STRING. */
	public static final String TYPE_STRING = "String";

	/** The Constant TYPE_INTEGER. */
	public static final String TYPE_INTEGER = "Integer";

	/** The Constant TYPE_LONG. */
	public static final String TYPE_LONG = "Long";

	/** The Constant TYPE_DOUBLE. */
	public static final String TYPE_DOUBLE = "Double";

	/** The Constant TYPE_BIGDECIMAL. */
	public static final String TYPE_BIGDECIMAL = "BigDecimal";

	/** The Constant TYPE_DATE. */
	public static final String TYPE_DATE = "Date";

	/** The Constant QUERY_LIKE. */
	public static final String QUERY_LIKE = "LIKE";

	/** The Constant REQUIRE. */
	public static final String REQUIRE = "1";
}
