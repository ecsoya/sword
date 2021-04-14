package com.soyatec.sword.zbx.domain;

import java.util.HashMap;
import java.util.Map;

import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.zbx.utils.HttpUtil;

/**
 * 开放接口返回数据
 *
 * @author ecsoya
 *
 */
public class ZbxOrderRawData extends ZbxResponse {

	public static final String WITHDRAWAL = "withdrawal";
	public static final String DEPOSIT = "deposit";

	// Status
	public static final String DEPOSIT_NONE = "0";
	public static final String DEPOSIT_SUCCESS = "2";

	public static final String WITHDRAWAL_NONE = "0";
	public static final String WITHDRAWAL_FAILURE = "1";
	public static final String WITHDRAWAL_SUCCESS = "2";
	public static final String WITHDRAWAL_CANCEL = "3";
	public static final String WITHDRAWAL_CHECKING = "4";
	public static final String WITHDRAWAL_CONFIRMING = "5";

	public static final String IS_INNER_TRUE = "1";
	public static final String IS_INNER_FALSE = "0";

	/**
	 * 币种类型
	 */
	private String symbol;

	/**
	 * 数量
	 */
	private String amount;

	/**
	 * 提币业务流水号
	 */
	private String orderNo;

	/**
	 * 交易哈希
	 */
	private String txId;

	/**
	 * 充值: 0:待确认 2:成功 提币： 0:提交 1:失败 2:成功 3:取消 4:审核中 5:待确认
	 *
	 */
	private String status;

	/**
	 * deposit:充值 withdrawal:提币
	 *
	 */
	private String method;

	/**
	 * 公玥
	 */
	private String accessKey;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 13位毫秒
	 */
	private String nonce;

	private String chain;

	private String isInner;

	public boolean isDeposit() {
		return DEPOSIT.equalsIgnoreCase(getMethod());
	}

	public boolean isWithdrawal() {
		return WITHDRAWAL.equals(getMethod());
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getIsInner() {
		return isInner;
	}

	public void setIsInner(String isInner) {
		this.isInner = isInner;
	}

	public boolean isInternal() {
		return IS_INNER_TRUE.equals(isInner);
	}

	@Override
	public String toString() {
		return "ZbxOrderRawData [symbol=" + symbol + ", amount=" + amount + ", orderNo=" + orderNo + ", txId=" + txId
				+ ", status=" + status + ", method=" + method + ", accessKey=" + accessKey + ", sign=" + sign
				+ ", nonce=" + nonce + ", address=" + getAddress() + ", memo=" + getMemo() + "]";
	}

	public String checkValid(String secretKey) {
		if (StringUtils.isEmpty(secretKey)) {
			return "SecretKey查询失败";
		}
		final String sign = getSign();
		if (StringUtils.isEmpty(sign)) {
			return "API签名错误";
		}
		final String nonce = getNonce();
		final long timestamp = Long.parseLong(nonce.trim());
		final long currentTimeMillis = System.currentTimeMillis();
		if ((currentTimeMillis - timestamp) > 10 * 60 * 1000) {
			return "API请求超时";
		}
		if (isWithdrawal()) {
			if (StringUtils.isEmpty(orderNo)) {
				return "API参数错误(提现订单号orderNo没有设置)";
			}
		}
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("accessKey", getAccessKey());
		params.put("symbol", getSymbol());
		params.put("nonce", getNonce());
		params.put("address", getAddress());
		params.put("amount", getAmount());
		if (StringUtils.isNotEmpty(orderNo)) {
			params.put("orderNo", getOrderNo());
		}
		if (getTxId() != null) {
			params.put("txId", getTxId());
		}
		if (!StringUtils.isEmpty(getChain())) {
			params.put("chain", getChain());
		}
		if (!StringUtils.isEmpty(getIsInner())) {
			params.put("isInner", getIsInner());
		}
		params.put("status", getStatus());
		params.put("method", getMethod());
		if (StringUtils.isNotEmpty(getMemo())) {
			params.put("memo", getMemo());
		}
		final String target = HttpUtil.getSignature(params, secretKey);
		if (!sign.equals(target)) {
			return "API签名验证未通过";
		}
		return null;
	}

}
