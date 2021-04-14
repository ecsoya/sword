package com.soyatec.sword.common.core.page;

import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class TableSupport {
	/**
	 * 封装分页对象
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

	public static PageDomain buildPageRequest() {
		return getPageDomain();
	}
}
