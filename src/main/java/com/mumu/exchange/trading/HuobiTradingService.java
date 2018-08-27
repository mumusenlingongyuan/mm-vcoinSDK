package com.mumu.exchange.trading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.mumu.exchange.api.HoubiAPI;
import com.mumu.exchange.coins.HuobiProfiles;
import com.mumu.exchange.common.Constants;
import com.mumu.exchange.common.Constants.HUOBI_ORDER_STATUS;
import com.mumu.exchange.common.Constants.HUOBI_ORDER_TYPE;
import com.mumu.exchange.common.JacksonHelper;
import com.mumu.exchange.common.RequestUtils;
import com.mumu.exchange.signature.HuobiParamsSigner;
import com.mumu.exchange.signature.ISignature;

public final class HuobiTradingService extends TradingService implements ITradingService {

	/**
	 * 查询资产详情方法调用顺序：查询当前用户的所有账户->查询指定账户的余额
	 * {"status":"ok","data":[{"id":4276741,"type":"spot","subtype":"","state":"working"},{"id":4439752,"type":"otc","subtype":"","state":"working"}]}
	 */
	@Override
	public AccountInfo accountInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.account_accounts);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.account_accounts_method_get);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.account_accounts+" 请求失败", e);
		}
    	
    	String result = content.asString();
    	String accountId = null;
    	try {
			Map<String, Object> jsonMap = (Map<String, Object>) JacksonHelper.getMapper().readValue(result, JacksonHelper.mapType);
			if (Constants.EXCHANGE_HOUBI_STATUS_OK.equals(jsonMap.get("status"))) {
				List<Map<String, String>> accountList = (List<Map<String, String>>)jsonMap.get("data");
				for (Iterator iterator = accountList.iterator(); iterator.hasNext();) {
					Map<String, String> map = (Map<String, String>) iterator.next();
					if (Constants.HUOBI_ACCOUNTS_TYPE.spot.name().equals(map.get("type").toString())) {
						accountId = String.valueOf(map.get("id"));
						break;
					}
				}
			}
		} catch (Exception e) {
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.account_accounts+" Json转Map失败", e);
		}
    	System.out.println(accountId);
    	
    	signature.clearParams();
    	signature.putParam(HoubiAPI.Accounts_p$accountId_balance_params.path$accountId.name(), accountId);
    	return this.accountBalance(accessKey, secretkey, signature);
    	
	}
	
	
	/**
	 * {"status":"ok","data":"9595716268"}
	 */
	@Override
	public Order.Response orders(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.order_orders_place);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.order_orders_place_method_post);
    		RequestUtils.addJson(request, signature.getParams(), HuobiParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		Order.Response response = new Order.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	RESPONSE_STATUS status = RESPONSE_STATUS.getByCode(jsonMap.get("status").toString());
        	if (RESPONSE_STATUS.OK.equals(status)) {
        		response.setStatus(status);
        		response.setTid(jsonMap.get("data").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("err-code").toString());
        		response.setErrorMsg(jsonMap.get("err-msg").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.order_orders_place+" 请求失败", e);
		}
    	
    	
    	return null;
	}

	
	/**
	 * {
	 * "status": "ok",//注意，返回OK表示撤单请求成功。订单是否撤销成功请调用订单查询接口查询该订单状态
	 * "data": "59378"
	 *	}
	 */
	@Override
	public Cancel.Response cancel(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		String apiUri = HoubiAPI.order_orders_path$orderId_submitcancel.replace(HoubiAPI.Order_orders_path$orderId_submitcancel_params.path$orderId.getCode(), signature.getParams().get(HoubiAPI.Order_orders_path$orderId_submitcancel_params.path$orderId.name()));
		signature.setApiUri(apiUri);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Post(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.order_orders_path$orderId_submitcancel_method_post);
    		RequestUtils.addJson(request, signature.getParams(), HuobiParamsSigner.charset_utf8);
    		content = request
			.execute()
			.returnContent();
    		
    		Cancel.Response response = new Cancel.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	RESPONSE_STATUS status = RESPONSE_STATUS.getByCode(jsonMap.get("status").toString());
        	if (RESPONSE_STATUS.OK.equals(status)) {
        		response.setStatus(status);
        		response.setTid(jsonMap.get("data").toString());
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("err-code").toString());
        		response.setErrorMsg(jsonMap.get("err-msg").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.order_orders_path$orderId_submitcancel+" 请求失败", e);
		}
    	
    	return null;
	}

	
	/**
	 * 
	 */
	@Override
	public GetOrder.Response orderInfo(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		String apiUri = HoubiAPI.order_orders_path$orderId.replace(HoubiAPI.Order_orders_path$orderId_params.path$orderId.getCode(), signature.getParams().get(HoubiAPI.Order_orders_path$orderId_params.path$orderId.name()));
		signature.setApiUri(apiUri);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.order_orders_path$orderId_method_get);
    		content = request
			.execute()
			.returnContent();
