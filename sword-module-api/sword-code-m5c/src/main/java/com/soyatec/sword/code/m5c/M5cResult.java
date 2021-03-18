package com.soyatec.sword.code.m5c;

import com.soyatec.sword.common.utils.StringUtils;

public class M5cResult {

	private boolean success;

	private String msgid;

	private String error;

	public M5cResult(boolean success, String msgid, String error) {
		this.setSuccess(success);
		this.setMsgid(msgid);
		this.setError(error);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "MessageResult [success=" + success + ", msgid=" + msgid + ", error=" + error + "]";
	}

	/**
	 * success:msgid 提交成功。 error:msgid 提交失败 error:Missing username 用户名为空
	 * error:Missing password 密码为空 error:Missing apikey APIKEY为空 error:Missing
	 * recipient 手机号码为空 error:Missing message content 短信内容为空 error:Account is
	 * blocked 帐号被禁用 error:Unrecognized encoding 编码未能识别 error:APIKEY or password_md5
	 * error APIKEY或密码错误 error:Unauthorized IP address 未授权 IP 地址 error:Account
	 * balance is insufficient 余额不足
	 */
	public static M5cResult create(String res) {
		if (StringUtils.isEmpty(res)) {
			return new M5cResult(false, null, "未知错误");
		} else if (res.startsWith("success:")) {
			return new M5cResult(true, res.substring("success:".length()), null);
		} else if (res.startsWith("error:")) {
			String rawError = res.substring("error:".length());
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
