package com.soyatec.sword.common.core.page;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 表格分页数据对象
 *
 * @author iptv
 */
public class SelectDataInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 总记录数 */
	private boolean more;

	private int page;

	/** 列表数据 */
	private List<?> rows;

	/** 消息状态码 */
	private int code;

	/** 消息内容 */
	private int msg;

	/**
	 * 表格数据对象
	 */
	public SelectDataInfo() {
	}

	/**
	 * 分页
	 *
	 * @param list  列表数据
	 * @param total 总记录数
	 */
	public SelectDataInfo(List<?> list, int page, boolean more) {
		this.rows = list;
		this.page = page;
		this.more = more;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public static class SelectItem {
		private String id;

		private String text;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> SelectDataInfo build(List<T> list, int page, String idFieldName, String textFiledName,
			Class<T> type) {
		final PageInfo pageInfo = new PageInfo(list);
		final boolean more = pageInfo.getPageNum() < pageInfo.getPages();
		final List<SelectItem> items = new ArrayList<>();
		for (final T object : list) {
			final SelectItem item = new SelectItem();
			final Object id = getFieldValue(object, idFieldName);
			if (id != null) {
				item.setId(id.toString());
			}
			final Object text = getFieldValue(object, textFiledName);
			if (text != null) {
				item.setText(text.toString());
			}
			items.add(item);
		}
		return new SelectDataInfo(items, page, more);
	}

	private static Object getFieldValue(Object object, String fieldName) {
		if (object == null || fieldName == null) {
			return null;
		}
		final PropertyDescriptor descriptor = org.springframework.beans.BeanUtils
				.getPropertyDescriptor(object.getClass(), fieldName);
		if (descriptor != null) {
			final Method readMethod = descriptor.getReadMethod();
			if (readMethod != null) {
				try {
					return readMethod.invoke(object);
				} catch (final Exception e) {
				}
			}
		}
		try {
			final Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (final Exception e) {
			return null;
		}
	}
}