package com.mumu.exchange;

import com.mumu.common.Constants;
import com.mumu.exchange.market.BinanceMarketService;
import com.mumu.exchange.market.CoinexMarketService;
import com.mumu.exchange.market.HuobiMarketService;
import com.mumu.exchange.market.IMarketService;
import com.mumu.exchange.market.OkexMarketService;

public class MarketServiceFactory {

	public static IMarketService getInstance(Constants.EXCHANGE_NAME exchange) {
		IMarketService iIMarketService = null;
		if (Constants.EXCHANGE_NAME.EXCHANGE_HOUBI.equals(exchange)) {
			iIMarketService = new HuobiMarketService();
		} else if (Constants.EXCHANGE_NAME.EXCHANGE_COINEX.equals(exchange)) {
			iIMarketService = new CoinexMarketService();
		} else if (Constants.EXCHANGE_NAME.EXCHANGE_OKEX.equals(exchange)) {
			iIMarketService = new OkexMarketService();
		} else if (Constants.EXCHANGE_NAME.EXCHANGE_BINANCE.equals(exchange)) {
			iIMarketService = new BinanceMarketService();
		}
		
		return iIMarketService;
	}
	
}
