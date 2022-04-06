package com.github.ecsoya.sword.common.core.page;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.ServletUtils;

/**
 * The Class TableSupport.
 */
public class TableSupport {

	/**
	 * Gets the page domain.
	 *
	 * @return the page domain
	 */
	public static PageDomain getPageDomain() {
		final PageDomain pageDomain = new PageDomain();
		Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
		if (pageNum == null) {
			pageNum = ServletUtils.getParameterToInt("pageNo");
		}
		pageDomain.setPageNum(pageNum);
		pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
		pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
		pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
		return pageDomain;
	}

	/**
	 * Builds the page request.
	 *
	 * @return the page domain
	 */
	public static PageDomain buildPageRequest() {
		return getPageDomain();
	}
}
