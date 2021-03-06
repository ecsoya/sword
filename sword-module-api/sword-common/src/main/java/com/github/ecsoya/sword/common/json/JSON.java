package com.github.ecsoya.sword.common.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * The Class JSON.
 */
public class JSON {

	/** The Constant DEFAULT_FAIL. */
	public static final String DEFAULT_FAIL = "\"Parse failed\"";

	/** The Constant objectMapper. */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/** The Constant objectWriter. */
	private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

	/**
	 * Marshal.
	 *
	 * @param file  the file
	 * @param value the value
	 * @throws Exception the exception
	 */
	public static void marshal(File file, Object value) throws Exception {
		try {
			objectWriter.writeValue(file, value);
		} catch (final JsonGenerationException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Marshal.
	 *
	 * @param os    the os
	 * @param value the value
	 * @throws Exception the exception
	 */
	public static void marshal(OutputStream os, Object value) throws Exception {
		try {
			objectWriter.writeValue(os, value);
		} catch (final JsonGenerationException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Marshal.
	 *
	 * @param value the value
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshal(Object value) throws Exception {
		try {
			return objectWriter.writeValueAsString(value);
		} catch (final JsonGenerationException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Marshal bytes.
	 *
	 * @param value the value
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public static byte[] marshalBytes(Object value) throws Exception {
		try {
			return objectWriter.writeValueAsBytes(value);
		} catch (final JsonGenerationException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Unmarshal.
	 *
	 * @param <T>       the generic type
	 * @param file      the file
	 * @param valueType the value type
	 * @return the t
	 * @throws Exception the exception
	 */
	public static <T> T unmarshal(File file, Class<T> valueType) throws Exception {
		try {
			return objectMapper.readValue(file, valueType);
		} catch (final JsonParseException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Unmarshal.
	 *
	 * @param <T>       the generic type
	 * @param is        the is
	 * @param valueType the value type
	 * @return the t
	 * @throws Exception the exception
	 */
	public static <T> T unmarshal(InputStream is, Class<T> valueType) throws Exception {
		try {
			return objectMapper.readValue(is, valueType);
		} catch (final JsonParseException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Unmarshal.
	 *
	 * @param <T>       the generic type
	 * @param str       the str
	 * @param valueType the value type
	 * @return the t
	 * @throws Exception the exception
	 */
	public static <T> T unmarshal(String str, Class<T> valueType) throws Exception {
		try {
			return objectMapper.readValue(str, valueType);
		} catch (final JsonParseException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * Unmarshal.
	 *
	 * @param <T>       the generic type
	 * @param bytes     the bytes
	 * @param valueType the value type
	 * @return the t
	 * @throws Exception the exception
	 */
	public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws Exception {
		try {
			if (bytes == null) {
				bytes = new byte[0];
			}
			return objectMapper.readValue(bytes, 0, bytes.length, valueType);
		} catch (final JsonParseException e) {
			throw new Exception(e);
		} catch (final JsonMappingException e) {
			throw new Exception(e);
		} catch (final IOException e) {
			throw new Exception(e);
		}
	}
}
