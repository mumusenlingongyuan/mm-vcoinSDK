package com.mumu.exchange;

import com.mumu.beans.AccountInfo;
import com.mumu.beans.Cancel;
import com.mumu.beans.GetOrder;
import com.mumu.beans.ListOrders;
import com.mumu.beans.Order;
import com.mumu.exchange.common.Constants;
import com.mumu.exchange.signature.BinanceParamsSigner;
import com.mumu.exchange.signature.CoinexParamsSigner;
import com.mumu.exchange.signature.HuobiParamsSigner;
import com.mumu.exchange.signature.ISignature;
import com.mumu.exchange.signature.OkexParamsSigner;
import com.mumu.exchange.trading.BinanceTradingService;
import com.mumu.exchange.trading.CoinexTradingService;
import com.mumu.exchange.trading.HuobiTradingService;
import com.mumu.exchange.trading.ITradingService;
import com.mumu.exchange.trading.OkexTradingService;

public class TradingServiceFactory {

	public static TradingServiceSPI getInstance(com.mumu.common.Constants.EXCHANGE_NAME exchange) {
		
		return new TradingServiceFactory.TradingServiceSPI(exchange);
	}
	
	public final static class TradingServiceSPI {
		
		private ITradingService tradingService;
		private ISignature signature;
	
		public TradingServiceSPI(com.mumu.common.Constants.EXCHANGE_NAME exchange) {
			if (com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI.equals(exchange)) {
				tradingService = new HuobiTradingService();
				signature = new HuobiParamsSigner();
			} else if (com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX.equals(exchange)) {
				tradingService = new CoinexTradingService();
				signature = new CoinexParamsSigner();
			} else if (com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX.equals(exchange)) {
				tradingService = new OkexTradingService();
				signature = new OkexParamsSigner();
			} else if (com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE.equals(exchange)) {
				tradingService = new BinanceTradingService();
				signature = new BinanceParamsSigner();
			}
		}
		
		public ISignature getSignature() {
			return signature;
		}
		
		/**
		 * 
		 * @param clazz
		 * @return
		 */
		public <T> T getLocalTradingService(Class<T> clazz) {
			
			return clazz.cast(tradingService);
		}

//		/**
//		 * 
//		 * @param key
//		 * @param value
//		 * @return
//		 */
//		public TradingServiceSPI putParam(String key, String value) {
//			this.signature.putParam(key, value);
//			return this;
//		}
//		
//		/**
//		 * 
//		 * @param params
//		 * @return
//		 */
//		public TradingServiceSPI addAllParams(Map<String, String> params) {
//			this.signature.addAllParams(params);
//			return this;
//		}
//		
//		/**
//		 * 清空参数后，在保证线程安全的情况下，可以使用同一SPI实例请求其它接口
//		 * @return
//		 */
//		public TradingServiceSPI clearParams() {
//			this.signature.clearParams();
//			return this;
//		}
		
		/**
		 * 
		 * @param accessKey
		 * @param secretkey
		 * @param accountId 可以传本地ID,用来标识返回数据所有者
		 * @return
		 */
		public AccountInfo accountInfo(String accessKey, String secretkey, String accountId) {
			AccountInfo accountInfo = this.tradingService.accountInfo(accessKey, secretkey, signature);
			accountInfo.setAccountId(accountId);
			return accountInfo;
		}
		
//		public String orders(String accessKey, String secretkey) {
//			return this.tradingService.orders(accessKey, secretkey, signature);
//		}
		
		/**
		 * h:{"status":"error","err-code":"account-frozen-balance-insufficient-error","err-msg":"余额不足，剩余：`0.0005`","data":null}
		 * h:{"status":"ok","data":"10721786675"}
		 * c:{  "code": 23,   "data": {},   "message": "IP:66.42.71.205 is not allowed"} { "code": 107,  "data": {},   "message": "Balance insufficient."}
		   c: {
			  "code": 0, 
			  "data": {
			    "amount": "232.56235465", 
			    "asset_fee": "0", 
			    "avg_price": "0.00", 
			    "create_time": 1534845385, 
			    "deal_amount": "0", 
			    "deal_fee": "0", 
			    "deal_money": "0", 
			    "fee_asset": null, 
			    "fee_discount": "0", 
			    "id": 1371225525, 
			    "left": "232.56235465", 
			    "maker_fee_rate": "0.001", 
			    "market": "CETBCH", 
			    "order_type": "limit", 
			    "price": "0.00001880", 
			    "source_id": "", 
			    "status": "not_deal", 
			    "taker_fee_rate": "0.001", 
			    "type": "buy"
			  }, 
			  "message": "Ok"
			}
		 * o:{"error_code":1002}
		 * o:{"result":true,"order_id":53649387}
		 * b:{"symbol":"ETHUSDT","orderId":106437085,"clientOrderId":"PtIGmuYsIa3uIz7fOh7i17","transactTime":1534847895782,"price":"380.00000000","origQty":"0.04000000","executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC","type":"LIMIT","side":"SELL","fills":[]}
		 * b:请求异常
		 * @return
		 */
		public Order.Response orders(Order order) {
			this.signature.addAllParams(tradingService.getOrdersParamsMap(order));
			return this.tradingService.orders(order.getAccessKey(), order.getSecretkey(), signature);
		}
		
		/**
		 * 
		 * @param accessKey
		 * @param secretkey
		 * @return
		 */
		public Cancel.Response cancel(Cancel cancel) {
			this.signature.addAllParams(this.tradingService.getCancelParamsMap(cancel));
			return this.tradingService.cancel(cancel.getAccessKey(), cancel.getSecretkey(), signature);
		}
		
		public GetOrder.Response orderInfo(GetOrder getOrder) {
			this.signature.addAllParams(this.tradingService.getGetOrderParamsMap(getOrder));
			return this.tradingService.orderInfo(getOrder.getAccessKey(), getOrder.getSecretkey(), signature);
		}
		
		public ListOrders.Response unmatchedList(ListOrders listOrders) {
			this.signature.addAllParams(this.tradingService.getListUnMatchedParamsMap(listOrders));
			return this.tradingService.unmatchedList(listOrders.getAccessKey(), listOrders.getSecretkey(), signature);
		}
		
//		public String dealtList(String accessKey, String secretkey, ISignature signature);
		
		public String historyList(String accessKey, String secretkey) {
			return this.tradingService.historyList(accessKey, secretkey, signature);
		}
		
		public String depositList(String accessKey, String secretkey) {
			return this.tradingService.depositList(accessKey, secretkey, signature);
		}
		
		public String withdrawList(String accessKey, String secretkey) {
			return this.tradingService.withdrawList(accessKey, secretkey, signature);
		}
	}
}
