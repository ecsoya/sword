package com.github.ecsoya.sword.common.core.controller;

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

import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.AjaxResult.Type;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.page.PageDomain;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.core.page.TableSupport;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.sql.SqlUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

/**
 * The Class BaseController.
 */
public class BaseController {

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * Inits the binder.
	 *
	 * @param binder the binder
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
	 * Start page.
	 *
	 * @return the page
	 */
	protected Page<?> startPage() {
		return startPage(TableSupport.buildPageRequest());
	}

	/**
	 * Start page.
	 *
	 * @param pageDomain the page domain
	 * @return the page
	 */
	protected Page<?> startPage(PageDomain pageDomain) {
		final Integer pageNum = pageDomain.getPageNum();
		final Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			final String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			return PageMethod.startPage(pageNum, pageSize, orderBy);
		} else if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			final String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.orderBy(orderBy);
		}
		return null;
	}

	/**
	 * Start page.
	 *
	 * @param force the force
	 */
	protected void startPage(boolean force) {
		final PageDomain pageDomain = TableSupport.buildPageRequest();
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
			final String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.startPage(pageNum, pageSize, orderBy);
		}
	}

	/**
	 * Start page.
	 *
	 * @param orderBy the order by
	 */
	protected void startPage(String orderBy) {
		final PageDomain pageDomain = TableSupport.buildPageRequest();
		final Integer pageNum = pageDomain.getPageNum();
		final Integer pageSize = pageDomain.getPageSize();

		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize) && StringUtils.isNotEmpty(orderBy)) {
			PageMethod.startPage(pageNum, pageSize, SqlUtil.escapeOrderBySql(orderBy));
		} else if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			PageMethod.startPage(pageNum, pageSize);
		} else if (StringUtils.isNotEmpty(orderBy)) {
			PageMethod.orderBy(SqlUtil.escapeOrderBySql(orderBy));
		}
	}

	/**
	 * Start order by.
	 */
	protected void startOrderBy() {
		final PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			final String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageMethod.orderBy(orderBy);
		}
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return ServletUtils.getRequest();
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return ServletUtils.getResponse();
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Gets the data table.
	 *
	 * @param list the list
	 * @return the data table
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(List<?> list) {
		final TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}

	/**
	 * To ajax.
	 *
	 * @param rows the rows
	 * @return the ajax result
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? success() : error();
	}

	/**
	 * To ajax.
	 *
	 * @param result the result
	 * @return the ajax result
	 */
	protected AjaxResult toAjax(boolean result) {
		return result ? success() : error();
	}

	/**
	 * To ajax.
	 *
	 * @param result the result
	 * @return the ajax result
	 */
	protected AjaxResult toAjax(CommonResult<?> result) {
		if (result != null && result.isSuccess()) {
			return success();
		} else if (result != null) {
			return error(result.getInfo());
		}
		return error();
	}

	/**
	 * Success.
	 *
	 * @return the ajax result
	 */
	public AjaxResult success() {
		return AjaxResult.success();
	}

	/**
	 * Error.
	 *
	 * @return the ajax result
	 */
	public AjaxResult error() {
		return AjaxResult.error();
	}

	/**
	 * Success.
	 *
	 * @param message the message
	 * @return the ajax result
	 */
	public AjaxResult success(String message) {
		return AjaxResult.success(message);
	}

	/**
	 * Error.
	 *
	 * @param message the message
	 * @return the ajax result
	 */
	public AjaxResult error(String message) {
		return AjaxResult.error(message);
	}

	/**
	 * Error.
	 *
	 * @param type    the type
	 * @param message the message
	 * @return the ajax result
	 */
	public AjaxResult error(Type type, String message) {
		return new AjaxResult(type, message);
	}

	/**
	 * Redirect.
	 *
	 * @param url the url
	 * @return the string
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}
}
