package com.mumu.beans;

import java.io.Serializable;

import com.mumu.common.Constants.ORDER_TYPE;
import com.mumu.common.Constants.RESPONSE_STATUS;
import com.mumu.common.Constants.TRADING_DIRECTION;

public class Order extends Common {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4205844875425589379L;
	private String symbol;
	private String volume;
	/**
	 * 市价单没有价格
	 */
	private String price;
	private TRADING_DIRECTION direction;
	private ORDER_TYPE type;
	

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	
	
	public static class Response implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 7657654382832287880L;
		private RESPONSE_STATUS status; 
		private String errorCode;
		private String errorMsg;
		
		private String tid;

		public RESPONSE_STATUS getStatus() {
			return status;
		}

		public void setStatus(RESPONSE_STATUS status) {
			this.status = status;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public String getTid() {
			return tid;
		}

		public void setTid(String tid) {
			this.tid = tid;
		}
		
		
	}
}
