package com.mumu.exchange.api;

import java.util.HashMap;
import java.util.Map;

public final class CoinexAPI {
	
	public static final String API_SIGN_KEY_AccessKeyId = "access_id";
	public static final String API_SIGN_KEY_Secret_key = "secret_key";
	public static final String API_SIGN_SignatureMethod = "32-bit MD5";
	public static final String API_SIGN_KEY_Timestamp = "tonce";
	public static final String API_SIGN_KEY_authorization = "authorization";
	
	private static Map<String, String> uriMethodMap = new HashMap<String, String>();
	public static final String rest_trading_root = "https://api.coinex.com";
	public static final String ws_root = "wss://socket.coinex.com";
	
	
	/**
	 * 用户资产API
	 */
	public static final String balance_info = "/v1/balance/info";
	public static final String balance_info_method_get = "GET";
	static { uriMethodMap.put(balance_info, balance_info_method_get); }
	public static enum Balance_info_params {
		
	}
	
	/**
	 * Place Limit Order
	 *	Request description:place limit order.
	 *	Request type: POST
	 *	Signature required: Yes
	 *	Request Header:
	 *	authorization:"xxxx" (32-digit capital letters, see generating methos in <API invocation instruction>)
	 *	Request Url:https://api.coinex.com/v1/order/limit
	 */
	public static final String order_path$orderType = "/v1/order/{path$orderType}";
	public static final String order_path$orderType_method_post = "POST";
	static { uriMethodMap.put(order_path$orderType, order_path$orderType_method_post); }
	public static enum Order_path$orderType_params {
		path$orderType("{path$orderType}"),
//		access_id	String	Yes	access_id
		market("market"),//	String	Yes	See <API invocation description·market>
		type("type"),//	String	Yes	sell: sell order;		buy: buy order;
		amount("amount"),//	String	Yes	order amount, min. 0.001, accurate to 8 decimal places
		price("price"),//	String	Yes	order amount, accurate to 8 decimal places
		source_id("source_id");//	String	no	user defines number and return
//		tonce	Integer	Yes	Tonce is a timestamp with a positive Interger that represents the number of milliseconds from Unix epoch to the current time. Error between tonce and server time can not exceed plus or minus 60s
		
		private final String code;
		
		private Order_path$orderType_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Cancel Order
	 *	Request description: Cancel unexecuted order.
	 *	Request type: DELETE
	 *	Signature required: Yes
	 *	Request Header: authorization:"xxxx" (32-digit capital letters, see generating methos in <API invocation instruction>)
	 *	Request Url:https://api.coinex.com/v1/order/pending
	 */
	public static final String order_pending = "/v1/order/pending";
	public static final String order_pending_method_delete = "DELETE";
	static { uriMethodMap.put(order_pending, order_pending_method_delete); }
	public static enum Order_pending_params {
//		access_id	String	Yes	access_id
		id("id"),//	Integer	Yes	Unexecuted order No
		market("market");//	String	Yes	See <API invocation description·market>
//		tonce	Integer	Yes
		
		private final String code;
		
		private Order_pending_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	
	/**
	 * Acquire Order Status
	 *	Request description: Acquire order status.
		Request type: GET
		Signature required: Yes
		Request Header: 
		authorization:"xxxx" (32-digit capital letters, see generating methos in <API invocation instruction>)
		Request Url:https://api.coinex.com/v1/order/status
	 */
	public static final String order_status = "/v1/order/status";
	public static final String order_status_method_get = "GET";
	static { uriMethodMap.put(order_status, order_status_method_get); }
	public static enum Order_status_params {
//		access_id	String	Yes	access_id
		id("id"),//	Integer	Yes	Unexecuted order No
		market("market");//	String	Yes	See <API invocation description·market>
//		tonce	Integer	Yes
		
		private final String code;
		
		private Order_status_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Acquire Unexecuted Order List
		Request description: Acquire Unexecuted Order List.
		Request type: GET
		Signature required: Yes
		Request Header: 
		authorization:"xxxx" (32-digit capital letters, see generating methos in <API invocation instruction>)
		Request Url:https://api.coinex.com/v1/order/pending
	 */
	public static final String order_pending_list = "/v1/order/pending";
	public static final String order_pending_list_alias = "order_pending_list";
	public static final String order_pending_list_method_get = "GET";
	static { uriMethodMap.put(order_pending_list_alias, order_pending_list_method_get); }
	public static enum Order_pending_list_params {
//		access_id	String	Yes	access_id
		market("market"),//	String	Yes	See <API invocation description·market>
		page("page"),//	Interger	Yes	Page, start from 1
		limit("limit");//	Interger	Yes	Amount per page(1-100)
//		tonce	Integer	Yes
		
		private final String code;
		
		private Order_pending_list_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Acquire Executed Order List
		Request description: Acquire executed order list, including datas in last 2 days.
		Request type: GET
		Signature required: Yes
		Request Header: 
		authorization:"xxxx" (32-digit capital letters, see generating methos in <API invocation instruction>)
		Request Url:https://api.coinex.com/v1/order/finished
	 */
	public static final String order_finished = "/v1/order/finished";
	public static final String order_finished_method_get = "GET";
	static { uriMethodMap.put(order_finished, order_finished_method_get); }
	public static enum Order_finished_params {
//		access_id	String	Yes	access_id
		market("market"),//	String	Yes	See <API invocation description·market>
		page("page"),//	Interger	Yes	Page, start from 1
		limit("limit");//	Interger	Yes	Amount per page(1-100)
//		tonce	Integer	Yes
		
		private final String code;
		
		private Order_finished_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Request description: Acquire real-time market data
		Request type: GET
		Signature required: No
		Request Url: https://api.coinex.com/v1/market/ticker?market=BTCBCH
	 */
	public static final String market_ticker = "/v1/market/ticker";
	public static final String market_ticker_method_get = "GET";
	static { uriMethodMap.put(market_ticker, market_ticker_method_get); }
	public static enum Market_ticker_params {
		market("market");//	String	Yes	See<API invocation description·market>
		
		private final String code;
		
		private Market_ticker_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	
	/**
	 * Acquire Latest Transaction Data
		Request description: Acquire latest transaction data，return up to 1000
		Request type: GET
		Signature required: No
		Request Url:https://api.coinex.com/v1/market/deals
	 *
	 */
	public static final String market_deals = "/v1/market/deals";
	public static final String market_deals_method_get = "GET";
	static { uriMethodMap.put(market_deals, market_deals_method_get); }
	public static enum Market_deals_params {
		market("market"),//	String	Yes	See<API invocation description·market>
		last_id("last_id");//	Interger	No	Transaction history id, send 0 to draw from the latest record.
		
		private final String code;
		
		private Market_deals_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	public static String getMethodByApiUri(String uri) {
		return uriMethodMap.get(uri);
	}
}
