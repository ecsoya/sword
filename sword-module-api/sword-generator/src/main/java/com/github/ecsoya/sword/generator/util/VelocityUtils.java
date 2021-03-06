package com.github.ecsoya.sword.generator.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ecsoya.sword.common.constant.GenConstants;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.generator.config.GenConfig;
import com.github.ecsoya.sword.generator.domain.GenTable;
import com.github.ecsoya.sword.generator.domain.GenTableColumn;

/**
 * The Class VelocityUtils.
 */
public class VelocityUtils {

	/** The Constant PROJECT_PATH. */
	private static final String PROJECT_PATH = "main/java";

	/** The Constant MYBATIS_PATH. */
	private static final String MYBATIS_PATH = "main/resources/mapper";

	/** The Constant TEMPLATES_PATH. */
	private static final String TEMPLATES_PATH = "main/resources/templates";

	/** The Constant DEFAULT_PARENT_MENU_ID. */
	private static final String DEFAULT_PARENT_MENU_ID = "3";

	/**
	 * Prepare context.
	 *
	 * @param genTable the gen table
	 * @return the velocity context
	 */
	public static VelocityContext prepareContext(GenTable genTable) {
		final String moduleName = genTable.getModuleName();
		final String businessName = genTable.getBusinessName();
		final String packageName = genTable.getPackageName();
		final String tplCategory = genTable.getTplCategory();
		final String functionName = genTable.getFunctionName();

		final VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("tplCategory", genTable.getTplCategory());
		velocityContext.put("tableName", genTable.getTableName());
		velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
		velocityContext.put("ClassName", genTable.getClassName());
		velocityContext.put("className", org.apache.commons.lang3.StringUtils.uncapitalize(genTable.getClassName()));
		velocityContext.put("moduleName", genTable.getModuleName());
		velocityContext.put("businessName", genTable.getBusinessName());
		velocityContext.put("basePackage", getPackagePrefix(packageName));
		velocityContext.put("domainPackagePrefix", GenConfig.getDomainPackagePrefix());
		velocityContext.put("packageName", packageName);
		velocityContext.put("author", genTable.getFunctionAuthor());
		velocityContext.put("datetime", DateUtils.getDate());
		velocityContext.put("pkColumn", genTable.getPkColumn());
		velocityContext.put("importList", getImportList(genTable));
		velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
		velocityContext.put("columns", genTable.getColumns());
		velocityContext.put("table", genTable);
		setMenuVelocityContext(velocityContext, genTable);
		if (GenConstants.TPL_TREE.equals(tplCategory)) {
			setTreeVelocityContext(velocityContext, genTable);
		}
		if (GenConstants.TPL_SUB.equals(tplCategory)) {
			setSubVelocityContext(velocityContext, genTable);
		}
		return velocityContext;
	}

