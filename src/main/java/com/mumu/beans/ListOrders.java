package com.mumu.beans;

import java.io.Serializable;
import java.util.List;

import com.mumu.common.Constants.RESPONSE_STATUS;

public class ListOrders extends Common {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1049013488197872434L;
	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public static class Response implements Serializable {
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -4005463384102145276L;
		private RESPONSE_STATUS status; 
		private String errorCode;
		private String errorMsg;
		private boolean hasNext = false;
		
		private List<OrderInfo> orderInfoList;

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

		public List<OrderInfo> getListOrderInfo() {
			return orderInfoList;
		}

		public void setListOrderInfo(List<OrderInfo> orderInfoList) {
			this.orderInfoList = orderInfoList;
		}

		public boolean isHasNext() {
			return hasNext;
		}

		public void setHasNext(boolean hasNext) {
			this.hasNext = hasNext;
		}

		
		
	}
}
