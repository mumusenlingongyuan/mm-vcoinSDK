package com.mumu.exchange.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.mumu.beans.DealtInfo;
import com.mumu.beans.Market;
import com.mumu.exchange.api.CoinexAPI;
import com.mumu.exchange.coins.CoinexProfiles;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.CoinexParamsSigner;
import com.mumu.exchange.signature.ISignature;

public class CoinexMarketService extends MarketService implements IMarketService {

	/**
	 * 为空的时候取所有的
	 * Request Url:https://api.coinex.com/v1/market/ticker/all
	 * 
	 * {"code": 0, "data": {"date": 1534495776723, "ticker": {
	 * "vol": "4922.64386048", "low": "11.8980201", "open": "12.15",
		"high": "12.22", "last": "12.0614398", "buy": "12.04506", "buy_amount": "1.94674137",
		 "sell": "12.07666", "sell_amount": "0.02747"}}, "message": "OK"}
	 */
	@Override
	public List<Market> quotationTicker(String accessKey, String secretkey, String... symbols) {
		String param = Arrays.toString(symbols);
		ISignature signature = new CoinexParamsSigner();
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.market_ticker);
		signature.putParam(CoinexAPI.Market_ticker_params.market.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.market_ticker_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.balance_info_method_get);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
    		String result = content.asString();
        	logger.warn("ticker=" + result);
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(result); 
        	Map<String, String> tickerMap = (Map<String, String>) ((Map<String, Object>) jsonMap.get("data")).get("ticker");
        	List<Market> listMarket = new ArrayList<Market>(1);
        	Market market = new Market();
//        	market.setAmount(null);
        	market.setAsk(new Double(tickerMap.get("sell")));
        	market.setAskVolume(new Double(tickerMap.get("sell_amount")));
        	market.setBid(new Double(tickerMap.get("buy")));
        	market.setBidVolume(new Double(tickerMap.get("buy_amount")));
        	
        	market.setHighest(new Double(tickerMap.get("high")));
        	market.setLowest(new Double(tickerMap.get("low")));
        	market.setLast(new Double(tickerMap.get("last")));
        	market.setOpen(new Double(tickerMap.get("open")));
        	market.setSymbol(symbols[0]);
        	market.setTs(Long.valueOf(((Map<String, Object>)jsonMap.get("data")).get("date").toString()));
        	market.setVolume(new Double(tickerMap.get("vol")));
        	
        	listMarket.add(market);
        	return listMarket;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.market_ticker +" 请求失败", e);
		}
    	
    	return null;
	}

	/**
	 * {
		  "code": 0,
		  "data": [
		    {
		      "amount": "0.0001",       # Transaction amount 
		      "date": 1494214689,       # Transaction time(s) 
		      "date_ms": 1494214689067, # Transaction time(ms)
		      "id": 5,                  # Transaction No
		      "price": "10.00",       # Transaction price
		      "type": "buy"             # Transaction type: buy, sell
		    }
		  ],
		  "message": "Ok"
		}
		{"id": 533912782, "type": "buy", "price": "11.45737771", "amount": "0.0002", "date": 1534742487, "date_ms": 1534742487664}
	 * Transaction history id, send 0 to draw from the latest record.
	 * return up to 1000
	 */
	@Override
	public DealtInfo getSettlementPrice(String accessKey, String secretkey, Integer size, String... symbols) {
		String param = Arrays.toString(symbols);
		ISignature signature = new CoinexParamsSigner();
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.market_deals);
		signature.putParam(CoinexAPI.Market_deals_params.market.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		signature.putParam(CoinexAPI.Market_deals_params.last_id.getCode(), size.toString());
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.market_deals_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.market_deals_method_get);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
    		String result = content.asString();
        	logger.warn("ticker=" + result);
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(result); 
        	List<Map<String, Object>> tickerList = (List<Map<String, Object>>)jsonMap.get("data");
        	DealtInfo dealtInfo = null;
        	Map<String, Object> item = null;

        	long time = getSplittingTime();//确定应用服务器与交易所服务器时间一致
        	for (Map<String, Object> map : tickerList) {
	    		item = map;//((List<Map<String, Object>>) map.get("data")).get(0);
	    		if (Long.valueOf(item.get("date_ms").toString()).longValue() <= time) {
	    			System.out.println("ts=" +Long.valueOf(item.get("date_ms").toString()).longValue());
	    			dealtInfo = new DealtInfo();
	    			dealtInfo.setDirection(com.mumu.common.Constants.TRADING_DIRECTION.valueOf(item.get("type").toString().toUpperCase()));
	    			dealtInfo.setId(item.get("id").toString());
	    			dealtInfo.setPrice(new Double(item.get("price").toString()));
	    			dealtInfo.setTs(Long.valueOf(item.get("date_ms").toString()));
	    			dealtInfo.setVolume(new Double(item.get("amount").toString()));
	    			break;
	    		}
			}                 
        	
        	return dealtInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.market_deals +" 请求失败", e);
		}
    	
    	return null;
	}

	
	
}
