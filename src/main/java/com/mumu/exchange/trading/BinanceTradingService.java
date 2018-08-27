package com.mumu.exchange.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mumu.beans.AccountInfo;
import com.mumu.beans.AccountInfo.AssetItem;
import com.mumu.beans.Cancel;
import com.mumu.beans.GetOrder;
import com.mumu.beans.ListOrders;
import com.mumu.beans.Order;
import com.mumu.beans.OrderInfo;
import com.mumu.common.Constants.EXCHANGE_NAME;
import com.mumu.common.Constants.ORDER_STATUS;
import com.mumu.common.Constants.ORDER_TYPE;
import com.mumu.common.Constants.RESPONSE_STATUS;
import com.mumu.common.Constants.TRADING_DIRECTION;
import com.mumu.exchange.api.BinanceAPI;
import com.mumu.exchange.coins.BinanceProfiles;
import com.mumu.exchange.common.Constants;
import com.mumu.exchange.common.Constants.BINANCE_ORDER_STATUS;
import com.mumu.exchange.common.Constants.BINANCE_RESP_TYPE;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.BinanceParamsSigner;
import com.mumu.exchange.signature.CoinexParamsSigner;
import com.mumu.exchange.signature.ISignature;

public final class BinanceTradingService extends TradingService implements ITradingService {

	/**
	 *{"makerCommission":10,"takerCommission":10,"buyerCommission":0,"sellerCommission":0,
	 *"canTrade":true,"canWithdraw":true,
	 *"canDeposit":true,"updateTime":1533047914613,"balances":[]}
	 */
	@Override
	public AccountInfo accountInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_account);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_account_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
		AccountInfo accountInfo = new AccountInfo();
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_account_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
    		
    		
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
    		List<Map<String, Object>> assetList = (List<Map<String, Object>>)jsonMap.get("balances");
    		Map<String, AccountInfo.AssetItem> assetMap = new HashMap<String, AccountInfo.AssetItem>(assetList.size());
    		accountInfo.setStatus(RESPONSE_STATUS.OK);
    		accountInfo.setNormal(Boolean.valueOf(jsonMap.get("canTrade").toString()));
    		AccountInfo.AssetItem assetItem = null;
    		for (Map<String, Object> map : assetList) {
    			assetItem = accountInfo.new AssetItem();
    			assetItem.setCurrency(map.get("asset").toString());
    			assetItem.setAvailable(map.get("free").toString());
    			assetItem.setFrozen(map.get("locked").toString());
    			assetMap.put(map.get("asset").toString(), assetItem);
			}
    		accountInfo.setAssetMap(assetMap);
        	return accountInfo;
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				accountInfo.setStatus(RESPONSE_STATUS.ERROR);
				accountInfo.setErrorCode(((HttpResponseException)e).getStatusCode()+"");
				accountInfo.setErrorMsg(e.getMessage());
			}
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_account +" 请求失败", e);
		}
    	
    	return accountInfo; 
				
	}
	
	
	
	/**
	 * {
		  "symbol": "BTCUSDT",
		  "orderId": 28,
		  "clientOrderId": "6gCrw2kRUAF9CvJDGP16IP",
		  "transactTime": 1507725176595
		}
	 */
	@Override
	public Order.Response orders(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_order);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_order_method_post, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
		Order.Response response = new Order.Response();
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_order_method_post);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		RequestUtils.addForm(request, signature.getParams(), BinanceParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	response.setStatus(RESPONSE_STATUS.OK);
    		response.setTid(jsonMap.get("orderId").toString());
        	return response;
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				response.setStatus(RESPONSE_STATUS.ERROR);
				response.setErrorCode(((HttpResponseException)e).getStatusCode()+"");
				response.setErrorMsg(e.getMessage());
			}
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_order +" 请求失败", e);
		}
    	
    	
    	return response; 
	}



	/**
	 * {"symbol":"ETHUSDT","origClientOrderId":"xSwlx0AGT2QpZNcxw1N8nb","orderId":106751148,"clientOrderId":"y2l6OE6g99UY7WUZuWsgTg"}
	 */
	@Override
	public Cancel.Response cancel(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_cancel_order);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_cancel_order_method_delete, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
		Cancel.Response response = new Cancel.Response();
    	try {
    		Request request = Request.Delete(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_cancel_order_method_delete);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
    		
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	response.setStatus(RESPONSE_STATUS.OK);
    		response.setTid(jsonMap.get("orderId").toString());
        	return response;
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				response.setStatus(RESPONSE_STATUS.ERROR);
				response.setErrorCode(((HttpResponseException)e).getStatusCode()+"");
				response.setErrorMsg(e.getMessage());
			}
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_cancel_order_alias +" 请求失败", e);
		}
    	
    	return response; 
	}



	/**
	 * Either orderId or origClientOrderId must be sent.
		For some historical orders cummulativeQuoteQty will be < 0, meaning the data is not available at this time.
		{"symbol":"ETHUSDT","orderId":101276044,"clientOrderId":"ze4GEJbN8uehxLEtZif87K",
		"price":"380.00000000","origQty":"0.04000000","executedQty":"0.00000000",
		"cummulativeQuoteQty":"0.00000000","status":"CANCELED","timeInForce":"GTC",
		"type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000",
		"time":1533882630265,"updateTime":1533886507003,"isWorking":true}
	 */
	@Override
	public GetOrder.Response orderInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_get_order);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_get_order_method_get, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
		GetOrder.Response response = new GetOrder.Response();
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_get_order_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
//    		{"":"ETHUSDT","orderId":107276381,"clientOrderId":"365225833","price":"380.00000000","origQty":"0.04000000",
//    			"executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC",
//    			"":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000",
//    			"":1535009341169,"updateTime":1535009341169,"isWorking":true}
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
    		OrderInfo orderInfo = new OrderInfo();
