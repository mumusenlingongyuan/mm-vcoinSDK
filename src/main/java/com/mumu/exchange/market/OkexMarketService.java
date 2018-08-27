package com.mumu.exchange.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mumu.beans.DealtInfo;
import com.mumu.beans.Market;
import com.mumu.common.Constants.EXCHANGE_NAME;
import com.mumu.exchange.api.OkexAPI;
import com.mumu.exchange.coins.OkexProfiles;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.ISignature;
import com.mumu.exchange.signature.OkexParamsSigner;

public class OkexMarketService extends MarketService implements IMarketService {

	/**
	 *单一
	 */
	@Override
	public List<Market> quotationTicker(String accessKey, String secretkey, String... symbols) {
		String param = Arrays.toString(symbols);
		
		ISignature signature = new OkexParamsSigner();
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_ticker);
		signature.putParam(OkexAPI.Api_ticker_params.symbol.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(OkexAPI.api_ticker_method_get, OkexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_ticker_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		String result = content.asString();
        	logger.warn("ticker=" + result);
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(result);
        	Map<String, Object> tickerMap = (Map<String, Object>)jsonMap.get("ticker");
        	List<Market> listMarket = new ArrayList<Market>(1);
        	Market market = new Market();
//        	market.setAmount(new Double(tickerMap.get("vol").toString()));
        	market.setAsk(new Double(tickerMap.get("sell").toString()));
//        	market.setAskVolume(((List<Double>)tickerMap.get("ask")).get(1));
        	market.setBid(new Double(tickerMap.get("buy").toString()));
//        	market.setBidVolume(((List<Double>)tickerMap.get("bid")).get(1));
        	
        	market.setHighest(new Double(tickerMap.get("high").toString()));
        	market.setLowest(new Double(tickerMap.get("low").toString()));
        	market.setLast(new Double(tickerMap.get("last").toString()));
//        	market.setOpen(new Double(tickerMap.get("open").toString()));
        	market.setSymbol(symbols[0]);
        	market.setTs(Long.valueOf(jsonMap.get("date").toString()) * 1000);
        	market.setVolume(new Double(tickerMap.get("vol").toString()));
        	
        	listMarket.add(market);
        	return listMarket;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(EXCHANGE_NAME.EXCHANGE_OKEX.name() +":"+ OkexAPI.api_ticker +" 请求失败", e);
		}
    	
    	
    	return null;
	}

	/**
	 * date:交易时间
		date_ms:交易时间(ms)
		price: 交易价格
		amount: 交易数量
		tid: 交易生成ID
		type: buy/sell
		{"date":1534739097,"date_ms":1534739097042,"amount":7.1561,"price":0.08766501,"type":"buy","tid":293687005}
	 */
	@Override
	public DealtInfo getSettlementPrice(String accessKey, String secretkey, Integer size, String... symbols) {
		String param = Arrays.toString(symbols);
		
		ISignature signature = new OkexParamsSigner();
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_trades);
		signature.putParam(OkexAPI.Api_trades_params.symbol.getCode(), StringUtils.trim(param.substring(1, (param.length() - 1))));
		if (null != size) {
			signature.putParam(OkexAPI.Api_trades_params.since.getCode(), size.toString());
		} 
		
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(OkexAPI.Api_trades_method_get, OkexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.Api_trades_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		String result = content.asString();
        	logger.warn("ticker=" + result);
    		
    		JavaType listMap = TypeFactory.defaultInstance().constructParametricType(List.class, Map.class);
    		List<Map<String, Object>> tickerList = (List<Map<String, Object>>) JacksonHelper.getMapper().readValue(result, listMap);
        	DealtInfo dealtInfo = null;
        	Map<String, Object> item = null;

        	long time = getSplittingTime();//确定应用服务器与交易所服务器时间一致
        	for (Map<String, Object> map : tickerList) {
	    		item = map;
	    		if (Long.valueOf(item.get("date_ms").toString()).longValue() <= time) {
	    			System.out.println("ts=" +Long.valueOf(item.get("date_ms").toString()).longValue());
	    			dealtInfo = new DealtInfo();
	    			dealtInfo.setDirection(com.mumu.common.Constants.TRADING_DIRECTION.valueOf(item.get("type").toString().toUpperCase()));
	    			dealtInfo.setId(item.get("tid").toString());
	    			dealtInfo.setPrice(new Double(item.get("price").toString()));
	    			dealtInfo.setTs(Long.valueOf(item.get("date_ms").toString()));
	    			dealtInfo.setVolume(new Double(item.get("amount").toString()));
	    			break;
	    		}
			}                 
        	
        	return dealtInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_trades +" 请求失败", e);
		}
    	
    	
    	return null;
	}

	
	
}