//    		{"status":"ok","data":{"id":10721786675,"symbol":"bchbtc","account-id":4276741,"amount":"0.037700000000000000","price":"0.089918000000000000",
//    			"created-at":1534843681358,"type":"sell-limit","field-amount":"0.0","field-cash-amount":"0.0","field-fees":"0.0",
//    			"finished-at":0,"source":"api","state":"submitted","canceled-at":0}}
    		GetOrder.Response response = new  GetOrder.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	RESPONSE_STATUS status = RESPONSE_STATUS.getByCode(jsonMap.get("status").toString());
        	if (RESPONSE_STATUS.OK.equals(status)) {
        		jsonMap = (Map<String, Object>)jsonMap.get("data");
        		OrderInfo orderInfo = new OrderInfo();
        		orderInfo.setAccountId(jsonMap.get("account-id").toString());
        		String type = jsonMap.get("type").toString().toUpperCase();
        		orderInfo.setDirection(TRADING_DIRECTION.valueOf(type.substring(0, type.indexOf("-"))));
        		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_HOUBI);
        		orderInfo.setFee(jsonMap.get("field-fees").toString());
//        		orderInfo.setFeeSymbol((String)jsonMap.get("fee_asset"));
        		orderInfo.setId(jsonMap.get("id").toString());
        		orderInfo.setOriginalVolume(jsonMap.get("amount").toString());
        		orderInfo.setPrice(jsonMap.get("price").toString());
        		ORDER_STATUS status2 = ORDER_STATUS.valueOf(HUOBI_ORDER_STATUS.valueOf(jsonMap.get("state").toString()).getStatus());
        		orderInfo.setStatus(status2);
        		orderInfo.setSymbol(jsonMap.get("symbol").toString());
        		orderInfo.setTs(jsonMap.get("created-at").toString());
        		ORDER_TYPE type2 = ORDER_TYPE.valueOf(type.substring(type.indexOf("-")+1, type.length()));
        		orderInfo.setType(type2);
        		orderInfo.setVolume(jsonMap.get("field-amount").toString());
            	response.setStatus(RESPONSE_STATUS.OK);
            	
        		response.setOrderInfo(orderInfo);
        		
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("err-code").toString());
        		response.setErrorMsg(jsonMap.get("err-msg").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.order_orders_path$orderId+" 请求失败", e);
		}
    	
    	return null;
	}

	

	@Override
	public Map<String, String> getGetOrderParamsMap(GetOrder getOrder) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(HoubiAPI.Order_orders_path$orderId_params.path$orderId.name(), getOrder.getTid());
		
		return params;
	}


	/**
	 * {"status":"ok","data":[{"filled-amount":"0.0","filled-fees":"0.0","filled-cash-amount":"0.0","symbol":"bchbtc",
	 * "source":"api","amount":"0.037700000000000000","created-at":1534843681358,"price":"0.089918000000000000",
	 * "account-id":4276741,"id":10721786675,"state":"submitted","type":"sell-limit"}]}
	 */
	@Override
	public ListOrders.Response unmatchedList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.order_openOrders);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.order_openOrders_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		ListOrders.Response response = new  ListOrders.Response();
        	Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	RESPONSE_STATUS status = RESPONSE_STATUS.getByCode(jsonMap.get("status").toString());
        	if (RESPONSE_STATUS.OK.equals(status)) {
        		List<Map<String, Object>> orderList = (List<Map<String, Object>>) jsonMap.get("data");
        		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>(orderList.size());
        		OrderInfo orderInfo = null;
        		
        		for (Map<String, Object> map : orderList) {
        			orderInfo = new OrderInfo();
        			
        			orderInfo.setAccountId(map.get("account-id").toString());
            		String type = map.get("type").toString().toUpperCase();
            		orderInfo.setDirection(TRADING_DIRECTION.valueOf(type.substring(0, type.indexOf("-"))));
            		orderInfo.setExchange(EXCHANGE_NAME.EXCHANGE_HOUBI);
            		orderInfo.setFee(map.get("filled-fees").toString());
//            		orderInfo.setFeeSymbol((String)jsonMap.get("fee_asset"));
            		orderInfo.setId(map.get("id").toString());
            		orderInfo.setOriginalVolume(map.get("amount").toString());
            		orderInfo.setPrice(map.get("price").toString());
            		ORDER_STATUS status2 = ORDER_STATUS.valueOf(HUOBI_ORDER_STATUS.valueOf(map.get("state").toString()).getStatus());
            		orderInfo.setStatus(status2);
            		orderInfo.setSymbol(map.get("symbol").toString());
            		orderInfo.setTs(map.get("created-at").toString());
            		ORDER_TYPE type2 = ORDER_TYPE.valueOf(type.substring(type.indexOf("-")+1, type.length()));
            		orderInfo.setType(type2);
            		orderInfo.setVolume(map.get("filled-amount").toString());
            		
            		orderInfoList.add(orderInfo);
				}
        		
            	response.setStatus(RESPONSE_STATUS.OK);
        		response.setListOrderInfo(orderInfoList);
        		
        	} else {
        		response.setStatus(RESPONSE_STATUS.ERROR);
        		response.setErrorCode(jsonMap.get("err-code").toString());
        		response.setErrorMsg(jsonMap.get("err-msg").toString());
        	}
        	return response;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.order_openOrders+" 请求失败", e);
		}
    	
    	return null;
	}

	

	@Override
	public Map<String, String> getListUnMatchedParamsMap(ListOrders listOrders) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(HoubiAPI.Order_openOrders_params.account_id.getCode(), listOrders.getAccountId());
		params.put(HoubiAPI.Order_openOrders_params.symbol.getCode(), listOrders.getSymbol());
