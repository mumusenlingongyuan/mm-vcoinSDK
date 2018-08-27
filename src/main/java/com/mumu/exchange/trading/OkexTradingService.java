package com.mumu.exchange.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

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
import com.mumu.exchange.api.OkexAPI;
import com.mumu.exchange.coins.OkexProfiles;
import com.mumu.exchange.common.Constants;
import com.mumu.exchange.common.Constants.OKEX_ORDER_STATUS;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.ISignature;
import com.mumu.exchange.signature.OkexParamsSigner;

public final class OkexTradingService extends TradingService implements ITradingService {

	/**
	 * {"result":true,"info":{"funds":{"free":{"ssc":"0","okb":"0","kcash":"0","theta":"0",
	 * "vib":"0","ugc":"0","trio":"0","egt":"0","brd":"0",
	 */
	@Override
	public AccountInfo accountInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_userinfo);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_userinfo_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_userinfo_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		AccountInfo accountInfo = new AccountInfo();
    		AccountInfo.AssetItem assetItem = null;
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if (Boolean.valueOf(jsonMap.get("result").toString())) {
        		accountInfo.setStatus(RESPONSE_STATUS.OK);

        		Map<String, Object> assetList = ((Map<String, Object>)((Map<String, Object>)jsonMap.get("info")).get("funds"));
        		Map<String, Object> freeMap = (Map<String, Object>) assetList.get("free");
        		Map<String, Object> freezedMap = (Map<String, Object>) assetList.get("freezed");
        		Map<String, AccountInfo.AssetItem> assetMap = new HashMap<String, AccountInfo.AssetItem>(freeMap.size());
        		
        		for (Map.Entry<String, Object> entry : freeMap.entrySet()) {
	        		assetItem = accountInfo.new AssetItem();
	    			assetItem.setCurrency(entry.getKey());
	    			assetItem.setAvailable(entry.getValue().toString());
	    			assetItem.setFrozen(freezedMap.get(entry.getKey()).toString());
	    			
	    			assetMap.put(entry.getKey().toUpperCase(), assetItem);
        		}
        		accountInfo.setAssetMap(assetMap);
        	} else {
        		accountInfo.setStatus(RESPONSE_STATUS.ERROR);
        		accountInfo.setErrorCode(jsonMap.get("error_code").toString());
        		accountInfo.setErrorMsg(Constants.oKexErrorMap.get(accountInfo.getErrorCode()));
        	}
        	
        	return accountInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_userinfo +" 请求失败", e);
		}
    	
    	return null; 
				
	}
	
	

	/**
	 * o:{"error_code":1002}
		 * o:{"result":true,"order_id":53649387}
	 */
	@Override
	public Order.Response orders(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_trade);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_trade_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_trade_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		Order.Response response = new Order.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if (null != jsonMap.get("result") && Boolean.valueOf(jsonMap.get("result").toString())) {
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setTid(jsonMap.get("order_id").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("error_code").toString());
        		response.setErrorMsg(Constants.oKexErrorMap.get(response.getErrorCode()));
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_trade +" 请求失败", e);
		}
    	
    	return null; 
	}



	@Override
	public Cancel.Response cancel(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_cancel_order);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_cancel_order_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_cancel_order_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		Cancel.Response response = new Cancel.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if (null != jsonMap.get("result") && Boolean.valueOf(jsonMap.get("result").toString())) {
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setTid(jsonMap.get("order_id").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("error_code").toString());
        		response.setErrorMsg(Constants.oKexErrorMap.get(response.getErrorCode()));
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_cancel_order +" 请求失败", e);
		}
    	
    	return null; 
	}

	

	@Override
	public Map<String, String> getGetOrderParamsMap(GetOrder getOrder) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OkexAPI.Api_order_info_params.symbol.getCode(), getOrder.getSymbol());
		params.put(OkexAPI.Api_order_info_params.order_id.getCode(), getOrder.getTid());
		
		return params;
	}



	@Override
	public Map<String, String> getCancelParamsMap(Cancel cancel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OkexAPI.Api_cancel_order_params.symbol.getCode(), cancel.getSymbol());
		params.put(OkexAPI.Api_cancel_order_params.order_id.getCode(), cancel.getTid());
		
		return params;
	}



	@Override
	public GetOrder.Response orderInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_order_info);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_order_info_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_order_info_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
