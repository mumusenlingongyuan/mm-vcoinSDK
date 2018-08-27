package com.mumu.exchange.api;

import java.util.HashMap;
import java.util.Map;

public final class OkexAPI {
	
	
	public static final String API_SIGN_KEY_AccessKeyId = "api_key";
	public static final String API_SIGN_KEY_Secret_key = "secret_key";
	public static final String API_SIGN_SignatureMethod = "32-bit MD5";
	public static final String API_SIGN_KEY_authorization = "sign";
	
	private static Map<String, String> uriMethodMap = new HashMap<String, String>();
	public static final String rest_trading_root = "https://www.okex.com";
	public static final String ws_root = "wss://real.okex.com:10441/websocket";
	
	
	/**
	 * 用户资产API
	 */
	public static final String api_userinfo = "/api/v1/userinfo.do";
	public static final String api_userinfo_method_post = "POST";
	static { uriMethodMap.put(api_userinfo, api_userinfo_method_post); }
	public static enum Api_userinfo_params {
		
	}
	
	/**
	 * POST /api/v1/trade 下单交易
	 * URL https://www.okex.com/api/v1/trade.do	访问频率 20次/2秒
	 */
	public static final String api_trade = "/api/v1/trade.do";
	public static final String api_trade_method_post = "POST";
	static { uriMethodMap.put(api_trade, api_trade_method_post); }
	public static enum Api_trade_params {
//		api_key	String	是	用户申请的apiKey
		symbol("symbol"),//	String	是	币对如ltc_btc
		type("type"),//	String	是	买卖类型：限价单(buy/sell) 市价单(buy_market/sell_market)
		price("price"),//	Double	否	下单价格 市价卖单不传price
		amount("amount");//	Double	否	交易数量 市价买单不传amount,市价买单需传price作为买入总金额
//		sign	String	是	请求参数的签名
		
		private final String code;
		
		private Api_trade_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * POST /api/v1/cancel_order 撤销订单
	 * URL https://www.okex.com/api/v1/cancel_order.do	访问频率 20次/2秒
	 * result:true撤单请求成功，等待系统执行撤单；false撤单失败(用于单笔订单)
	 *	order_id:订单ID(用于单笔订单)
	 *	success:撤单请求成功的订单ID，等待系统执行撤单(用于多笔订单)
	 *	error:撤单请求失败的订单ID(用户多笔订单)
	 */
	public static final String api_cancel_order = "/api/v1/cancel_order.do";
	public static final String api_cancel_order_method_post = "POST";
	static { uriMethodMap.put(api_cancel_order, api_cancel_order_method_post); }
	public static enum Api_cancel_order_params {
//		api_key	String	是	用户申请的apiKey
		symbol("symbol"),//	String	是	币对如ltc_btc
		order_id("order_id");//	订单ID(多个订单ID中间以","分隔,一次最多允许撤消3个订单)
//		sign	String	是	请求参数的签名
		
		private final String code;
		
		private Api_cancel_order_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * POST /api/v1/order_info   获取用户的订单信息
	 *	URL https://www.okex.com/api/v1/order_info.do	访问频率 20次/2秒(未成交)
	 */
	public static final String api_order_info = "/api/v1/order_info.do";
	public static final String api_order_info_method_post = "POST";
	static { uriMethodMap.put(api_order_info, api_order_info_method_post); }
	public static enum Api_order_info_params {
//		api_key	String	是	用户申请的apiKey
		symbol("symbol"),//	String	是	币对如ltc_btc
		order_id("order_id");//	订单ID -1:未完成订单，否则查询相应订单号的订单
//		sign	String	是	请求参数的签名
		
		private final String code;
		
		private Api_order_info_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * POST /api/v1/orders_info 批量获取用户订单
	 * URL https://www.okex.com/api/v1/orders_info.do	访问频率 20次/2秒
	 */
	public static final String api_orders_info = "/api/v1/orders_info.do";
	public static final String api_orders_info_method_post = "POST";
	static { uriMethodMap.put(api_orders_info, api_orders_info_method_post); }
	public static enum Api_orders_info_params {
//		api_key	String	是	用户申请的apiKey
		type("type"),//	Integer	是	查询类型 0:未完成的订单 1:已经完成的订单
		symbol("symbol"),//	String	是	币对如ltc_btc
		order_id("order_id");//	订单ID(多个订单ID中间以","分隔,一次最多允许查询50个订单)
//		sign	String	是	请求参数的签名
		
		private final String code;
		
		private Api_orders_info_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * POST /api/v1/order_history 获取历史订单信息，只返回最近两天的信息
	 * URL https://www.okex.com/api/v1/order_history.do
	 */
	public static final String api_order_history = "/api/v1/order_history.do";
	public static final String api_order_history_method_post = "POST";
	static { uriMethodMap.put(api_order_history, api_order_history_method_post); }
	public static enum Api_order_history_params {
//		api_key	String	是	用户申请的apiKey
		symbol("symbol"),//	String	是	币对如ltc_btc
		status("status"),//	Integer	是	查询状态 0：未完成的订单 1：已经完成的订单(最近两天的数据)
		current_page("current_page"),//	Integer	是	当前页数
		page_length("page_length");//	Integer	是	每页数据条数，最多不超过200
//		sign	String	是	请求参数的签名
		
		private final String code;
		
		private Api_order_history_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Get /api/v1/ticker   获取OKEx币币行情
		URL https://www.okex.com/api/v1/ticker.do
	 * @param uri
	 * @return
	 */
	public static final String api_ticker = "/api/v1/ticker.do";
	public static final String api_ticker_method_get = "GET";
	static { uriMethodMap.put(api_ticker, api_ticker_method_get); }
	public static enum Api_ticker_params {
		symbol("symbol");//	String	是	币对如ltc_btc
		
		private final String code;
		
		private Api_ticker_params(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * Get /api/v1/trades 获取OKEx币币交易信息(60条)
		URL https://www.okex.com/api/v1/trades.do
	 */
	public static final String api_trades = "/api/v1/trades.do";
	public static final String Api_trades_method_get = "GET";
	static { uriMethodMap.put(api_trades, Api_trades_method_get); }
	public static enum Api_trades_params {
		symbol("symbol"),	//String	是	币对如ltc_btc
		since("since");//	Long	否(默认返回最近成交60条)	tid:交易记录ID(返回数据不包括当前tid值,最多返回60条数据)
		
		private final String code;
		
		private Api_trades_params(String code) {
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