	/**
	 * Sets the menu velocity context.
	 *
	 * @param context  the context
	 * @param genTable the gen table
	 */
	public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
		final String options = genTable.getOptions();
		final JSONObject paramsObj = JSON.parseObject(options);
		final String parentMenuId = getParentMenuId(paramsObj);
		context.put("parentMenuId", parentMenuId);
	}

	/**
	 * Sets the tree velocity context.
	 *
	 * @param context  the context
	 * @param genTable the gen table
	 */
	public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
		final String options = genTable.getOptions();
		final JSONObject paramsObj = JSON.parseObject(options);
		final String treeCode = getTreecode(paramsObj);
		final String treeParentCode = getTreeParentCode(paramsObj);
		final String treeName = getTreeName(paramsObj);

		context.put("treeCode", treeCode);
		context.put("treeParentCode", treeParentCode);
		context.put("treeName", treeName);
		context.put("expandColumn", getExpandColumn(genTable));
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
		}
	}

	/**
	 * Sets the sub velocity context.
	 *
	 * @param context  the context
	 * @param genTable the gen table
	 */
	public static void setSubVelocityContext(VelocityContext context, GenTable genTable) {
		final GenTable subTable = genTable.getSubTable();
		final String subTableName = genTable.getSubTableName();
		final String subTableFkName = genTable.getSubTableFkName();
		final String subClassName = genTable.getSubTable().getClassName();
		final String subTableFkClassName = StringUtils.convertToCamelCase(subTableFkName);

		context.put("subTable", subTable);
		context.put("subTableName", subTableName);
		context.put("subTableFkName", subTableFkName);
		context.put("subTableFkClassName", subTableFkClassName);
		context.put("subTableFkclassName", org.apache.commons.lang3.StringUtils.uncapitalize(subTableFkClassName));
		context.put("subClassName", subClassName);
		context.put("subclassName", org.apache.commons.lang3.StringUtils.uncapitalize(subClassName));
		context.put("subImportList", getImportList(genTable.getSubTable()));
	}

	/**
	 * Gets the template list.
	 *
	 * @param tplCategory the tpl category
	 * @return the template list
	 */
	public static List<String> getTemplateList(String tplCategory) {
		final List<String> templates = new ArrayList<String>();
		templates.add("vm/java/domain.java.vm");
		templates.add("vm/java/mapper.java.vm");
		templates.add("vm/java/service.java.vm");
		templates.add("vm/java/serviceImpl.java.vm");
		templates.add("vm/java/controller.java.vm");
		templates.add("vm/xml/mapper.xml.vm");
		if (GenConstants.TPL_CRUD.equals(tplCategory)) {
			templates.add("vm/html/list.html.vm");
		} else if (GenConstants.TPL_TREE.equals(tplCategory)) {
			templates.add("vm/html/tree.html.vm");
			templates.add("vm/html/list-tree.html.vm");
		} else if (GenConstants.TPL_SUB.equals(tplCategory)) {
			templates.add("vm/html/list.html.vm");
			templates.add("vm/java/sub-domain.java.vm");
		}
		templates.add("vm/html/add.html.vm");
		templates.add("vm/html/edit.html.vm");
		templates.add("vm/sql/sql.vm");
		return templates;
	}

	/**
	 * Gets the file name.
	 *
	 * @param template the template
	 * @param genTable the gen table
	 * @return the file name
	 */
	public static String getFileName(String template, GenTable genTable) {
		// 文件名称
		String fileName = "";
		// 包路径
		final String packageName = genTable.getPackageName();
		// 模块名
		final String moduleName = genTable.getModuleName();
		// 大写类名
		final String className = genTable.getClassName();
		// 业务名称
		final String businessName = genTable.getBusinessName();

		final String javaPath = PROJECT_PATH + "/"
				+ org.apache.commons.lang3.StringUtils.replace(packageName, ".", "/");
		final String mybatisPath = MYBATIS_PATH + "/" + moduleName;
		final String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + businessName;

		if (template.contains("domain.java.vm")) {
			fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
		}
		if (template.contains("sub-domain.java.vm")
				&& org.apache.commons.lang3.StringUtils.equals(GenConstants.TPL_SUB, genTable.getTplCategory())) {
			fileName = StringUtils.format("{}/domain/{}.java", javaPath, genTable.getSubTable().getClassName());
		} else if (template.contains("mapper.java.vm")) {
			fileName = StringUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
		} else if (template.contains("service.java.vm")) {
			fileName = StringUtils.format("{}/service/I{}Service.java", javaPath, className);
		} else if (template.contains("serviceImpl.java.vm")) {
			fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
		} else if (template.contains("controller.java.vm")) {
			fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
		} else if (template.contains("mapper.xml.vm")) {
			fileName = StringUtils.format("{}/{}Mapper.xml", mybatisPath, className);
		} else if (template.contains("list.html.vm")) {
			fileName = StringUtils.format("{}/{}.html", htmlPath, businessName);
		} else if (template.contains("list-tree.html.vm")) {
			fileName = StringUtils.format("{}/{}.html", htmlPath, businessName);
		} else if (template.contains("tree.html.vm")) {
			fileName = StringUtils.format("{}/tree.html", htmlPath);
		} else if (template.contains("add.html.vm")) {
			fileName = StringUtils.format("{}/add.html", htmlPath);
		} else if (template.contains("edit.html.vm")) {
			fileName = StringUtils.format("{}/edit.html", htmlPath);
		} else if (template.contains("sql.vm")) {
			fileName = businessName + "Menu.sql";
		}
		return fileName;
	}

	/**
	 * Gets the project path.
	 *
	 * @return the project path
	 */
	public static String getProjectPath() {
		final String packageName = GenConfig.getPackageName();
		final StringBuffer projectPath = new StringBuffer();
		projectPath.append("main/java/");
		projectPath.append(packageName.replace(".", "/"));
		projectPath.append("/");
		return projectPath.toString();
	}

	/**
	 * Gets the package prefix.
	 *
	 * @param packageName the package name
	 * @return the package prefix
	 */
	public static String getPackagePrefix(String packageName) {
		final int lastIndex = packageName.lastIndexOf(".");
		final String basePackage = StringUtils.substring(packageName, 0, lastIndex);
		return basePackage;
	}

	/**
	 * Gets the import list.
	 *
	 * @param genTable the gen table
	 * @return the import list
	 */
	public static HashSet<String> getImportList(GenTable genTable) {
		final List<GenTableColumn> columns = genTable.getColumns();
		final GenTable subGenTable = genTable.getSubTable();
		final HashSet<String> importList = new HashSet<String>();
		if (StringUtils.isNotNull(subGenTable)) {
			importList.add("java.util.List");
		}
		for (final GenTableColumn column : columns) {
			if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			} else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
				importList.add("java.math.BigDecimal");
			}
		}
		return importList;
	}

	/**
	 * Gets the permission prefix.
	 *
	 * @param moduleName   the module name
	 * @param businessName the business name
	 * @return the permission prefix
	 */
	public static String getPermissionPrefix(String moduleName, String businessName) {
		return StringUtils.format("{}:{}", moduleName, businessName);
	}

	/**
	 * Gets the parent menu id.
	 *
	 * @param paramsObj the params obj
	 * @return the parent menu id
	 */
	public static String getParentMenuId(JSONObject paramsObj) {
		if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)) {
			return paramsObj.getString(GenConstants.PARENT_MENU_ID);
		}
		return DEFAULT_PARENT_MENU_ID;
	}

	/**
	 * Gets the treecode.
	 *
	 * @param paramsObj the params obj
	 * @return the treecode
	 */
	public static String getTreecode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
		}
		return org.apache.commons.lang3.StringUtils.EMPTY;
	}

	/**
	 * Gets the tree parent code.
	 *
	 * @param paramsObj the params obj
	 * @return the tree parent code
	 */
	public static String getTreeParentCode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		return org.apache.commons.lang3.StringUtils.EMPTY;
	}

	/**
	 * Gets the tree name.
	 *
	 * @param paramsObj the params obj
	 * @return the tree name
	 */
	public static String getTreeName(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
		}
		return org.apache.commons.lang3.StringUtils.EMPTY;
	}

	/**
	 * Gets the expand column.
	 *
	 * @param genTable the gen table
	 * @return the expand column
	 */
	public static int getExpandColumn(GenTable genTable) {
		final String options = genTable.getOptions();
		final JSONObject paramsObj = JSON.parseObject(options);
		final String treeName = paramsObj.getString(GenConstants.TREE_NAME);
		int num = 0;
		for (final GenTableColumn column : genTable.getColumns()) {
			if (column.isList()) {
				num++;
				final String columnName = column.getColumnName();
				if (columnName.equals(treeName)) {
					break;
				}
			}
		}
		return num;
	}
}