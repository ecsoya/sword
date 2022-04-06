package com.github.ecsoya.sword.common.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class JSONObject.
 */
public class JSONObject extends LinkedHashMap<String, Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant arrayNamePattern. */
	private static final Pattern arrayNamePattern = Pattern.compile("(\\w+)((\\[\\d+\\])+)");

	/** The Constant objectMapper. */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * The Class JSONArray.
	 */
	public static class JSONArray extends ArrayList<Object> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new JSON array.
		 */
		public JSONArray() {
			super();
		}

		/**
		 * Instantiates a new JSON array.
		 *
		 * @param size the size
		 */
		public JSONArray(int size) {
			super(size);
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			try {
				return JSON.marshal(this);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Sets the.
		 *
		 * @param index   the index
		 * @param element the element
		 * @return the object
		 */
		@Override
		public Object set(int index, Object element) {
			return super.set(index, transfer(element));
		}

		/**
		 * Adds the.
		 *
		 * @param element the element
		 * @return true, if successful
		 */
		@Override
		public boolean add(Object element) {
			return super.add(transfer(element));
		}

		/**
		 * Adds the.
		 *
		 * @param index   the index
		 * @param element the element
		 */
		@Override
		public void add(int index, Object element) {
			super.add(index, transfer(element));
		}
	}

	/**
	 * Instantiates a new JSON object.
	 */
	public JSONObject() {
		super();
	}

	/**
	 * Instantiates a new JSON object.
	 *
	 * @param other the other
	 */
	public JSONObject(final JSONObject other) {
		super(other);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		try {
			return JSON.marshal(this);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To compact string.
	 *
	 * @return the string
	 */
	public String toCompactString() {
		try {
			return objectMapper.writeValueAsString(this);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Int value.
	 *
	 * @param name the name
	 * @return the integer
	 */
	public Integer intValue(final String name) {
		return valueAsInt(value(name));
	}

	/**
	 * Int value.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the integer
	 */
	public Integer intValue(final String name, final Integer defaultValue) {
		return StringUtils.nvl(intValue(name), defaultValue);
	}

	/**
	 * Long value.
	 *
	 * @param name the name
	 * @return the long
	 */
	public Long longValue(final String name) {
		return valueAsLong(value(name));
	}

	/**
	 * Long value.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the long
	 */
	public Long longValue(final String name, final Long defaultValue) {
		return StringUtils.nvl(longValue(name), defaultValue);
	}

	/**
	 * Bool value.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	public Boolean boolValue(final String name) {
		return valueAsBool(value(name));
	}

	/**
	 * Bool value.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the boolean
	 */
	public Boolean boolValue(final String name, final Boolean defaultValue) {
		return StringUtils.nvl(boolValue(name), defaultValue);
	}

	/**
	 * Str value.
	 *
	 * @param name the name
	 * @return the string
	 */
	public String strValue(final String name) {
		return valueAsStr(value(name));
	}

	/**
	 * Str value.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the string
	 */
	public String strValue(final String name, final String defaultValue) {
		return StringUtils.nvl(strValue(name), defaultValue);
	}

	/**
	 * Value.
	 *
	 * @param name the name
	 * @return the object
	 */
	public Object value(final String name) {
		final int indexDot = name.indexOf('.');
		if (indexDot >= 0) {
			return obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1));
		} else {
			final Matcher matcher = arrayNamePattern.matcher(name);
			if (matcher.find()) {
				return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Object>() {
					@Override
					public Object callback(JSONArray arr, int index) {
						return elementAt(arr, index);
					}
				});
			} else {
				return get(name);
			}
		}
	}

	/**
	 * Value.
	 *
	 * @param name  the name
	 * @param value the value
	 * @return the JSON object
	 */
	public JSONObject value(final String name, final Object value) {
		final int indexDot = name.indexOf('.');
		if (indexDot >= 0) {
			obj(name.substring(0, indexDot)).value(name.substring(indexDot + 1), value);
		} else {
			final Matcher matcher = arrayNamePattern.matcher(name);
			if (matcher.find()) {
				endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<Void>() {
					@Override
					public Void callback(JSONArray arr, int index) {
						elementAt(arr, index, value);
						return null;
					}
				});
			} else {
				set(name, value);
			}
		}
		return this;
	}

	/**
	 * Obj.
	 *
	 * @param name the name
	 * @return the JSON object
	 */
	public JSONObject obj(final String name) {
		final Matcher matcher = arrayNamePattern.matcher(name);
		if (matcher.find()) {
			return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<JSONObject>() {
				@Override
				public JSONObject callback(JSONArray arr, int index) {
					return objAt(arr, index);
				}
			});
		} else {
			JSONObject obj = getObj(name);
			if (obj == null) {
				obj = new JSONObject();
				put(name, obj);
			}
			return obj;
		}
	}

	/**
	 * Arr.
	 *
	 * @param name the name
	 * @return the JSON array
	 */
	public JSONArray arr(final String name) {
		JSONArray arr = getArr(name);
		if (arr == null) {
			arr = new JSONArray();
			put(name, arr);
		}
		return arr;
	}

	/**
	 * Gets the obj.
	 *
	 * @param name the name
	 * @return the obj
	 */
	public JSONObject getObj(final String name) {
		return (JSONObject) get(name);
	}

	/**
	 * Gets the arr.
	 *
	 * @param name the name
	 * @return the arr
	 */
	public JSONArray getArr(final String name) {
		return (JSONArray) get(name);
	}

	/**
	 * Gets the int.
	 *
	 * @param name the name
	 * @return the int
	 */
	public Integer getInt(final String name) {
		return valueAsInt(get(name));
	}

	/**
	 * Gets the int.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the int
	 */
	public Integer getInt(final String name, Integer defaultValue) {
		return StringUtils.nvl(getInt(name), defaultValue);
	}

	/**
	 * Gets the long.
	 *
	 * @param name the name
	 * @return the long
	 */
	public Long getLong(final String name) {
		return valueAsLong(get(name));
	}

	/**
	 * Gets the long.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the long
	 */
	public Long getLong(final String name, Long defaultValue) {
		return StringUtils.nvl(getLong(name), defaultValue);
	}

	/**
	 * Gets the str.
	 *
	 * @param name the name
	 * @return the str
	 */
	public String getStr(final String name) {
		return valueAsStr(get(name));
	}

	/**
	 * Gets the str.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the str
	 */
	public String getStr(final String name, final String defaultValue) {
		return StringUtils.nvl(getStr(name), defaultValue);
	}

	/**
	 * Gets the bool.
	 *
	 * @param name the name
	 * @return the bool
	 */
	public Boolean getBool(final String name) {
		return valueAsBool(get(name));
	}

	/**
	 * Gets the bool.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the bool
	 */
	public Boolean getBool(final String name, final Boolean defaultValue) {
		return StringUtils.nvl(getBool(name), defaultValue);
	}

	/**
	 * Sets the.
	 *
	 * @param name  the name
	 * @param value the value
	 * @return the JSON object
	 */
	public JSONObject set(final String name, final Object value) {
		put(name, value);
		return this;
	}

	/**
	 * As bean.
	 *
	 * @param <T>       the generic type
	 * @param beanClass the bean class
	 * @return the t
	 */
	public <T> T asBean(Class<T> beanClass) {
		try {
			return JSON.unmarshal(JSON.marshal(this), beanClass);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Put.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the object
	 */
	@Override
	public Object put(String key, Object value) {
		return super.put(key, transfer(value));
	}

	/**
	 * Value as int.
	 *
	 * @param value the value
	 * @return the integer
	 */
	public static Integer valueAsInt(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			return Integer.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? 1 : 0;
		} else {
			return null;
		}
	}

	/**
	 * Value as long.
	 *
	 * @param value the value
	 * @return the long
	 */
	public static Long valueAsLong(Object value) {
		if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Number) {
			return ((Number) value).longValue();
		} else if (value instanceof String) {
			return Long.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? 1L : 0L;
		} else {
			return null;
		}
	}

	/**
	 * Value as str.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String valueAsStr(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	/**
	 * Value as bool.
	 *
	 * @param value the value
	 * @return the boolean
	 */
	public static Boolean valueAsBool(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue() != 0.0;
		} else if (value instanceof String) {
			return Boolean.valueOf((String) value);
		} else {
			return null;
		}
	}

	/**
	 * Transfer.
	 *
	 * @param value the value
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	private static Object transfer(final Object value) {
		if (!(value instanceof JSONObject) && value instanceof Map) {
			return toObj((Map<String, Object>) value);
		} else if (!(value instanceof JSONArray) && value instanceof Collection) {
			return toArr((Collection<Object>) value);
		} else {
			return value;
		}
	}

	/**
	 * To arr.
	 *
	 * @param list the list
	 * @return the JSON array
	 */
	private static JSONArray toArr(final Collection<Object> list) {
		final JSONArray arr = new JSONArray(list.size());
		for (final Object element : list) {
			arr.add(element);
		}
		return arr;
	}

	/**
	 * To obj.
	 *
	 * @param map the map
	 * @return the JSON object
	 */
	private static JSONObject toObj(final Map<String, Object> map) {
		final JSONObject obj = new JSONObject();
		for (final Map.Entry<String, Object> ent : map.entrySet()) {
			obj.put(ent.getKey(), transfer(ent.getValue()));
		}
		return obj;
	}

	/**
	 * Array at.
	 *
	 * @param arr   the arr
	 * @param index the index
	 * @return the JSON array
	 */
	private static JSONArray arrayAt(JSONArray arr, int index) {
		expand(arr, index);
		if (arr.get(index) == null) {
			arr.set(index, new JSONArray());
		}
		return (JSONArray) arr.get(index);
	}

	/**
	 * Obj at.
	 *
	 * @param arr   the arr
	 * @param index the index
	 * @return the JSON object
	 */
	private static JSONObject objAt(final JSONArray arr, int index) {
		expand(arr, index);
		if (arr.get(index) == null) {
			arr.set(index, new JSONObject());
		}
		return (JSONObject) arr.get(index);
	}

	/**
	 * Element at.
	 *
	 * @param arr   the arr
	 * @param index the index
	 * @param value the value
	 */
	private static void elementAt(final JSONArray arr, final int index, final Object value) {
		expand(arr, index).set(index, value);
	}

	/**
	 * Element at.
	 *
	 * @param arr   the arr
	 * @param index the index
	 * @return the object
	 */
	private static Object elementAt(final JSONArray arr, final int index) {
		return expand(arr, index).get(index);
	}

	/**
	 * Expand.
	 *
	 * @param arr   the arr
	 * @param index the index
	 * @return the JSON array
	 */
	private static JSONArray expand(final JSONArray arr, final int index) {
		while (arr.size() <= index) {
			arr.add(null);
		}
		return arr;
	}

	/**
	 * The Interface EndArrayCallback.
	 *
	 * @param <T> the generic type
	 */
	private interface EndArrayCallback<T> {

		/**
		 * Callback.
		 *
		 * @param arr   the arr
		 * @param index the index
		 * @return the t
		 */
		T callback(JSONArray arr, int index);
	}

	/**
	 * End array.
	 *
	 * @param <T>        the generic type
	 * @param name       the name
	 * @param indexesStr the indexes str
	 * @param callback   the callback
	 * @return the t
	 */
	private <T> T endArray(final String name, final String indexesStr, final EndArrayCallback<T> callback) {
		JSONArray endArr = arr(name);
		final int[] indexes = parseIndexes(indexesStr);
		int i = 0;
		while (i < indexes.length - 1) {
			endArr = arrayAt(endArr, indexes[i++]);
		}
		return callback.callback(endArr, indexes[i]);
	}

	/**
	 * Parses the indexes.
	 *
	 * @param s the s
	 * @return the int[]
	 */
	private static int[] parseIndexes(final String s) {
		int[] indexes = null;
		final List<Integer> list = new ArrayList<Integer>();

		final StringTokenizer st = new StringTokenizer(s, "[]");
		while (st.hasMoreTokens()) {
			final int index = Integer.valueOf(st.nextToken());
			if (index < 0) {
				throw new RuntimeException(String.format("Illegal index %1$d in \"%2$s\"", index, s));
			}

			list.add(index);
		}

		indexes = new int[list.size()];
		int i = 0;
		for (final Integer tmp : list.toArray(new Integer[list.size()])) {
			indexes[i++] = tmp;
		}

		return indexes;
	}
}
