package com.github.ecsoya.sword.zbx.domain;

import java.util.HashMap;
import java.util.Map;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.zbx.utils.HttpUtil;

/**
 * The Class ZbxOrderRawData.
 */
public class ZbxOrderRawData extends ZbxResponse {

	/** The Constant WITHDRAWAL. */
	public static final String WITHDRAWAL = "withdrawal";

	/** The Constant DEPOSIT. */
	public static final String DEPOSIT = "deposit";

	/** The Constant DEPOSIT_NONE. */
	// Status
	public static final String DEPOSIT_NONE = "0";

	/** The Constant DEPOSIT_SUCCESS. */
	public static final String DEPOSIT_SUCCESS = "2";

	/** The Constant WITHDRAWAL_NONE. */
	public static final String WITHDRAWAL_NONE = "0";

	/** The Constant WITHDRAWAL_FAILURE. */
	public static final String WITHDRAWAL_FAILURE = "1";

	/** The Constant WITHDRAWAL_SUCCESS. */
	public static final String WITHDRAWAL_SUCCESS = "2";

	/** The Constant WITHDRAWAL_CANCEL. */
	public static final String WITHDRAWAL_CANCEL = "3";

	/** The Constant WITHDRAWAL_CHECKING. */
	public static final String WITHDRAWAL_CHECKING = "4";

	/** The Constant WITHDRAWAL_CONFIRMING. */
	public static final String WITHDRAWAL_CONFIRMING = "5";

	/** The Constant IS_INNER_TRUE. */
	public static final String IS_INNER_TRUE = "1";

	/** The Constant IS_INNER_FALSE. */
	public static final String IS_INNER_FALSE = "0";

	/** The symbol. */
	private String symbol;

	/** The amount. */
	private String amount;

	/** The order no. */
	private String orderNo;

	/** The tx id. */
	private String txId;

	/** The status. */
	private String status;

	/** The method. */
	private String method;

	/** The access key. */
	private String accessKey;

	/** The sign. */
	private String sign;

	/** The nonce. */
	private String nonce;

	/** The chain. */
	private String chain;

	/** The is inner. */
	private String isInner;

	/**
	 * Checks if is deposit.
	 *
	 * @return true, if is deposit
	 */
	public boolean isDeposit() {
		return DEPOSIT.equalsIgnoreCase(getMethod());
	}

	/**
	 * Checks if is withdrawal.
	 *
	 * @return true, if is withdrawal
	 */
	public boolean isWithdrawal() {
		return WITHDRAWAL.equals(getMethod());
	}

	/**
	 * Gets the ????????????.
	 *
	 * @return the ????????????
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the ????????????.
	 *
	 * @param symbol the new ????????????
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the ??????.
	 *
	 * @return the ??????
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the ??????.
	 *
	 * @param amount the new ??????
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Gets the ?????????????????????.
	 *
	 * @return the ?????????????????????
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the ?????????????????????.
	 *
	 * @param orderNo the new ?????????????????????
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the ????????????.
	 *
	 * @return the ????????????
	 */
	public String getTxId() {
		return txId;
	}

	/**
	 * Sets the ????????????.
	 *
	 * @param txId the new ????????????
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * Gets the ??????: 0:????????? 2:?????? ????????? 0:?????? 1:?????? 2:?????? 3:?????? 4:????????? 5:?????????.
	 *
	 * @return the ??????: 0:????????? 2:?????? ????????? 0:?????? 1:?????? 2:?????? 3:?????? 4:????????? 5:?????????
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the ??????: 0:????????? 2:?????? ????????? 0:?????? 1:?????? 2:?????? 3:?????? 4:????????? 5:?????????.
	 *
	 * @param status the new ??????: 0:????????? 2:?????? ????????? 0:?????? 1:?????? 2:?????? 3:?????? 4:????????? 5:?????????
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the deposit:?????? withdrawal:??????.
	 *
	 * @return the deposit:?????? withdrawal:??????
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the deposit:?????? withdrawal:??????.
	 *
	 * @param method the new deposit:?????? withdrawal:??????
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Gets the ??????.
	 *
	 * @return the ??????
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Sets the ??????.
	 *
	 * @param accessKey the new ??????
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * Gets the ??????.
	 *
	 * @return the ??????
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Sets the ??????.
	 *
	 * @param sign the new ??????
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * Gets the 13?????????.
	 *
	 * @return the 13?????????
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * Sets the 13?????????.
	 *
	 * @param nonce the new 13?????????
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/**
	 * Gets the chain.
	 *
	 * @return the chain
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * Sets the chain.
	 *
	 * @param chain the new chain
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * Gets the checks if is inner.
	 *
	 * @return the checks if is inner
	 */
	public String getIsInner() {
		return isInner;
	}

	/**
	 * Sets the checks if is inner.
	 *
	 * @param isInner the new checks if is inner
	 */
	public void setIsInner(String isInner) {
		this.isInner = isInner;
	}

	/**
	 * Checks if is internal.
	 *
	 * @return true, if is internal
	 */
	public boolean isInternal() {
		return IS_INNER_TRUE.equals(isInner);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ZbxOrderRawData [symbol=" + symbol + ", amount=" + amount + ", orderNo=" + orderNo + ", txId=" + txId
				+ ", status=" + status + ", method=" + method + ", accessKey=" + accessKey + ", sign=" + sign
				+ ", nonce=" + nonce + ", address=" + getAddress() + ", memo=" + getMemo() + "]";
	}

	/**
	 * Check valid.
	 *
	 * @param secretKey the secret key
	 * @return the string
	 */
	public String checkValid(String secretKey) {
		if (StringUtils.isEmpty(secretKey)) {
			return "SecretKey????????????";
		}
		final String sign = getSign();
		if (StringUtils.isEmpty(sign)) {
			return "API????????????";
		}
		final String nonce = getNonce();
		final long timestamp = Long.parseLong(nonce.trim());
		final long currentTimeMillis = System.currentTimeMillis();
		if ((currentTimeMillis - timestamp) > 10 * 60 * 1000) {
			return "API????????????";
		}
		if (isWithdrawal()) {
			if (StringUtils.isEmpty(orderNo)) {
				return "API????????????(???????????????orderNo????????????)";
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
			return "API?????????????????????";
		}
		return null;
	}

}
