package com.mumu.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.mumu.common.BaseForm;
import com.mumu.common.Constants.RESPONSE_STATUS;

public class AccountInfo extends BaseForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3374677305748881668L;
	private RESPONSE_STATUS status; 
	private String errorCode;
	private String errorMsg;
	
	/**
	 * 帐户状态是否正常
	 */
	private String accountId;
	private boolean isNormal = true;
	private Map<String, AssetItem> assetMap = null;
	
	
	
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



	public String getAccountId() {
		return accountId;
	}



	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	public boolean isNormal() {
		return isNormal;
	}



	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}



	public Map<String, AssetItem> getAssetMap() {
		return Collections.unmodifiableMap(this.assetMap);
	}



	public void setAssetMap(Map<String, AssetItem> assetMap) {
		if (null == this.assetMap) {
			this.assetMap = assetMap;
		}
	}



	public class AssetItem implements Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2464994811180200234L;
		private String currency;
		private String available;
		private String frozen;
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getAvailable() {
			return available;
		}
		public void setAvailable(String available) {
			this.available = available;
		}
		public String getFrozen() {
			return frozen;
		}
		public void setFrozen(String frozen) {
			this.frozen = frozen;
		}
		
		
	}
}