//		params.put(HoubiAPI.Order_openOrders_params.side.getCode(), listOrders.get)
		params.put(HoubiAPI.Order_openOrders_params.size.getCode(), "500");
		
		return params;
	}


	@Override
	public String historyList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.order_orders);
		signature.putParam(HoubiAPI.Order_orders_params.states.getCode(), 
			Constants.HUOBI_ORDER_STATUS.canceled.getCode() + "," +
			Constants.HUOBI_ORDER_STATUS.filled.getCode()+ "," + 
			Constants.HUOBI_ORDER_STATUS.partial_canceled.getCode()+ ","+ 
			Constants.HUOBI_ORDER_STATUS.partial_filled.getCode()
//			+ ","+ Constants.HOUBI_ORDER_STATUS.submitted.getCode()
			);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.order_orders_method_get);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.order_orders+" 请求失败", e);
		}
    	
    	return content.asString();
	}


	
	
	@Override
	public String depositList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.query_deposit_withdraw);
		signature.putParam(HoubiAPI.Query_deposit_withdraw_params.type.getCode(), Constants.HOUBI_deposit);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.query_deposit_withdraw_method_get);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.query_deposit_withdraw+" 请求失败", e);
		}
    	
    	return content.asString();
	}


	@Override
	public String withdrawList(String accessKey, String secretkey, ISignature signature) {
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		signature.setApiUri(HoubiAPI.query_deposit_withdraw);
		signature.putParam(HoubiAPI.Query_deposit_withdraw_params.type.getCode(), Constants.HOUBI_withdraw);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.query_deposit_withdraw_method_get);
    		content = request
			.execute()
			.returnContent();
    		
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.query_deposit_withdraw+" 请求失败", e);
		}
    	
    	return content.asString();
	}


	/**
	 * {"status":"ok","data":{"id":4276741,"type":"spot","state":"working","list":[
	 * {"currency":"hb10","type":"trade","balance":"0"},
	 * {"currency":"hb10","type":"frozen","balance":"0"},
	 * {"currency":"usdt","type":"trade","balance":"0"},
	 * {"currency":"usdt","type":"frozen","balance":"0"},
	 * {"currency":"btc","type":"trade","balance":"0.003596"},
	 * {"currency":"neo","type":"frozen","balance":"0"},
	 * {"currency":"trx","type":"trade","balance":"0"},{"currency":"trx","type":"frozen","balance":"0"},{"currency":"icx","type":"trade","balance":"0"},{"currency":"icx","type":"frozen","balance":"0"},{"currency":"lsk","type":"trade","balance":"0"},{"currency":"lsk","type":"frozen","balance":"0"},{"currency":"qtum","type":"trade","balance":"0"},{"currency":"qtum","type":"frozen","balance":"0"},{"currency":"etc","type":"trade","balance":"0"},{"currency":"etc","type":"frozen","balance":"0"},{"currency":"btg","type":"trade","balance":"0"},{"currency":"btg","type":"frozen","balance":"0"},{"currency":"omg","type":"trade","balance":"0"},{"currency":"omg","type":"frozen","balance":"0"},{"currency":"hsr","type":"trade","balance":"0"},{"currency":"hsr","type":"frozen","balance":"0"},{"currency":"zec","type":"trade","balance":"0"},{"currency":"zec","type":"frozen","balance":"0"},{"currency":"dcr","type":"trade","balance":"0"},{"currency":"dcr","type":"frozen","balance":"0"},{"currency":"steem","type":"trade","balance":"0"},{"currency":"steem","type":"frozen","balance":"0"},{"currency":"bts","type":"trade","balance":"0"},{"currency":"bts","type":"frozen","balance":"0"},{"currency":"waves","type":"trade","balance":"0"},{"currency":"waves","type":"frozen","balance":"0"},{"currency":"snt","type":"trade","balance":"0"},{"currency":"snt","type":"frozen","balance":"0"},{"currency":"salt","type":"trade","balance":"0"},{"currency":"salt","type":"frozen","balance":"0"},{"currency":"gnt","type":"trade","balance":"0"},{"currency":"gnt","type":"frozen","balance":"0"},{"currency":"cmt","type":"trade","balance":"0"},{"currency":"cmt","type":"frozen","balance":"0"},{"currency":"btm","type":"trade","balance":"0"},{"currency":"btm","type":"frozen","balance":"0"},{"currency":"pay","type":"trade","balance":"0"},{"currency":"pay","type":"frozen","balance":"0"},{"currency":"knc","type":"trade","balance":"0"},{"currency":"knc","type":"frozen","balance":"0"},{"currency":"powr","type":"trade","balance":"0"},{"currency":"powr","type":"frozen","balance":"0"},{"currency":"bat","type":"trade","balance":"0"},{"currency":"bat","type":"frozen","balance":"0"},{"currency":"dgd","type":"trade","balance":"0"},{"currency":"dgd","type":"frozen","balance":"0"},{"currency":"ven","type":"trade","balance":"0"},{"currency":"ven","type":"frozen","balance":"0"},{"currency":"qash","type":"trade","balance":"0"},{"currency":"qash","type":"frozen","balance":"0"},{"currency":"zrx","type":"trade","balance":"0"},{"currency":"zrx","type":"frozen","balance":"0"},{"currency":"gas","type":"trade","balance":"0"},{"currency":"gas","type":"frozen","balance":"0"},{"currency":"mana","type":"trade","balance":"0"},{"currency":"mana","type":"frozen","balance":"0"},{"currency":"eng","type":"trade","balance":"0"},{"currency":"eng","type":"frozen","balance":"0"},{"currency":"cvc","type":"trade","balance":"0"},{"currency":"cvc","type":"frozen","balance":"0"},{"currency":"mco","type":"trade","balance":"0"},{"currency":"mco","type":"frozen","balance":"0"},{"currency":"mtl","type":"trade","balance":"0"},{"currency":"mtl","type":"frozen","balance":"0"},{"currency":"rdn","type":"trade","balance":"0"},{"currency":"rdn","type":"frozen","balance":"0"},{"currency":"storj","type":"trade","balance":"0"},{"currency":"storj","type":"frozen","balance":"0"},{"currency":"chat","type":"trade","balance":"0"},{"currency":"chat","type":"frozen","balance":"0"},{"currency":"srn","type":"trade","balance":"0"},{"currency":"srn","type":"frozen","balance":"0"},{"currency":"link","type":"trade","balance":"0"},{"currency":"link","type":"frozen","balance":"0"},{"currency":"act","type":"trade","balance":"0"},{"currency":"act","type":"frozen","balance":"0"},{"currency":"tnb","type":"trade","balance":"0"},{"currency":"tnb","type":"frozen","balance":"0"},{"currency":"qsp","type":"trade","balance":"0"},{"currency":"qsp","type":"frozen","balance":"0"},{"currency":"req","type":"trade","balance":"0"},{"currency":"req","type":"frozen","balance":"0"},{"currency":"rpx","type":"trade","balance":"0"},{"currency":"rpx","type":"frozen","balance":"0"},{"currency":"appc","type":"trade","balance":"0"},{"currency":"appc","type":"frozen","balance":"0"},{"currency":"rcn","type":"trade","balance":"0"},{"currency":"rcn","type":"frozen","balance":"0"},{"currency":"smt","type":"trade","balance":"0"},{"currency":"smt","type":"frozen","balance":"0"},{"currency":"adx","type":"trade","balance":"0"},{"currency":"adx","type":"frozen","balance":"0"},{"currency":"tnt","type":"trade","balance":"0"},{"currency":"tnt","type":"frozen","balance":"0"},{"currency":"ost","type":"trade","balance":"0"},{"currency":"ost","type":"frozen","balance":"0"},{"currency":"itc","type":"trade","balance":"0"},{"currency":"itc","type":"frozen","balance":"0"},{"currency":"lun","type":"trade","balance":"0"},{"currency":"lun","type":"frozen","balance":"0"},{"currency":"gnx","type":"trade","balance":"0"},{"currency":"gnx","type":"frozen","balance":"0"},{"currency":"ast","type":"trade","balance":"0"},{"currency":"ast","type":"frozen","balance":"0"},{"currency":"evx","type":"trade","balance":"0"},{"currency":"evx","type":"frozen","balance":"0"},{"currency":"mds","type":"trade","balance":"0"},{"currency":"mds","type":"frozen","balance":"0"},{"currency":"snc","type":"trade","balance":"0"},{"currency":"snc","type":"frozen","balance":"0"},{"currency":"propy","type":"trade","balance":"0"},{"currency":"propy","type":"frozen","balance":"0"},{"currency":"eko","type":"trade","balance":"0"},{"currency":"eko","type":"frozen","balance":"0"},{"currency":"nas","type":"trade","balance":"0"},{"currency":"nas","type":"frozen","balance":"0"},{"currency":"bcd","type":"trade","balance":"0"},{"currency":"bcd","type":"frozen","balance":"0"},{"currency":"wax","type":"trade","balance":"0"},{"currency":"wax","type":"frozen","balance":"0"},{"currency":"wicc","type":"trade","balance":"0"},{"currency":"wicc","type":"frozen","balance":"0"},{"currency":"topc","type":"trade","balance":"0"},{"currency":"topc","type":"frozen","balance":"0"},{"currency":"swftc","type":"trade","balance":"0"},{"currency":"swftc","type":"frozen","balance":"0"},{"currency":"dbc","type":"trade","balance":"0"},{"currency":"dbc","type":"frozen","balance":"0"},{"currency":"elf","type":"trade","balance":"0"},{"currency":"elf","type":"frozen","balance":"0"},{"currency":"aidoc","type":"trade","balance":"0"},{"currency":"aidoc","type":"frozen","balance":"0"},{"currency":"qun","type":"trade","balance":"0"},{"currency":"qun","type":"frozen","balance":"0"},{"currency":"iost","type":"trade","balance":"0"},{"currency":"iost","type":"frozen","balance":"0"},{"currency":"yee","type":"trade","balance":"0"},{"currency":"yee","type":"frozen","balance":"0"},{"currency":"dat","type":"trade","balance":"0"},{"currency":"dat","type":"frozen","balance":"0"},{"currency":"theta","type":"trade","balance":"0"},{"currency":"theta","type":"frozen","balance":"0"},{"currency":"let","type":"trade","balance":"0"},{"currency":"let","type":"frozen","balance":"0"},{"currency":"dta","type":"trade","balance":"0"},{"currency":"dta","type":"frozen","balance":"0"},{"currency":"utk","type":"trade","balance":"0"},{"currency":"utk","type":"frozen","balance":"0"},{"currency":"meet","type":"trade","balance":"0"},{"currency":"meet","type":"frozen","balance":"0"},{"currency":"zil","type":"trade","balance":"0"},{"currency":"zil","type":"frozen","balance":"0"},{"currency":"soc","type":"trade","balance":"0"},{"currency":"soc","type":"frozen","balance":"0"},{"currency":"ruff","type":"trade","balance":"0"},{"currency":"ruff","type":"frozen","balance":"0"},{"currency":"ocn","type":"trade","balance":"0"},{"currency":"ocn","type":"frozen","balance":"0"},{"currency":"ela","type":"trade","balance":"0"},{"currency":"ela","type":"frozen","balance":"0"},{"currency":"bcx","type":"trade","balance":"0"},{"currency":"bcx","type":"frozen","balance":"0"},{"currency":"sbtc","type":"trade","balance":"0"},{"currency":"sbtc","type":"frozen","balance":"0"},{"currency":"etf","type":"trade","balance":"0"},{"currency":"etf","type":"frozen","balance":"0"},{"currency":"bifi","type":"trade","balance":"0"},{"currency":"bifi","type":"frozen","balance":"0"},{"currency":"zla","type":"trade","balance":"0"},{"currency":"zla","type":"frozen","balance":"0"},{"currency":"stk","type":"trade","balance":"0"},{"currency":"stk","type":"frozen","balance":"0"},{"currency":"wpr","type":"trade","balance":"0"},{"currency":"wpr","type":"frozen","balance":"0"},{"currency":"mtn","type":"trade","balance":"0"},{"currency":"mtn","type":"frozen","balance":"0"},{"currency":"mtx","type":"trade","balance":"0"},{"currency":"mtx","type":"frozen","balance":"0"},{"currency":"edu","type":"trade","balance":"0"},{"currency":"edu","type":"frozen","balance":"0"},{"currency":"blz","type":"trade","balance":"0"},{"currency":"blz","type":"frozen","balance":"0"},{"currency":"abt","type":"trade","balance":"0"},{"currency":"abt","type":"frozen","balance":"0"},{"currency":"ont","type":"trade","balance":"0"},{"currency":"ont","type":"frozen","balance":"0"},{"currency":"ctxc","type":"trade","balance":"0"},{"currency":"ctxc","type":"frozen","balance":"0"},{"currency":"bft","type":"trade","balance":"0"},{"currency":"bft","type":"frozen","balance":"0"},{"currency":"wan","type":"trade","balance":"0"},{"currency":"wan","type":"frozen","balance":"0"},{"currency":"kan","type":"trade","balance":"0"},{"currency":"kan","type":"frozen","balance":"0"},{"currency":"lba","type":"trade","balance":"0"},{"currency":"lba","type":"frozen","balance":"0"},{"currency":"poly","type":"trade","balance":"0"},{"currency":"poly","type":"frozen","balance":"0"},{"currency":"pai","type":"trade","balance":"0"},{"currency":"pai","type":"frozen","balance":"0"},{"currency":"wtc","type":"trade","balance":"0"},{"currency":"wtc","type":"frozen","balance":"0"},{"currency":"box","type":"trade","balance":"0"},{"currency":"box","type":"frozen","balance":"0"},{"currency":"dgb","type":"trade","balance":"0"},{"currency":"dgb","type":"frozen","balance":"0"},{"currency":"gxs","type":"trade","balance":"0"},{"currency":"gxs","type":"frozen","balance":"0"},{"currency":"bix","type":"trade","balance":"0"},{"currency":"bix","type":"frozen","balance":"0"},{"currency":"xlm","type":"trade","balance":"0"},{"currency":"xlm","type":"frozen","balance":"0"},{"currency":"xvg","type":"trade","balance":"0"},{"currency":"xvg","type":"frozen","balance":"0"},{"currency":"hit","type":"trade","balance":"0"},{"currency":"hit","type":"frozen","balance":"0"},{"currency":"bt1","type":"trade","balance":"0"},{"currency":"bt1","type":"frozen","balance":"0"},{"currency":"bt2","type":"trade","balance":"0"},{"currency":"bt2","type":"frozen","balance":"0"},{"currency":"a5b5","type":"trade","balance":"0"},{"currency":"a5b5","type":"frozen","balance":"0"},{"currency":"x5z5","type":"trade","balance":"0"},{"currency":"x5z5","type":"frozen","balance":"0"},{"currency":"egcc","type":"trade","balance":"0"},{"currency":"egcc","type":"frozen","balance":"0"}]}}
	 * @param accessKey
	 * @param secretkey
	 * @param signature
	 * @return
	 */
	public AccountInfo accountBalance(String accessKey, String secretkey, ISignature signature) {
		
		signature.setApiRoot(HoubiAPI.rest_trading_root);
		String apiUri = HoubiAPI.accounts_p$accountId_balance.replace(HoubiAPI.Accounts_p$accountId_balance_params.path$accountId.getCode(), signature.getParams().get(HoubiAPI.Accounts_p$accountId_balance_params.path$accountId.name()));
		signature.setApiUri(apiUri);
		String uri = signature.sign(accessKey, secretkey);
		logger.warn("uri="  + uri);
		Content content = null;
    	try {
    		Request request = Request.Get(uri);
    		RequestUtils.setProxy(request);
    		HuobiProfiles.addHeader(request, HoubiAPI.accounts_p$accountId_balance_method_get);
    		content = request
			.execute()
			.returnContent();
    		
    		AccountInfo accountInfo = new AccountInfo();
    		
    		Map<String, Object> jsonMap = JacksonHelper.getJsonMap(content.asString());
        	RESPONSE_STATUS status = RESPONSE_STATUS.getByCode(jsonMap.get("status").toString());
        	if (RESPONSE_STATUS.OK.equals(status)) {
        		accountInfo.setStatus(status);
        		accountInfo.setNormal(Boolean.valueOf("working".equals(((Map<String, Object>)jsonMap.get("data")).get("state").toString())));
        		
        		List<Map<String, Object>> assetList = (List<Map<String, Object>>)((Map<String, Object>)jsonMap.get("data")).get("list");
        		Map<String, AccountInfo.AssetItem> assetMap = new HashMap<String, AccountInfo.AssetItem>(assetList.size());
        		AccountInfo.AssetItem assetItem = null;
        		
        		for (Iterator iterator = assetList.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					
					assetItem = accountInfo.new AssetItem();
        			assetItem.setCurrency(map.get("currency").toString());
        			assetItem.setAvailable(map.get("balance").toString());
        		
        			map = (Map<String, Object>) iterator.next();
        			assetItem.setFrozen(map.get("balance").toString());
        			
        			assetMap.put(map.get("currency").toString().toUpperCase(), assetItem);
				}
        		
        		accountInfo.setAssetMap(assetMap);
        	} else {
        		accountInfo.setStatus(RESPONSE_STATUS.ERROR);
        		accountInfo.setErrorCode(jsonMap.get("err-code").toString());
        		accountInfo.setErrorMsg(jsonMap.get("err-msg").toString());
        	}
        	
        	return accountInfo;
		} catch (Exception e) {
			e.printStackTrace();
			super.logger.error(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI+":"+HoubiAPI.accounts_p$accountId_balance+" 请求失败", e);
		}
    	
    	return null; 
				
	}


	@Override
	public Map<String, String> getOrdersParamsMap(Order order) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(HoubiAPI.Order_orders_place_params.account_id.getCode(), order.getAccountId());
		params.put(HoubiAPI.Order_orders_place_params.amount.getCode(), String.valueOf(order.getVolume()));
		params.put(HoubiAPI.Order_orders_place_params.price.getCode(), String.valueOf(order.getPrice()));
		params.put(HoubiAPI.Order_orders_place_params.symbol.getCode(), order.getSymbol());
		params.put(HoubiAPI.Order_orders_place_params.source.getCode(), "api");
		String type = HUOBI_ORDER_TYPE.getByCode(order.getDirection().getCode().toLowerCase() +"-"+ order.getType().getCode().toLowerCase()).getCode().toString();
		params.put(HoubiAPI.Order_orders_place_params.type.getCode(), type);
		
		return params;
	}


	@Override
	public Map<String, String> getCancelParamsMap(Cancel cancel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(HoubiAPI.Order_orders_path$orderId_submitcancel_params.path$orderId.name(), cancel.getTid());
		
		return params;
	}

	
	
	
}