//    		{"result":true,"orders":[{"amount":0.068394,"avg_price":0,"create_date":1535073706000,"deal_amount":0,
//    			"order_id":54614577,"orders_id":54614577,"price":0.18162686,"status":0,"symbol":"ltc_eth","type":"buy"}]}
    		GetOrder.Response response = new GetOrder.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if (null != jsonMap.get("result") && Boolean.valueOf(jsonMap.get("result").toString())) {
        		List<Map<String, Object>> listMap = (List<Map<String, Object>>) jsonMap.get("orders");
        		jsonMap = listMap.get(0);
        		response.setStatus(RESPONSE_STATUS.OK);
        		
        		OrderInfo orderInfo = new OrderInfo();
        		String[] orderType = jsonMap.get("type").toString().split("_");
        		orderInfo.setDirection(TRADING_DIRECTION.valueOf(orderType[0].toUpperCase()));
        		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_OKEX);
        		orderInfo.setId(jsonMap.get("order_id").toString());
        		orderInfo.setOriginalVolume(jsonMap.get("amount").toString());
        		orderInfo.setPrice(jsonMap.get("price").toString());
        		ORDER_STATUS status = ORDER_STATUS.valueOf(OKEX_ORDER_STATUS.getByCode(jsonMap.get("status").toString()).getStatus());
        		orderInfo.setStatus(status);
        		orderInfo.setSymbol(jsonMap.get("symbol").toString());
        		orderInfo.setTs(jsonMap.get("create_date").toString());
        		ORDER_TYPE type = null;
        		if (1 == orderType.length) {
        			type = ORDER_TYPE.LIMIT;
        		} else {
        			type = ORDER_TYPE.MARKET;
        		}
        		orderInfo.setType(type);
        		orderInfo.setVolume(jsonMap.get("deal_amount").toString());
            	
        		response.setOrderInfo(orderInfo);
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("error_code").toString());
        		response.setErrorMsg(Constants.oKexErrorMap.get(response.getErrorCode()));
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_order_info +" 请求失败", e);
		}
    	
    	return null; 
	}



	@Override
	public ListOrders.Response unmatchedList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_order_info);
		signature.putParam(OkexAPI.Api_order_info_params.order_id.getCode(), "-1");
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_order_info_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_order_info_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		ListOrders.Response response = new ListOrders.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if (null != jsonMap.get("result") && Boolean.valueOf(jsonMap.get("result").toString())) {
        		List<Map<String, Object>> listMap = (List<Map<String, Object>>) jsonMap.get("orders");
        		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>(listMap.size());
        		OrderInfo orderInfo = null;
        		
        		for (Map<String, Object> jsonMap2 : listMap) {
        			orderInfo = new OrderInfo();
        			String[] orderType = jsonMap2.get("type").toString().split("_");
            		orderInfo.setDirection(TRADING_DIRECTION.valueOf(orderType[0].toUpperCase()));
            		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_OKEX);
            		orderInfo.setId(jsonMap2.get("order_id").toString());
            		orderInfo.setOriginalVolume(jsonMap2.get("amount").toString());
            		orderInfo.setPrice(jsonMap2.get("price").toString());
            		ORDER_STATUS status = ORDER_STATUS.valueOf(OKEX_ORDER_STATUS.getByCode(jsonMap2.get("status").toString()).getStatus());
            		orderInfo.setStatus(status);
            		orderInfo.setSymbol(jsonMap2.get("symbol").toString());
            		orderInfo.setTs(jsonMap2.get("create_date").toString());
            		ORDER_TYPE type = null;
            		if (1 == orderType.length) {
            			type = ORDER_TYPE.LIMIT;
            		} else {
            			type = ORDER_TYPE.MARKET;
            		}
            		orderInfo.setType(type);
            		orderInfo.setVolume(jsonMap2.get("deal_amount").toString());
            		
            		orderInfoList.add(orderInfo);
        		}
        		
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setListOrderInfo(orderInfoList);
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("error_code").toString());
        		response.setErrorMsg(Constants.oKexErrorMap.get(response.getErrorCode()));
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_order_info +" 请求失败", e);
		}
    	
    	return null; 

	}

	

	@Override
	public Map<String, String> getListUnMatchedParamsMap(ListOrders listOrders) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OkexAPI.Api_order_info_params.symbol.getCode(), listOrders.getSymbol());
		return params;
	}



	@Override
	public String historyList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(OkexAPI.rest_trading_root);
		signature.setApiUri(OkexAPI.api_order_history);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(OkexAPI.api_order_history_method_post, OkexParamsSigner.charset_utf8);
//		uri = uri + "&" + OkexAPI.API_SIGN_KEY_authorization +"="+ authorization;
		signature.putParam(OkexAPI.API_SIGN_KEY_authorization, authorization);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		OkexProfiles.addHeader(request, OkexAPI.api_order_history_method_post);
    		RequestUtils.addForm(request, signature.getParams(), OkexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX +":"+ OkexAPI.api_order_history +" 请求失败", e);
		}
    	
    	String result = content.asString();
    	return result; 
	}



	
	@Override
	public Map<String, String> getOrdersParamsMap(Order order) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OkexAPI.Api_trade_params.amount.getCode(), String.valueOf(order.getVolume()));
		params.put(OkexAPI.Api_trade_params.price.getCode(), String.valueOf(order.getPrice()));
		params.put(OkexAPI.Api_trade_params.symbol.getCode(), order.getSymbol());
		String type = order.getDirection().getCode().toLowerCase();
		if (ORDER_TYPE.MARKET.equals(order.getType())) {
			type = com.mumu.exchange.common.Constants.OKEX_ORDER_TYPE.getByCode(order.getDirection().getCode().toLowerCase() +"_"+ order.getType().getCode().toLowerCase()).getCode().toLowerCase();
		}
		params.put(OkexAPI.Api_trade_params.type.getCode(), type);
		
		return params;
	}
}
