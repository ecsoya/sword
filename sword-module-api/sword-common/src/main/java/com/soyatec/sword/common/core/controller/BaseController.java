package com.soyatec.sword.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.AjaxResult.Type;
import com.soyatec.sword.common.core.page.PageDomain;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.core.page.TableSupport;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.ServletUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.sql.SqlUtil;

/**
 * web层通用数据处理
 * 
 * @author Jin Liu (angryred@qq.com)
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	/**
	 * 设置请求分页数据
	 */
	protected void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.startPage(pageNum, pageSize, orderBy);
		}
	}

	protected void startPage(boolean force) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (force) {
			if (pageNum == null) {
				pageNum = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
		}
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.startPage(pageNum, pageSize, orderBy);
		}
	}

	/**
	 * 设置请求分页数据
	 */
	protected void startPage(String orderBy) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();

		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize) && StringUtils.isNotEmpty(orderBy)) {
			PageMethod.startPage(pageNum, pageSize, SqlUtil.escapeOrderBySql(orderBy));
		} else if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			PageMethod.startPage(pageNum, pageSize);
		} else if (StringUtils.isNotEmpty(orderBy)) {
			PageMethod.orderBy(SqlUtil.escapeOrderBySql(orderBy));
		}
	}

	/**
	 * 设置请求排序数据
	 */
	protected void startOrderBy() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.orderBy(orderBy);
		}
	}

	/**
	 * 获取request
	 */
	public HttpServletRequest getRequest() {
		return ServletUtils.getRequest();
	}

	/**
	 * 获取response
	 */
	public HttpServletResponse getResponse() {
		return ServletUtils.getResponse();
	}

	/**
	 * 获取session
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 响应请求分页数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}

	/**
	 * 响应返回结果
	 * 
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? success() : error();
	}

	/**
	 * 响应返回结果
	 * 
	 * @param result 结果
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(boolean result) {
		return result ? success() : error();
	}

	/**
	 * 返回成功
	 */
	public AjaxResult success() {
		return AjaxResult.success();
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error() {
		return AjaxResult.error();
	}

	/**
	 * 返回成功消息
	 */
	public AjaxResult success(String message) {
		return AjaxResult.success(message);
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error(String message) {
		return AjaxResult.error(message);
	}

	/**
	 * 返回错误码消息
	 */
	public AjaxResult error(Type type, String message) {
		return new AjaxResult(type, message);
	}

	/**
	 * 页面跳转
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}
}
