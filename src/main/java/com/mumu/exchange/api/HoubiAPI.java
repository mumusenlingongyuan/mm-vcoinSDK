package com.mumu.exchange.api;

import java.util.HashMap;
import java.util.Map;

public final class HoubiAPI {
	
//	POST请求头信息中必须声明 Content-Type:application/json;
//	GET请求头信息中必须声明 Content-Type:application/x-www-form-urlencoded。
//	(汉语用户建议设置 Accept-Language:zh-cn)
//	
//	请务必在header中设置user agent为 'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36'
	
	public static final String API_SIGN_KEY_AccessKeyId = "AccessKeyId";//=e2xxxxxx-99xxxxxx-84xxxxxx-7xxxx
	public static final String API_SIGN_KEY_SignatureMethod = "SignatureMethod";//=HmacSHA256
	public static final String API_SIGN_SignatureMethod = "HmacSHA256";//=HmacSHA256
	public static final String API_SIGN_KEY_SignatureVersion = "SignatureVersion";//=2
	public static final String API_SIGN_SignatureVersion = "2";//=2
	public static final String API_SIGN_KEY_Timestamp = "Timestamp";//=2017-05-11T15%3A19%3A30
	
	private static Map<String, String> uriMethodMap = new HashMap<String, String>();
	public static final String rest_market_root = "https://api.huobi.pro";
	public static final String rest_trading_root = "https://api.huobi.pro";//https://api.huobi.pro/v1/common/symbols
	public static final String ws_root = "wss://api.huobi.pro/ws";
	
	
	/**
	 * 用户资产API
	 */
	public static final String account_accounts = "/v1/account/accounts";
	public static final String account_accounts_method_get = "GET";
	static { uriMethodMap.put(account_accounts, account_accounts_method_get); }
	public static enum Account_accounts_params {
		
	}
	
	/**
	 * 下单
	 */
	public static final String order_orders_place = "/v1/order/orders/place";
	public static final String order_orders_place_method_post = "POST";
	static { uriMethodMap.put(order_orders_place, order_orders_place_method_post); }
	public static enum Order_orders_place_params {
//		true	string	账户 ID，使用accounts方法获得。币币交易使用‘spot’账户的accountid；借贷资产交易，请使用‘margin’账户的accountid		
		account_id("account-id"),
//		true	string	限价单表示下单数量，市价买单时表示买多少钱，市价卖单时表示卖多少币		
		amount("amount"),	
//		false	string	下单价格，市价单不传该参数		
		price("price"),	
//		false	string	订单来源	api，如果使用借贷资产交易，请填写‘margin-api’	
		source("source"),	
//		true	string	交易对		btcusdt, bchbtc, rcneth ...
		symbol("symbol"),	
//		true	string	订单类型		buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, buy-ioc：IOC买单, sell-ioc：IOC卖单, buy-limit-maker, sell-limit-maker(详细说明见下)
		type("type");
		
		private final String code;
		
		private Order_orders_place_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	
	/**
	 * 取消
	 * /v1/order/orders/{order-id}/submitcancel
	 */
	public static final String order_orders_path$orderId_submitcancel = "/v1/order/orders/{path$orderId}/submitcancel";
	public static final String order_orders_path$orderId_submitcancel_method_post = "POST";
	static { uriMethodMap.put(order_orders_path$orderId_submitcancel, order_orders_path$orderId_submitcancel_method_post); }
	public static enum Order_orders_path$orderId_submitcancel_params {
		path$orderId("{path$orderId}");
		
		private final String code;
		
		private Order_orders_path$orderId_submitcancel_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * 订单信息
	 * GET /v1/order/orders/{order-id} 
	 */
	public static final String order_orders_path$orderId = "/v1/order/orders/{path$orderId}";
	public static final String order_orders_path$orderId_method_get = "GET";
	static { uriMethodMap.put(order_orders_path$orderId, order_orders_path$orderId_method_get); }
	public static enum Order_orders_path$orderId_params {
		path$orderId("{path$orderId}");
		
		private final String code;
		
