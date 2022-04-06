package com.github.ecsoya.sword.generator.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.ArrayUtils;

import com.github.ecsoya.sword.common.constant.GenConstants;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class GenTable.
 */
public class GenTable extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The table id. */
	private Long tableId;

	/** The table name. */
	@NotBlank(message = "表名称不能为空")
	private String tableName;

	/** The table comment. */
	@NotBlank(message = "表描述不能为空")
	private String tableComment;

	/** The sub table name. */
	private String subTableName;

	/** The sub table fk name. */
	private String subTableFkName;

	/** The class name. */
	@NotBlank(message = "实体类名称不能为空")
	private String className;

	/** The tpl category. */
	private String tplCategory;

	/** The package name. */
	@NotBlank(message = "生成包路径不能为空")
	private String packageName;

	/** The module name. */
	@NotBlank(message = "生成模块名不能为空")
	private String moduleName;

	/** The business name. */
	@NotBlank(message = "生成业务名不能为空")
	private String businessName;

	/** The function name. */
	@NotBlank(message = "生成功能名不能为空")
	private String functionName;

	/** The function author. */
	@NotBlank(message = "作者不能为空")
	private String functionAuthor;

	/** The gen type. */
	private String genType;

	/** The gen path. */
	private String genPath;

	/** The pk column. */
	private GenTableColumn pkColumn;

	/** The sub table. */
	private GenTable subTable;

	/** The columns. */
	@Valid
	private List<GenTableColumn> columns;

	/** The options. */
	private String options;

	/** The tree code. */
	private String treeCode;

	/** The tree parent code. */
	private String treeParentCode;

	/** The tree name. */
	private String treeName;

	/** The parent menu id. */
	private String parentMenuId;

	/** The parent menu name. */
	private String parentMenuName;

	/**
	 * Gets the 编号.
	 *
	 * @return the 编号
	 */
	public Long getTableId() {
		return tableId;
	}

	/**
	 * Sets the 编号.
	 *
	 * @param tableId the new 编号
	 */
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	/**
	 * Gets the 表名称.
	 *
	 * @return the 表名称
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the 表名称.
	 *
	 * @param tableName the new 表名称
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Gets the 表描述.
	 *
	 * @return the 表描述
	 */
	public String getTableComment() {
		return tableComment;
	}

	/**
	 * Sets the 表描述.
	 *
	 * @param tableComment the new 表描述
	 */
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	/**
	 * Gets the 关联父表的表名.
	 *
	 * @return the 关联父表的表名
	 */
	public String getSubTableName() {
		return subTableName;
	}

	/**
	 * Sets the 关联父表的表名.
	 *
	 * @param subTableName the new 关联父表的表名
	 */
	public void setSubTableName(String subTableName) {
		this.subTableName = subTableName;
	}

	/**
	 * Gets the 本表关联父表的外键名.
	 *
	 * @return the 本表关联父表的外键名
	 */
	public String getSubTableFkName() {
		return subTableFkName;
	}

	/**
	 * Sets the 本表关联父表的外键名.
	 *
	 * @param subTableFkName the new 本表关联父表的外键名
	 */
	public void setSubTableFkName(String subTableFkName) {
		this.subTableFkName = subTableFkName;
	}

	/**
	 * Gets the 实体类名称(首字母大写).
	 *
	 * @return the 实体类名称(首字母大写)
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the 实体类名称(首字母大写).
	 *
	 * @param className the new 实体类名称(首字母大写)
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Gets the 使用的模板（crud单表操作 tree树表操作 sub主子表操作）.
	 *
	 * @return the 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
	 */
	public String getTplCategory() {
		return tplCategory;
	}

	/**
	 * Sets the 使用的模板（crud单表操作 tree树表操作 sub主子表操作）.
	 *
	 * @param tplCategory the new 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
	 */
	public void setTplCategory(String tplCategory) {
		this.tplCategory = tplCategory;
	}

	/**
	 * Gets the 生成包路径.
	 *
	 * @return the 生成包路径
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Sets the 生成包路径.
	 *
	 * @param packageName the new 生成包路径
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Gets the 生成模块名.
	 *
	 * @return the 生成模块名
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Sets the 生成模块名.
	 *
	 * @param moduleName the new 生成模块名
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * Gets the 生成业务名.
	 *
	 * @return the 生成业务名
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * Sets the 生成业务名.
	 *
	 * @param businessName the new 生成业务名
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * Gets the 生成功能名.
	 *
	 * @return the 生成功能名
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Sets the 生成功能名.
	 *
	 * @param functionName the new 生成功能名
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * Gets the 生成作者.
	 *
	 * @return the 生成作者
	 */
	public String getFunctionAuthor() {
		return functionAuthor;
	}

	/**
	 * Sets the 生成作者.
	 *
	 * @param functionAuthor the new 生成作者
	 */
	public void setFunctionAuthor(String functionAuthor) {
		this.functionAuthor = functionAuthor;
	}

	/**
	 * Gets the 生成代码方式（0zip压缩包 1自定义路径）.
	 *
	 * @return the 生成代码方式（0zip压缩包 1自定义路径）
	 */
	public String getGenType() {
		return genType;
	}

	/**
	 * Sets the 生成代码方式（0zip压缩包 1自定义路径）.
	 *
	 * @param genType the new 生成代码方式（0zip压缩包 1自定义路径）
	 */
	public void setGenType(String genType) {
		this.genType = genType;
	}

	/**
	 * Gets the 生成路径（不填默认项目路径）.
	 *
	 * @return the 生成路径（不填默认项目路径）
	 */
	public String getGenPath() {
		return genPath;
	}

	/**
	 * Sets the 生成路径（不填默认项目路径）.
	 *
	 * @param genPath the new 生成路径（不填默认项目路径）
	 */
	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	/**
	 * Gets the 主键信息.
	 *
	 * @return the 主键信息
	 */
	public GenTableColumn getPkColumn() {
		return pkColumn;
	}

	/**
	 * Sets the 主键信息.
	 *
	 * @param pkColumn the new 主键信息
	 */
	public void setPkColumn(GenTableColumn pkColumn) {
		this.pkColumn = pkColumn;
	}

	/**
	 * Gets the 子表信息.
	 *
	 * @return the 子表信息
	 */
	public GenTable getSubTable() {
		return subTable;
	}

	/**
	 * Sets the 子表信息.
	 *
	 * @param subTable the new 子表信息
	 */
	public void setSubTable(GenTable subTable) {
		this.subTable = subTable;
	}

	/**
	 * Gets the 表列信息.
	 *
	 * @return the 表列信息
	 */
	public List<GenTableColumn> getColumns() {
		return columns;
	}

	/**
	 * Sets the 表列信息.
	 *
	 * @param columns the new 表列信息
	 */
	public void setColumns(List<GenTableColumn> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the 其它生成选项.
	 *
	 * @return the 其它生成选项
	 */
	public String getOptions() {
		return options;
	}

	/**
	 * Sets the 其它生成选项.
	 *
	 * @param options the new 其它生成选项
	 */
	public void setOptions(String options) {
		this.options = options;
	}

	/**
	 * Gets the 树编码字段.
	 *
	 * @return the 树编码字段
	 */
	public String getTreeCode() {
		return treeCode;
	}

	/**
	 * Sets the 树编码字段.
	 *
	 * @param treeCode the new 树编码字段
	 */
	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	/**
	 * Gets the 树父编码字段.
	 *
	 * @return the 树父编码字段
	 */
	public String getTreeParentCode() {
		return treeParentCode;
	}

	/**
	 * Sets the 树父编码字段.
	 *
	 * @param treeParentCode the new 树父编码字段
	 */
	public void setTreeParentCode(String treeParentCode) {
		this.treeParentCode = treeParentCode;
	}

	/**
	 * Gets the 树名称字段.
	 *
	 * @return the 树名称字段
	 */
	public String getTreeName() {
		return treeName;
	}

	/**
	 * Sets the 树名称字段.
	 *
	 * @param treeName the new 树名称字段
	 */
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	/**
	 * Gets the 上级菜单ID字段.
	 *
	 * @return the 上级菜单ID字段
	 */
	public String getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * Sets the 上级菜单ID字段.
	 *
	 * @param parentMenuId the new 上级菜单ID字段
	 */
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	/**
	 * Gets the 上级菜单名称字段.
	 *
	 * @return the 上级菜单名称字段
	 */
	public String getParentMenuName() {
		return parentMenuName;
	}

	/**
	 * Sets the 上级菜单名称字段.
	 *
	 * @param parentMenuName the new 上级菜单名称字段
	 */
	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	/**
	 * Checks if is sub.
	 *
	 * @return true, if is sub
	 */
	public boolean isSub() {
		return isSub(this.tplCategory);
	}

	/**
	 * Checks if is sub.
	 *
	 * @param tplCategory the tpl category
	 * @return true, if is sub
	 */
	public static boolean isSub(String tplCategory) {
		return tplCategory != null && org.apache.commons.lang3.StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
	}

	/**
	 * Checks if is tree.
	 *
	 * @return true, if is tree
	 */
	public boolean isTree() {
		return isTree(this.tplCategory);
	}

	/**
	 * Checks if is tree.
	 *
	 * @param tplCategory the tpl category
	 * @return true, if is tree
	 */
	public static boolean isTree(String tplCategory) {
		return tplCategory != null && org.apache.commons.lang3.StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
	}

	/**
	 * Checks if is crud.
	 *
	 * @return true, if is crud
	 */
	public boolean isCrud() {
		return isCrud(this.tplCategory);
	}

	/**
	 * Checks if is crud.
	 *
	 * @param tplCategory the tpl category
	 * @return true, if is crud
	 */
	public static boolean isCrud(String tplCategory) {
		return tplCategory != null && org.apache.commons.lang3.StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
	}

	/**
	 * Checks if is super column.
	 *
	 * @param javaField the java field
	 * @return true, if is super column
	 */
	public boolean isSuperColumn(String javaField) {
		return isSuperColumn(this.tplCategory, javaField);
	}

	/**
	 * Checks if is super column.
	 *
	 * @param tplCategory the tpl category
	 * @param javaField   the java field
	 * @return true, if is super column
	 */
	public static boolean isSuperColumn(String tplCategory, String javaField) {
		if (isTree(tplCategory)) {
			return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(javaField,
					ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
		}
		return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
	}
}