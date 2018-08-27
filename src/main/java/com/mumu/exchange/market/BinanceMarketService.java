package com.mumu.exchange.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.mumu.beans.DealtInfo;
import com.mumu.beans.Market;
import com.mumu.exchange.api.BinanceAPI;
import com.mumu.exchange.coins.BinanceProfiles;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.BinanceParamsSigner;
import com.mumu.exchange.signature.CoinexParamsSigner;
import com.mumu.exchange.signature.ISignature;

public class BinanceMarketService extends MarketService implements IMarketService {

	/**
	 * If the symbol is not sent, tickers for all symbols will be returned in an array.
	 * 
		{"symbol":"BNBBTC",
		"priceChange":"0.00006740",
		"priceChangePercent":"4.465",
		"weightedAvgPrice":"0.00153351",
		"prevClosePrice":"0.00151040",
		"lastPrice":"0.00157690",
		"lastQty":"0.08000000",
		"bidPrice":"0.00157670",
		"bidQty":"1.11000000",
		"askPrice":"0.00157690",
		"askQty":"0.92000000",
		"openPrice":"0.00150950",
		"highPrice":"0.00158590",
		"lowPrice":"0.00148020",
		"volume":"900996.21000000",
		"quoteVolume":"1381.68836299",
		"openTime":1534388378877,
		"closeTime":1534474778877,
		"firstId":26740356,
		"lastId":26800274,
		"count":59919
		}
	 */
	@Override
	public List<Market> quotationTicker(String accessKey, String secretkey, String... symbols) {
		String param = null;
		if (ArrayUtils.isNotEmpty(symbols)) {
			param = Arrays.toString(symbols);
		}
		 
		ISignature signature = new BinanceParamsSigner();
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_ticker_24hr);
		if (StringUtils.isNotBlank(param)) {
			signature.putParam(BinanceAPI.Api_ticker_24hr_params.symbol.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		}
		String uri = signature.getUri(BinanceAPI.api_ticker_24hr_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_ticker_24hr_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		
    		String result = "{\"list\":" +content.asString() +"}";
        	logger.warn("ticker=" + result);
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(result); 
        	List<Market> listMarket = null;
        	Market market = null;
        	if (null != symbols) {
        		listMarket = new ArrayList<Market>(1);
        		jsonMap = (Map<String, Object>) jsonMap.get("list");
        		
        		market = new Market();
				market.setAmount(new Double(jsonMap.get("volume").toString()));
				market.setAsk(new Double(jsonMap.get("askPrice").toString()));
				market.setAskVolume(new Double(jsonMap.get("askQty").toString()));
				market.setBid(new Double(jsonMap.get("bidPrice").toString()));
				market.setBidVolume(new Double(jsonMap.get("bidQty").toString()));
				market.setHighest(new Double(jsonMap.get("highPrice").toString()));
				market.setLast(new Double(jsonMap.get("lastPrice").toString()));
				market.setLowest(new Double(jsonMap.get("lowPrice").toString()));
				market.setOpen(new Double(jsonMap.get("openPrice").toString()));
				market.setSymbol(jsonMap.get("symbol").toString());
				market.setTs(Long.valueOf(jsonMap.get("closeTime").toString()));
				market.setVolume(new Double(jsonMap.get("count").toString()));
				listMarket.add(market);
				
				return listMarket;
        	} else {
        		List<Map<String, Object>> marketMap = (List<Map<String, Object>>) jsonMap.get("list");
            	listMarket = new ArrayList<Market>(marketMap.size());
        		
        		
        		for (Iterator iterator = marketMap.iterator(); iterator.hasNext();) {
    				Map<String, Object> map = (Map<String, Object>) iterator.next();
    				market = new Market();
    				market.setAmount(new Double(map.get("volume").toString()));
    				market.setAsk(new Double(map.get("askPrice").toString()));
    				market.setAskVolume(new Double(map.get("askQty").toString()));
    				market.setBid(new Double(map.get("bidPrice").toString()));
    				market.setBidVolume(new Double(map.get("bidQty").toString()));
    				market.setHighest(new Double(map.get("highPrice").toString()));
    				market.setLast(new Double(map.get("lastPrice").toString()));
    				market.setLowest(new Double(map.get("lowPrice").toString()));
    				market.setOpen(new Double(map.get("openPrice").toString()));
    				market.setSymbol(map.get("symbol").toString());
    				market.setTs(Long.valueOf(map.get("closeTime").toString()));
    				market.setVolume(new Double(map.get("quoteVolume").toString()));
    				listMarket.add(market);
    			}
        		
        		return listMarket;
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_ticker_24hr +" 请求失败", e);
		}
    	
    	return null;
	}

	/**
	 * [
		  {
		    "id": 28457,
		    "price": "4.00000100",
		    "qty": "12.00000000",
		    "time": 1499865549590,
		    "isBuyerMaker": true,
		    "isBestMatch": true
		  }
		]
		{"id":64812920,"price":"6445.15000000","qty":"0.00155900","time":1534741270535,"isBuyerMaker":true,"isBestMatch":true}
	 */
	@Override
	public DealtInfo getSettlementPrice(String accessKey, String secretkey, Integer size, String... symbols) {
		String param = null;
		if (ArrayUtils.isNotEmpty(symbols)) {
			param = Arrays.toString(symbols);
		}
		 
		ISignature signature = new BinanceParamsSigner();
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_trades);
		signature.putParam(BinanceAPI.Api_trades_params.symbol.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		if (null != size) {
			signature.putParam(BinanceAPI.Api_trades_params.limit.getCode(), size.toString());
		}
		String uri = signature.getUri(BinanceAPI.api_trades_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_trades_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		
    		String result = "{\"list\":" +content.asString() +"}";
        	logger.warn("ticker=" + result);
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(result); 
        	List<Map<String, Object>> tickerList = (List<Map<String, Object>>)jsonMap.get("list");
        	DealtInfo dealtInfo = null;
        	Map<String, Object> item = null;
        	
        	long time = getSplittingTime();//确定应用服务器与交易所服务器时间一致
	//        	for (Entry<String, Object> entry : tickerMap.entrySet()) {
	//    		item = (Map<String, Object>) ((Map<String, Object>)entry.getValue()).get("data");
	//    		if (Long.valueOf(item.get("ts").toString()).longValue() <= time) {
	//    			System.out.println("ts=" +Long.valueOf(item.get("ts").toString()).longValue());
	//    			market = new Market();
	//    			break;
	//    		}
	//		}
	    	
	    	for (Map<String, Object> map : tickerList) {
	    		item = map;//((List<Map<String, Object>>) map.get("data")).get(0);
	    		if (Long.valueOf(item.get("time").toString()).longValue() <= time) {
	    			System.out.println("ts=" +Long.valueOf(item.get("time").toString()).longValue());
	    			dealtInfo = new DealtInfo();
	    			dealtInfo.setDirection((Boolean.valueOf(item.get("isBuyerMaker").toString()) ? com.mumu.common.Constants.TRADING_DIRECTION.BUY : com.mumu.common.Constants.TRADING_DIRECTION.SELL));
	    			dealtInfo.setId(item.get("id").toString());
	    			dealtInfo.setPrice(new Double(item.get("price").toString()));
	    			dealtInfo.setTs(Long.valueOf(item.get("time").toString()));
	    			dealtInfo.setVolume(new Double(item.get("qty").toString()));
	    			break;
	    		}
			}                                              
    	
        	return dealtInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_trades +" 请求失败", e);
		}
    	
    	return null;
	}

	
	
}