		private Order_orders_path$orderId_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * GET /v1/order/openOrders 获取所有当前帐号下未成交订单
	 * “account-id” 和 “symbol” 需同时指定或者二者都不指定。如果二者都不指定，返回最多500条尚未成交订单，按订单号降序排列。
	 */
	public static final String order_openOrders = "/v1/order/openOrders";
	public static final String order_openOrders_method_get = "GET";
	static { uriMethodMap.put(order_openOrders, order_openOrders_method_get); }
	public static enum Order_openOrders_params {
//		true	string	账号ID
		account_id("account-id"),
//		true	string	交易对		单个交易对字符串，缺省将返回所有符合条件尚未成交订单
		symbol("symbol"),
//		false	string	主动交易方向		“buy”或者“sell”，缺省将返回所有符合条件尚未成交订单
		side("side"),
//		false	int	所需返回记录数	10	[0,500]
		size("size");
		
		private final String code;
		
		private Order_openOrders_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * GET /v1/order/orders 查询当前委托、历史委托
	 */
	public static final String order_orders = "/v1/order/orders";
	public static final String order_orders_method_get = "GET";
	static { uriMethodMap.put(order_orders, order_orders_method_get); }
	public static enum Order_orders_params {
		//true	string	交易对		btcusdt, bchbtc, rcneth ...
		symbol("symbol"),
		//false	string	查询的订单类型组合，使用','分割		buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, buy-ioc：IOC买单, sell-ioc：IOC卖单
		types("types"),
		//false	string	查询开始日期, 日期格式yyyy-mm-dd
		start_date("start-date"),
		//false	string	查询结束日期, 日期格式yyyy-mm-dd
		end_date("end-date"),
		//true	string	查询的订单状态组合，使用','分割		submitted 已提交, partial-filled 部分成交, partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销
		states("states"),
		//false	string	查询起始 ID
		from("from"),
		//false	string	查询方向		prev 向前，next 向后
		direct("direct"),
		//false	string	查询记录大小
		size("size");			
		
		private final String code;
		
		private Order_orders_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * GET /v1/query/deposit-withdraw 查询虚拟币充提记录
	 */
	public static final String query_deposit_withdraw = "/v1/query/deposit-withdraw";
	public static final String query_deposit_withdraw_method_get = "GET";
	static { uriMethodMap.put(query_deposit_withdraw, query_deposit_withdraw_method_get); }
	public static enum Query_deposit_withdraw_params {
		//true	string	币种	
		currency("currency"),		
//		//true	string	'deposit' or 'withdraw'		
		type("type"),
		//false	string	查询起始 ID
		from("from"),
		//false	string	查询记录大小
		size("size");
		
		private final String code;
		
		private Query_deposit_withdraw_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	
	
	/**
	 *  查询Pro站指定账户的余额
	 *  /v1/account/accounts/4276741/balance
	 */
	public static final String accounts_p$accountId_balance = "/v1/account/accounts/{path$accountId}/balance";
	public static final String accounts_p$accountId_balance_method_get = "GET";
	static { uriMethodMap.put(accounts_p$accountId_balance, accounts_p$accountId_balance_method_get); }
	public static enum Accounts_p$accountId_balance_params {
		path$accountId("{path$accountId}");
		
		private final String code;
		
		private Accounts_p$accountId_balance_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * GET /market/detail/merged 获取聚合行情(Ticker)
	 */
	public static final String market_detail_merged = "/market/detail/merged";
	public static final String market_detail_merged_method_get = "GET";
	static { uriMethodMap.put(market_detail_merged, market_detail_merged_method_get); }
	public static enum Market_detail_merged_params {
		symbol("symbol");//	true	string	交易对		btcusdt, bchbtc, rcneth ...
		
		private final String code;
		
		private Market_detail_merged_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	/**
	 * GET /market/history/trade 批量获取最近的交易记录
	 */
	public static final String market_history_trade = "/market/history/trade";
	public static final String market_history_trade_method_get = "GET";
	static { uriMethodMap.put(market_history_trade, market_history_trade_method_get); }
	public static enum Market_history_trade_params {
		symbol("symbol"),//	true	string	交易对		btcusdt, bchbtc, rcneth ...
		size("size");//	false	integer	获取交易记录的数量	1	[1, 2000]
		
		private final String code;
		
		private Market_history_trade_params(String code) {
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