//    		orderInfo.setAccountId(jsonMap.get("").toString());
    		orderInfo.setDirection(TRADING_DIRECTION.valueOf(jsonMap.get("side").toString().toUpperCase()));
    		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_BINANCE);
//    		orderInfo.setFee(jsonMap.get("").toString());
//    		orderInfo.setFeeSymbol(jsonMap.get("").toString());
    		orderInfo.setId(jsonMap.get("orderId").toString());
    		orderInfo.setOriginalVolume(jsonMap.get("origQty").toString());
    		orderInfo.setPrice(jsonMap.get("price").toString());
    		orderInfo.setRequestId((String)jsonMap.get("clientOrderId"));
    		ORDER_STATUS status = ORDER_STATUS.valueOf(BINANCE_ORDER_STATUS.valueOf(jsonMap.get("status").toString()).getStatus());
    		orderInfo.setStatus(status);
    		orderInfo.setSymbol(jsonMap.get("symbol").toString());
    		orderInfo.setTs(jsonMap.get("time").toString());
    		ORDER_TYPE type = ORDER_TYPE.valueOf(jsonMap.get("type").toString());
    		orderInfo.setType(type);
    		orderInfo.setVolume(jsonMap.get("executedQty").toString());
        	response.setStatus(RESPONSE_STATUS.OK);
    		response.setOrderInfo(orderInfo);
        	return response;
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				response.setStatus(RESPONSE_STATUS.ERROR);
				response.setErrorCode(((HttpResponseException)e).getStatusCode()+"");
				response.setErrorMsg(e.getMessage());
			}
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_get_order_alias +" 请求失败", e);
		}
    	
    	return response; 
	}



	/**
	 * 
	 */
	@Override
	public ListOrders.Response unmatchedList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_openOrders);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_openOrders_method_get, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
		ListOrders.Response response = new ListOrders.Response();
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_openOrders_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
//    		[{"symbol":"ETHUSDT","orderId":107276381,"clientOrderId":"365225833","price":"380.00000000","origQty":"0.04000000",
//    			"executedQty":"0.00000000","cummulativeQuoteQty":"0.00000000","status":"NEW","timeInForce":"GTC",
//    			"type":"LIMIT","side":"SELL","stopPrice":"0.00000000","icebergQty":"0.00000000","time":1535009341169,
//    			"updateTime":1535009341169,"isWorking":true}]
    		JavaType listMap = TypeFactory.defaultInstance().constructParametricType(List.class, Map.class);
    		List<Map<String, Object>> orderList = (List<Map<String, Object>>) JacksonHelper.getMapper().readValue(content.asStream(), listMap);
    		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>(orderList.size());
    		OrderInfo orderInfo = null;
    		for (Map<String, Object> jsonMap : orderList) {
    			orderInfo = new OrderInfo();
    			
        		orderInfo.setDirection(TRADING_DIRECTION.valueOf(jsonMap.get("side").toString().toUpperCase()));
        		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_BINANCE);
        		orderInfo.setId(jsonMap.get("orderId").toString());
        		orderInfo.setOriginalVolume(jsonMap.get("origQty").toString());
        		orderInfo.setPrice(jsonMap.get("price").toString());
        		orderInfo.setRequestId((String)jsonMap.get("clientOrderId"));
        		ORDER_STATUS status = ORDER_STATUS.valueOf(BINANCE_ORDER_STATUS.valueOf(jsonMap.get("status").toString()).getStatus());
        		orderInfo.setStatus(status);
        		orderInfo.setSymbol(jsonMap.get("symbol").toString());
        		orderInfo.setTs(jsonMap.get("time").toString());
        		ORDER_TYPE type = ORDER_TYPE.valueOf(jsonMap.get("type").toString());
        		orderInfo.setType(type);
        		orderInfo.setVolume(jsonMap.get("executedQty").toString());
            	
        		orderInfoList.add(orderInfo);
			}
    		response.setStatus(RESPONSE_STATUS.OK);
    		response.setListOrderInfo(orderInfoList);
        	return response;
    		
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				response.setStatus(RESPONSE_STATUS.ERROR);
				response.setErrorCode(((HttpResponseException)e).getStatusCode()+"");
				response.setErrorMsg(e.getMessage());
			}
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_openOrders +" 请求失败", e);
		}
    	
    	return response; 
	}

	

	@Override
	public Map<String, String> getListUnMatchedParamsMap(ListOrders listOrders) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(BinanceAPI.Api_openOrders_params.symbol.getCode(), listOrders.getSymbol());
		return params;
	}



	/**
	 * 
	 */
	@Override
	public String historyList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.api_allOrders);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.api_allOrders_method_get, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.api_allOrders_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.api_allOrders +" 请求失败", e);
		}
    	
    	String result = content.asString();
    	return result; 
	}



	/**
	 * 
	 */
	@Override
	public String depositList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.wapi_depositHistory);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.wapi_depositHistory_method_get, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.wapi_depositHistory_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.wapi_depositHistory +" 请求失败", e);
		}
    	
    	String result = content.asString();
    	return result; 
	}



	/***
	 * 
	 */
	@Override
	public String withdrawList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(BinanceAPI.rest_trading_root);
		signature.setApiUri(BinanceAPI.wapi_withdrawHistory);
		String authorization = signature.sign(accessKey, secretkey);
		signature.putParam(BinanceAPI.API_SIGN_KEY_authorization, authorization);
		String uri = signature.getUri(BinanceAPI.wapi_withdrawHistory_method_get, BinanceParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		BinanceProfiles.addHeader(request, BinanceAPI.wapi_withdrawHistory_method_get);
    		request.addHeader(BinanceAPI.API_SIGN_KEY_AccessKeyId, accessKey);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE +":"+ BinanceAPI.wapi_withdrawHistory +" 请求失败", e);
		}
    	
    	String result = content.asString();
    	return result; 
	}



	@Override
	public Map<String, String> getOrdersParamsMap(Order order) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(BinanceAPI.Api_order_params.timeInForce.getCode(), Constants.BINANCE_TIMEINFORCE.GTC.getCode());
		params.put(BinanceAPI.Api_order_params.quantity.getCode(), String.valueOf(order.getVolume()));
		params.put(BinanceAPI.Api_order_params.price.getCode(), String.valueOf(order.getPrice()));
		params.put(BinanceAPI.Api_order_params.symbol.getCode(), order.getSymbol());
		params.put(BinanceAPI.Api_order_params.side.getCode(), order.getDirection().name());
		params.put(BinanceAPI.Api_order_params.type.getCode(), order.getType().name());
		params.put(BinanceAPI.Api_order_params.newClientOrderId.getCode(), order.getRequestId());
		params.put(BinanceAPI.Api_order_params.newOrderRespType.getCode(), BINANCE_RESP_TYPE.ACK.getCode());
		
		return params;
	}



	@Override
	public Map<String, String> getCancelParamsMap(Cancel cancel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(BinanceAPI.Api_cancel_order_params.symbol.getCode(), cancel.getSymbol());
		if (StringUtils.isNotBlank(cancel.getTid())) {
			params.put(BinanceAPI.Api_cancel_order_params.orderId.getCode(), cancel.getTid());
		}
		
		if (StringUtils.isNotBlank(cancel.getRequestId())) {
			params.put(BinanceAPI.Api_cancel_order_params.newClientOrderId.getCode(), cancel.getRequestId());
		}
		
		return params;
	}



	@Override
	public Map<String, String> getGetOrderParamsMap(GetOrder getOrder) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(BinanceAPI.Api_get_order_params.symbol.getCode(), getOrder.getSymbol());
		params.put(BinanceAPI.Api_get_order_params.orderId.getCode(), getOrder.getTid());
		
		return params;
	}
	
}
