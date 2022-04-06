package com.github.ecsoya.sword.common.core.page;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * The Class SelectDataInfo.
 */
public class SelectDataInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The more. */
	private boolean more;

	/** The page. */
	private int page;

	/** The rows. */
	private List<?> rows;

	/** The code. */
	private int code;

	/** The msg. */
	private int msg;

	/**
	 * Instantiates a new select data info.
	 */
	public SelectDataInfo() {
	}

	/**
	 * Instantiates a new select data info.
	 *
	 * @param list the list
	 * @param page the page
	 * @param more 总记录数.
	 */
	public SelectDataInfo(List<?> list, int page, boolean more) {
		this.rows = list;
		this.page = page;
		this.more = more;
	}

	/**
	 * Gets the 列表数据.
	 *
	 * @return the 列表数据
	 */
	public List<?> getRows() {
		return rows;
	}

	/**
	 * Sets the 列表数据.
	 *
	 * @param rows the new 列表数据
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	/**
	 * Gets the 消息状态码.
	 *
	 * @return the 消息状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the 消息状态码.
	 *
	 * @param code the new 消息状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the 消息内容.
	 *
	 * @return the 消息内容
	 */
	public int getMsg() {
		return msg;
	}

	/**
	 * Sets the 消息内容.
	 *
	 * @param msg the new 消息内容
	 */
	public void setMsg(int msg) {
		this.msg = msg;
	}

	/**
	 * Checks if is 总记录数.
	 *
	 * @return the 总记录数
	 */
	public boolean isMore() {
		return more;
	}

	/**
	 * Sets the 总记录数.
	 *
	 * @param more the new 总记录数
	 */
	public void setMore(boolean more) {
		this.more = more;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * The Class SelectItem.
	 */
	public static class SelectItem {

		/** The id. */
		private String id;

		/** The text. */
		private String text;

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * Gets the text.
		 *
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * Sets the text.
		 *
		 * @param text the new text
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return String.format("%s=%s", id, text);
		}
	}

	/**
	 * Builds the.
	 *
	 * @param <T>           the generic type
	 * @param list          the list
	 * @param page          the page
	 * @param idFieldName   the id field name
	 * @param textFiledName the text filed name
	 * @param type          the type
	 * @return the select data info
	 */
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

	/**
	 * Gets the field value.
	 *
	 * @param object    the object
	 * @param fieldName the field name
	 * @return the field value
	 */
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