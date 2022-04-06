package com.github.ecsoya.sword.code.m5c;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class M5cResult.
 */
public class M5cResult {

	/** The success. */
	private boolean success;

	/** The msgid. */
	private String msgid;

	/** The error. */
	private String error;

	/**
	 * Instantiates a new m 5 c result.
	 *
	 * @param success the success
	 * @param msgid   the msgid
	 * @param error   the error
	 */
	public M5cResult(boolean success, String msgid, String error) {
		this.setSuccess(success);
		this.setMsgid(msgid);
		this.setError(error);
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Gets the msgid.
	 *
	 * @return the msgid
	 */
	public String getMsgid() {
		return msgid;
	}

	/**
	 * Sets the msgid.
	 *
	 * @param msgid the new msgid
	 */
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "MessageResult [success=" + success + ", msgid=" + msgid + ", error=" + error + "]";
	}

	/**
	 * Creates the.
	 *
	 * @param res the res
	 * @return the m 5 c result
	 */
	public static M5cResult create(String res) {
		if (StringUtils.isEmpty(res)) {
			return new M5cResult(false, null, "未知错误");
		} else if (res.startsWith("success:")) {
			return new M5cResult(true, res.substring("success:".length()), null);
		} else if (res.startsWith("error:")) {
			final String rawError = res.substring("error:".length());
			String error = null;
			String msgid = null;
			if (rawError.contains("Missing username")) {
				error = "用户名为空";
			} else if (rawError.contains("Missing password")) {
				error = "密码为空";
			} else if (rawError.contains("Missing apikey")) {
				error = "APIKEY为空";
			} else if (rawError.contains("Missing recipient")) {
				error = "手机号码为空";
			} else if (rawError.contains("Missing message content")) {
				error = "短信内容为空";
			} else if (rawError.contains("Account is blocked")) {
				error = "帐号被禁用";
			} else if (rawError.contains("Unrecognized encoding")) {
				error = "编码未能识别";
			} else if (rawError.contains("APIKEY")) {
				error = "APIKEY或密码错误";
			} else if (rawError.contains("Unauthorized IP address")) {
				error = "未授权 IP 地址";
			} else if (rawError.contains("Account balance is insufficient")) {
				error = "余额不足";
			} else {
				msgid = rawError;
			}
			return new M5cResult(false, msgid, error);
		}
		return new M5cResult(false, null, res);
	}
}
