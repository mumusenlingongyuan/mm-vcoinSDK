package com.mumu.beans;

import com.mumu.common.BaseForm;
import com.mumu.common.Constants.EXCHANGE_NAME;
import com.mumu.common.Constants.ORDER_STATUS;
import com.mumu.common.Constants.ORDER_TYPE;
import com.mumu.common.Constants.TRADING_DIRECTION;

public class OrderInfo extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1822555787222340534L;
	private String id;
	private String symbol;
	private String accountId;
	/**
	 * 委托量
	 */
	private String originalVolume;
	/**
	 * 成交量
	 */
	private String volume;
	private String price;
	/**
	 * 创建时间
	 */
	private String ts;
	private TRADING_DIRECTION direction;
	private ORDER_TYPE type;
	private ORDER_STATUS status;
	private EXCHANGE_NAME exchange;
	/**
	 * 手续费
	 */
	private String fee;
	/**
	 * 手续费币种
	 */
	private String feeSymbol;
	private String requestId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getOriginalVolume() {
		return originalVolume;
	}
	public void setOriginalVolume(String originalVolume) {
		this.originalVolume = originalVolume;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public TRADING_DIRECTION getDirection() {
		return direction;
	}
	public void setDirection(TRADING_DIRECTION direction) {
		this.direction = direction;
	}
	public ORDER_TYPE getType() {
		return type;
	}
	public void setType(ORDER_TYPE type) {
		this.type = type;
	}
	public ORDER_STATUS getStatus() {
		return status;
	}
	public void setStatus(ORDER_STATUS status) {
		this.status = status;
	}
	public EXCHANGE_NAME getExchange() {
		return exchange;
	}
	public void setExchange(EXCHANGE_NAME exchange) {
		this.exchange = exchange;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getFeeSymbol() {
		return feeSymbol;
	}
	public void setFeeSymbol(String feeSymbol) {
		this.feeSymbol = feeSymbol;
	}
	
	
	
}
