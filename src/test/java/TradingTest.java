import com.mumu.beans.ListOrders;
import com.mumu.exchange.TradingServiceFactory;

public class TradingTest {
	
	public static void main(String[] args) {
		//-----------------------------------------BINANCEBINANCEBINANCEBINANCEBINANCEBINANCE-----------------------------------------------------------------------//
		String accessKey4 = "";
		String secretkey4 = "";
//		
		accessKey4 = "";
		secretkey4 = "";
//		AccountInfo accountInfo = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE).accountInfo(accessKey4, secretkey4, "userid");
		
//		Order order = new Order();
//		order.setSymbol("ETHUSDT");
//		order.setPrice("380");
//		order.setVolume("0.04000");
//		order.setAccessKey(accessKey4);
//		order.setDirection(TRADING_DIRECTION.SELL);
//		order.setRequestId(CommonUtils.genUniqueInt()+"");
//		order.setSecretkey(secretkey4);
//		order.setType(ORDER_TYPE.LIMIT);
//		Order.Response response = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//		.orders(order);
		
//		Cancel cancel = new Cancel();
//		cancel.setAccessKey(accessKey4);
//		cancel.setSecretkey(secretkey4);
//		cancel.setSymbol("ETHUSDT");
//		cancel.setTid("106751148");
//		Cancel.Response response = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//				.cancel(cancel);
		
		
//		GetOrder getOrder = new GetOrder();
//		getOrder.setSymbol("ETHUSDT");
//		getOrder.setAccessKey(accessKey4);
//		getOrder.setSecretkey(secretkey4);
//		getOrder.setTid("107276381");
//		TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//		.orderInfo(getOrder);
		
//		ListOrders listOrders = new ListOrders();
//		listOrders.setAccessKey(accessKey4);
//		listOrders.setSecretkey(secretkey4);
//		listOrders.setSymbol("ETHUSDT");
//		TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//		 .unmatchedList(listOrders);
		
//		accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//				.putParam(BinanceAPI.Api_allOrders_params.symbol.getCode(), "ETHUSDT")
//				.historyList(accessKey, secretkey);
//		System.out.println(accountInfoJson);
		
//		accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)
//				.putParam(BinanceAPI.Wapi_depositHistory_params.asset.getCode(), "ETH")
//				.depositList(accessKey, secretkey);
//		System.out.println(accountInfoJson);
		
//		accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE)//xxxx
//				.putParam(BinanceAPI.Wapi_withdrawHistory_params.asset.getCode(), "ETH")
//				.putParam(BinanceAPI.Wapi_withdrawHistory_params.status.getCode(), Constants.BINANCE_WITHDRAW_STATUS.Completed.getCode())
//				.withdrawList(accessKey, secretkey);
//		System.out.println(accountInfoJson);
		
		//-----------------------------------------COINEXCOINEXCOINEXCOINEX-----------------------------------------------------------------------//
		
				String accessKey2 = "";
				String secretkey2 = "";
//				AccountInfo accountInfo =  TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX).accountInfo(accessKey2, secretkey2,"2432432423");
//				Order order = new Order();
//				order.setSymbol("ETHBCH");
//				order.setPrice("0.5466036");
//				order.setVolume("0.0300");
//				order.setAccessKey(accessKey2);
//				order.setDirection(TRADING_DIRECTION.BUY);
//				order.setRequestId(CommonUtils.genUniqueInt()+"");
//				order.setSecretkey(secretkey2);
//				order.setType(ORDER_TYPE.LIMIT);
//				System.out.println(TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX)
//						.orders(order).getTid());
				
//				Cancel cancel = new Cancel();
//				cancel.setAccessKey(accessKey2);
//				cancel.setSecretkey(secretkey2);
//				cancel.setSymbol("CETBCH");
//				cancel.setTid("1371225525");
//				Cancel.Response response = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX)
//						.cancel(cancel);
				
//				GetOrder getOrder = new GetOrder();
//				getOrder.setAccessKey(accessKey2);
//				getOrder.setSecretkey(secretkey2);
//				getOrder.setTid("1392841140");
//				getOrder.setSymbol("ETHBCH");
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX).orderInfo(getOrder);
						
				//"id": 1293194399, 
//				ListOrders listOrders = new ListOrders();
//				listOrders.setAccessKey(accessKey2);
//				listOrders.setSecretkey(secretkey2);
//				listOrders.setSymbol("ETHBCH");
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX)
//				 .unmatchedList(listOrders);
				
