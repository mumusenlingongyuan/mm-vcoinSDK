package com.mumu.exchange.trading;

import java.util.Map;

import com.mumu.beans.AccountInfo;
import com.mumu.beans.Cancel;
import com.mumu.beans.GetOrder;
import com.mumu.beans.ListOrders;
import com.mumu.beans.Order;
import com.mumu.exchange.signature.ISignature;

public interface ITradingService {

	public AccountInfo accountInfo(String accessKey, String secretkey, ISignature signature);
	
	public Order.Response orders(String accessKey, String secretkey, ISignature signature);
	
	public Cancel.Response cancel(String accessKey, String secretkey, ISignature signature);
	
	public GetOrder.Response orderInfo(String accessKey, String secretkey, ISignature signature);
	
	public ListOrders.Response unmatchedList(String accessKey, String secretkey, ISignature signature);
	
//	public String dealtList(String accessKey, String secretkey, ISignature signature);
	
	public String historyList(String accessKey, String secretkey, ISignature signature);
	
	public String depositList(String accessKey, String secretkey, ISignature signature);
	
	public String withdrawList(String accessKey, String secretkey, ISignature signature);
	
	public Map<String, String> getOrdersParamsMap(Order order);
	public Map<String, String> getCancelParamsMap(Cancel cancel);
	public Map<String, String> getGetOrderParamsMap(GetOrder getOrder);
	public Map<String, String> getListUnMatchedParamsMap(ListOrders listOrders);
	public Map<String, String> getListMatchedParamsMap(ListOrders listOrders);
	public Map<String, String> getListHistoryParamsMap(ListOrders listOrders);
}
