package com.mumu.exchange.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
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
import com.mumu.exchange.api.CoinexAPI;
import com.mumu.exchange.coins.CoinexProfiles;
import com.mumu.exchange.common.Constants;
import com.mumu.exchange.common.Constants.COINEX_ORDER_STATUS;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.CoinexParamsSigner;
import com.mumu.exchange.signature.ISignature;

public final class CoinexTradingService extends TradingService implements ITradingService {

	/**
	 * {
	  "code": 0, 
	  "data": {
	    "BBN": {
	      "available": "0", 
	      "frozen": "0"
	    }, 
	    "BCH": {
	      "available": "0", 
	      "frozen": "0"
	    }, 
	    "BTC": {
	      "available": "0", 
	      "frozen": "0"
	    }, 
	 */
	@Override
	public AccountInfo accountInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.balance_info);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.balance_info_method_get, CoinexParamsSigner.charset_utf8);
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
    		
    		AccountInfo accountInfo = new AccountInfo();
    		AccountInfo.AssetItem assetItem = null;
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if ("0".equals(jsonMap.get("code").toString())) {
        		accountInfo.setStatus(RESPONSE_STATUS.OK);

        		@SuppressWarnings("unchecked")
				Map<String, Object> assetList = (Map<String, Object>)jsonMap.get("data");
        		Map<String, AccountInfo.AssetItem> assetMap = new HashMap<String, AccountInfo.AssetItem>(assetList.size());
        		
        		for (Map.Entry<String, Object> entry : assetList.entrySet()) {
        			
        			assetItem = accountInfo.new AssetItem();
        			assetItem.setCurrency(entry.getKey());
        			assetItem.setAvailable(((Map<String, String>)entry.getValue()).get("available"));
        			assetItem.setFrozen(((Map<String, String>)entry.getValue()).get("frozen"));
        			
        			assetMap.put(entry.getKey(), assetItem);
				}
        		accountInfo.setAssetMap(assetMap);
        	} else {
        		accountInfo.setStatus(RESPONSE_STATUS.ERROR);
        		accountInfo.setErrorCode(jsonMap.get("code").toString());
        		accountInfo.setErrorMsg(Constants.oKexErrorMap.get(accountInfo.getErrorCode()));
        	}
        	
        	return accountInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.balance_info +" 请求失败", e);
		}
    	
    	return null; 
				
	}
	
	
	
	/**
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
	 */
	@Override
	public Order.Response orders(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		String apiUri = CoinexAPI.order_path$orderType.replace(CoinexAPI.Order_path$orderType_params.path$orderType.getCode(), signature.getParams().get(CoinexAPI.Order_path$orderType_params.path$orderType.name()));
		signature.setApiUri(apiUri);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.order_path$orderType_method_post, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.order_path$orderType_method_post);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		
    		RequestUtils.addJson(request, signature.getParams(), CoinexParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		Order.Response response = new Order.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if ("0".equals(jsonMap.get("code").toString())) {
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setTid(((Map<String, Object>)jsonMap.get("data")).get("id").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("code").toString());
        		response.setErrorMsg(jsonMap.get("message").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ apiUri +" 请求失败", e);
		}
    	
    	return null; 
	}



	/**
	 * 取消的记录有些交易所直接删除
	 */
	@Override
	public Cancel.Response cancel(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.order_pending);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.order_pending_method_delete, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Delete(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.order_pending_method_delete);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
    		Cancel.Response response = new Cancel.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	if ("0".equals(jsonMap.get("code").toString())) {
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setTid(((Map<String, Object>)jsonMap.get("data")).get("id").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("code").toString());
        		response.setErrorMsg(jsonMap.get("message").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.order_pending +" 请求失败", e);
		}
    	
    	return null; 
	}



	/**
	 * {
		  "code": 227, 
		  "data": {}, 
		  "message": "tonce check error, correct tonce should be within one minute of the current time"
		}
		
		{
		  "code": 0, 
		  "data": {
		    "amount": "0.00202284", 
		    "asset_fee": "0", 
		    "avg_price": "0.00", 
		    "create_time": 1535013061, 
		    "deal_amount": "0", 
		    "deal_fee": "0", 
		    "deal_money": "0", 
		    "fee_asset": null, 
		    "fee_discount": "0", 
		    "id": 1392841140, 
		    "left": "0.00202284", 
		    "maker_fee_rate": "0.001", 
		    "market": "ETHBCH", 
		    "order_type": "limit", 
		    "price": "0.42667640", 
		    "status": "not_deal", 
		    "taker_fee_rate": "0.001", 
		    "type": "buy"
		  }, 
		  "message": "Ok"
		}

	 */
	@Override
	public GetOrder.Response orderInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.order_status);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.order_status_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.order_status_method_get);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
    		GetOrder.Response response = new GetOrder.Response();
    		if ("0".equals(jsonMap.get("code").toString())) {
        		response.setStatus(RESPONSE_STATUS.OK);

        		OrderInfo orderInfo = new OrderInfo();
        		orderInfo.setDirection(TRADING_DIRECTION.valueOf(jsonMap.get("type").toString().toUpperCase()));
        		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_COINEX);
        		orderInfo.setFee(jsonMap.get("deal_fee").toString());
        		orderInfo.setFeeSymbol((String)jsonMap.get("fee_asset"));
        		orderInfo.setId(jsonMap.get("id").toString());
        		orderInfo.setOriginalVolume(jsonMap.get("amount").toString());
        		orderInfo.setPrice(jsonMap.get("price").toString());
        		ORDER_STATUS status = ORDER_STATUS.valueOf(COINEX_ORDER_STATUS.valueOf(jsonMap.get("status").toString()).getStatus());
        		orderInfo.setStatus(status);
        		orderInfo.setSymbol(jsonMap.get("market").toString());
        		orderInfo.setTs(jsonMap.get("create_time").toString());
        		ORDER_TYPE type = ORDER_TYPE.valueOf(jsonMap.get("order_type").toString().toUpperCase());
        		orderInfo.setType(type);
        		orderInfo.setVolume(jsonMap.get("deal_amount").toString());
            	response.setStatus(RESPONSE_STATUS.OK);
            	
        		response.setOrderInfo(orderInfo);
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("code").toString());
        		response.setErrorMsg(jsonMap.get("message").toString());
        	}
    		
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.order_status +" 请求失败", e);
		}
    	
    	return null; 
	}

	

	@Override
	public Map<String, String> getGetOrderParamsMap(GetOrder getOrder) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(CoinexAPI.Order_pending_params.market.getCode(), getOrder.getSymbol());
		params.put(CoinexAPI.Order_pending_params.id.getCode(), getOrder.getTid());
		
		return params;
	}



	/**
	 * {
		  "code": 0, 
		  "data": {
		    "count": 1, 
		    "curr_page": 1, 
		    "data": [
		      {
		        "amount": "0.00202284", 
		        "asset_fee": "0", 
		        "avg_price": "0.00", 
		        "create_time": 1535013061, 
		        "deal_amount": "0", 
		        "deal_fee": "0", 
		        "deal_money": "0", 
		        "fee_asset": null, 
		        "fee_discount": "0", 
		        "id": 1392841140, 
		        "left": "0.00202284", 
		        "maker_fee_rate": "0.001", 
		        "market": "ETHBCH", 
		        "order_type": "limit", 
		        "price": "0.42667640", 
		        "status": "not_deal", 
		        "taker_fee_rate": "0.001", 
		        "type": "buy"
		      }
		    ], 
		    "has_next": false, 
		    "total": 1
		  }, 
		  "message": "Ok"
		}

	 */
	@Override
	public ListOrders.Response unmatchedList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.order_pending_list);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.order_pending_list_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.order_pending_list_method_get);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
    		ListOrders.Response response = new ListOrders.Response();
    		if ("0".equals(jsonMap.get("code").toString())) {
        		List<Map<String, Object>> orderList = (List<Map<String, Object>>) ((Map<String, Object>) jsonMap.get("data")).get("data");
        		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>(orderList.size());
        		OrderInfo orderInfo = null;
        		for (Map<String, Object> jsonMap2 : orderList) {
        			orderInfo = new OrderInfo();
        			
        			orderInfo.setDirection(TRADING_DIRECTION.valueOf(jsonMap2.get("type").toString().toUpperCase()));
            		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_COINEX);
            		orderInfo.setFee(jsonMap2.get("deal_fee").toString());
            		orderInfo.setFeeSymbol((String)jsonMap2.get("fee_asset"));
            		orderInfo.setId(jsonMap2.get("id").toString());
            		orderInfo.setOriginalVolume(jsonMap2.get("amount").toString());
            		orderInfo.setPrice(jsonMap2.get("price").toString());
            		ORDER_STATUS status = ORDER_STATUS.valueOf(COINEX_ORDER_STATUS.valueOf(jsonMap2.get("status").toString()).getStatus());
            		orderInfo.setStatus(status);
            		orderInfo.setSymbol(jsonMap2.get("market").toString());
            		orderInfo.setTs(jsonMap2.get("create_time").toString());
            		ORDER_TYPE type = ORDER_TYPE.valueOf(jsonMap2.get("order_type").toString().toUpperCase());
            		orderInfo.setType(type);
            		orderInfo.setVolume(jsonMap2.get("deal_amount").toString());
                	
            		orderInfoList.add(orderInfo);
    			}
        		
        		
        		response.setStatus(RESPONSE_STATUS.OK);
        		response.setListOrderInfo(orderInfoList);
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("code").toString());
        		response.setErrorMsg(jsonMap.get("message").toString());
        	}
    		
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.order_pending_list_alias +" 请求失败", e);
		}
    	
    	return null; 
	}

	

	@Override
	public Map<String, String> getListUnMatchedParamsMap(ListOrders listOrders) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(CoinexAPI.Order_pending_list_params.market.getCode(), listOrders.getSymbol());
		params.put(CoinexAPI.Order_pending_list_params.page.getCode(), "1");
		params.put(CoinexAPI.Order_pending_list_params.limit.getCode(), "100");
		
		return params;
	}



	/**
	 * order_finished
	 */
	@Override
	public String historyList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(CoinexAPI.rest_trading_root);
		signature.setApiUri(CoinexAPI.order_finished);
		String authorization = signature.sign(accessKey, secretkey);
		String uri = signature.getUri(CoinexAPI.order_finished_method_get, CoinexParamsSigner.charset_utf8);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		CoinexProfiles.addHeader(request, CoinexAPI.order_finished_method_get);
    		request.addHeader(CoinexAPI.API_SIGN_KEY_authorization, authorization);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX +":"+ CoinexAPI.order_finished +" 请求失败", e);
		}
    	
    	String result = content.asString();
    	return result; 
	}



	@Override
	public Map<String, String> getOrdersParamsMap(Order order) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(CoinexAPI.Order_path$orderType_params.path$orderType.name(), order.getType().getCode().toLowerCase());
		params.put(CoinexAPI.Order_path$orderType_params.amount.getCode(), String.valueOf(order.getVolume()));
		params.put(CoinexAPI.Order_path$orderType_params.price.getCode(), String.valueOf(order.getPrice()));
		params.put(CoinexAPI.Order_path$orderType_params.market.getCode(), order.getSymbol());
		params.put(CoinexAPI.Order_path$orderType_params.type.getCode(), order.getDirection().getCode().toLowerCase());
		
		return params;
	}



	@Override
	public Map<String, String> getCancelParamsMap(Cancel cancel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(CoinexAPI.Order_pending_params.market.getCode(), cancel.getSymbol());
		params.put(CoinexAPI.Order_pending_params.id.getCode(), cancel.getTid());
		
		return params;
	}
	
}