//				accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX)
//						.putParam(CoinexAPI.Order_finished_params.market.getCode(), "ETHBCH")
//						.putParam(CoinexAPI.Order_finished_params.page.getCode(), "1")
//						.putParam(CoinexAPI.Order_finished_params.limit.getCode(), "1")
//						.historyList(accessKey, secretkey);
//				System.out.println(accountInfoJson);
				//--------------------------------------------------火火火火火火火火火币--------------------------------------------------------------//
				String accessKey = "";
				String secretkey = "";
//				AccountInfo accountInfo = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI).accountInfo(accessKey, secretkey, "46915295");
//				
//				Order order = new Order();
//				order.setAccountId("4276741");
//				order.setSymbol("bchbtc");
//				order.setPrice("0.083489");
//				order.setAccessKey(accessKey);
//				order.setDirection(TRADING_DIRECTION.BUY);
//				order.setRequestId(CommonUtils.genUniqueInt()+"");
//				order.setSecretkey(secretkey);
//				order.setType(ORDER_TYPE.LIMIT);
//				order.setVolume("0.0005");
//				Order.Response response = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI).orders(order);
				
//				Cancel cancel = new Cancel();
//				cancel.setAccessKey(accessKey);
//				cancel.setSecretkey(secretkey);
//				cancel.setTid("1371225525");
//				Cancel.Response response = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI)
//						.cancel(cancel);
				
//				GetOrder getOrder = new GetOrder();
//				getOrder.setTid("10721786675");
//				getOrder.setAccessKey(accessKey);
//				getOrder.setSecretkey(secretkey);
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI).orderInfo(getOrder);
				
//				ListOrders listOrders = new ListOrders();
//				listOrders.setAccessKey(accessKey);
//				listOrders.setSecretkey(secretkey);
//				listOrders.setSymbol("bchbtc");
//				listOrders.setAccountId("4276741");
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI)
//				 .unmatchedList(listOrders);
				
//				accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI)
//						.putParam(HoubiAPI.Order_orders_params.symbol.getCode(), "bchbtc")
//						.historyList(accessKey, secretkey);
//				System.out.println(accountInfoJson);
				
//				accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI)
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.currency.getCode(), "btc")
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.size.getCode(), "10")
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.from.getCode(), "0")
//						.depositList(accessKey, secretkey);
//				System.out.println(accountInfoJson);
//				
//				accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI)
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.currency.getCode(), "btc")
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.size.getCode(), "10")
//						.putParam(HoubiAPI.Query_deposit_withdraw_params.from.getCode(), "0")
//						.withdrawList(accessKey, secretkey);
//				System.out.println(accountInfoJson);
				//-----------------------------------------OKEXOKEXOKEXOKEXOKEXOKEXOKEXOKEXOKEXOKEXOKEXOKEX-----------------------------------------------------------------------//
				
				String accessKey3 = "";
				String secretkey3 = "";
//				AccountInfo accountInfo = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX).accountInfo(accessKey3, secretkey3, "tewtes");
				
//				Order order = new Order();
//				order.setSymbol("ltc_eth");
//				order.setPrice("0.18162686");
//				order.setVolume("0.068394");
//				order.setAccessKey(accessKey3);
//				order.setDirection(TRADING_DIRECTION.BUY);
//				order.setRequestId(CommonUtils.genUniqueInt()+"");
//				order.setSecretkey(secretkey3);
//				order.setType(ORDER_TYPE.LIMIT);
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX)
//						.orders(order);
				
//				Cancel cancel = new Cancel();
//				cancel.setAccessKey(accessKey3);
//				cancel.setSecretkey(secretkey3);
//				cancel.setSymbol("ltc_eth");
//				cancel.setTid("53649387");
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX).cancel(cancel);

				//				54614577
//				GetOrder getOrder = new GetOrder();
//				getOrder.setAccessKey(accessKey3);
//				getOrder.setSecretkey(secretkey3);
//				getOrder.setSymbol("ltc_eth");
//				getOrder.setTid("54614577");
//				TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX).orderInfo(getOrder);
				
				
				ListOrders listOrders = new ListOrders();
				listOrders.setAccessKey(accessKey3);
				listOrders.setSecretkey(secretkey3);
				listOrders.setSymbol("ltc_eth");
				TradingServiceFactory.getInstance(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX)
				 .unmatchedList(listOrders);
				
//				accountInfoJson = TradingServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX)
//						.putParam(OkexAPI.Api_order_history_params.symbol.getCode(), "bch_eth")
//						.putParam(OkexAPI.Api_order_history_params.status.getCode(), Constants.OKEX_COMMON_STATUS.done.getCode())
//						.putParam(OkexAPI.Api_order_history_params.current_page.getCode(), "1")
//						.putParam(OkexAPI.Api_order_history_params.page_length.getCode(), "200")
//						.historyList(accessKey, secretkey);
//				System.out.println(accountInfoJson);
	}
	
	
}
