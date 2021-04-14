package com.soyatec.sword.common.exception.file;

import com.soyatec.sword.common.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class FileException extends BaseException {
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
