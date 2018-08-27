package com.mumu.beans;

import java.io.Serializable;

import com.mumu.common.Constants.RESPONSE_STATUS;

public class Cancel extends Common {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2401774342779032830L;
	private String symbol;
	private String tid;
	
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}


	public static class Response implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2870163071445336074L;
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
