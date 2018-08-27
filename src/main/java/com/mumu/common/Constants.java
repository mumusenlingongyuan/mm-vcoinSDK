package com.mumu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Constants {
	
	public static enum EXCHANGE_NAME {
		EXCHANGE_HOUBI("huobi"),
		EXCHANGE_COINEX("coinex"),
		EXCHANGE_OKEX("okex"),
		EXCHANGE_BINANCE("binance");
		
		private final String code;
		
		private EXCHANGE_NAME(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			EXCHANGE_NAME[] statuses = EXCHANGE_NAME.values();
			List<HashMap<String, String>> kvPairList = new ArrayList<HashMap<String, String>>(statuses.length);
			HashMap<String, String> kvPairMap = null;
			
			for (int i = 0; i < statuses.length; i++) {
				kvPairMap = new HashMap<String, String>(2);
				kvPairMap.put("name", statuses[i].name());
				kvPairMap.put("code", statuses[i].code);
				
				kvPairList.add(kvPairMap);
			}
			return kvPairList;
		}
		
		public static HashMap<String, EXCHANGE_NAME> getCodeEnumMap() {
			EXCHANGE_NAME[] from = EXCHANGE_NAME.values();
			HashMap<String, EXCHANGE_NAME> kvPairMap = new HashMap<String, EXCHANGE_NAME>();;
			for (int i = 0; i < from.length; i++) {
				kvPairMap.put(from[i].code, from[i]);
			}
			return kvPairMap;
		}
	}
	
	
	public static enum TRADING_DIRECTION {
		BUY("buy"),
		SELL("sell");
		
		private final String code;
		
		private TRADING_DIRECTION(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			TRADING_DIRECTION[] statuses = TRADING_DIRECTION.values();
			List<HashMap<String, String>> kvPairList = new ArrayList<HashMap<String, String>>(statuses.length);
			HashMap<String, String> kvPairMap = null;
			
			for (int i = 0; i < statuses.length; i++) {
				kvPairMap = new HashMap<String, String>(2);
				kvPairMap.put("name", statuses[i].name());
				kvPairMap.put("code", statuses[i].code);
				
				kvPairList.add(kvPairMap);
			}
			return kvPairList;
		}
	}
	
	/**
	 * buy-limit-maker
		当“下单价格”>=“市场最低卖出价”，订单提交后，系统将拒绝接受此订单；
		当“下单价格”<“市场最低卖出价”，提交成功后，此订单将被系统接受。
		sell-limit-maker
		当“下单价格”<=“市场最高买入价”，订单提交后，系统将拒绝接受此订单；
		当“下单价格”>“市场最高买入价”，提交成功后，此订单将被系统接受。
	 *
	 */
	public static enum ORDER_TYPE {
		LIMIT("limit"),//
		MARKET("market"),//
		IOC("ioc"),//
		LIMIT_MAKER("LIMIT-MAKER"),//
		STOP_LOSS("STOP_LOSS"),//市价止损
		STOP_LOSS_LIMIT("STOP_LOSS_LIMIT"),//限价止损
		TAKE_PROFIT("TAKE_PROFIT"),//市价止盈
		TAKE_PROFIT_LIMIT("TAKE_PROFIT_LIMIT");//限价止盈;
		
		private final String code;
		
		private ORDER_TYPE(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			ORDER_TYPE[] statuses = ORDER_TYPE.values();
			List<HashMap<String, String>> kvPairList = new ArrayList<HashMap<String, String>>(statuses.length);
			HashMap<String, String> kvPairMap = null;
			
			for (int i = 0; i < statuses.length; i++) {
				kvPairMap = new HashMap<String, String>(2);
				kvPairMap.put("name", statuses[i].name());
				kvPairMap.put("code", statuses[i].code);
				
				kvPairList.add(kvPairMap);
			}
			return kvPairList;
		}
	}
	
	public static enum RESPONSE_STATUS {
		OK("ok"),
		ERROR("error");
		
		private final String code;
		
		private RESPONSE_STATUS(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static RESPONSE_STATUS getByCode(String code) {
			RESPONSE_STATUS type = null;
			
			for (int i = 0; i < RESPONSE_STATUS.values().length; i++) {
				type = RESPONSE_STATUS.values()[i];
				if (type.getCode().equals(code)) {
					break;
				}
			}
			
			return type;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			RESPONSE_STATUS[] statuses = RESPONSE_STATUS.values();
			List<HashMap<String, String>> kvPairList = new ArrayList<HashMap<String, String>>(statuses.length);
			HashMap<String, String> kvPairMap = null;
			
			for (int i = 0; i < statuses.length; i++) {
				kvPairMap = new HashMap<String, String>(2);
				kvPairMap.put("name", statuses[i].name());
				kvPairMap.put("code", statuses[i].code);
				
				kvPairList.add(kvPairMap);
			}
			return kvPairList;
		}
	}
	
	public static enum ORDER_STATUS {
		UNMATCHED("未成交"), 
		MATCHED("全部成交"),
		CANCELED("已撤销"),
		CANCELING("撤销中"),
		PART_MATCHED("部分成交"),
		PART_CANCELED("部成部撤");
		
		private final String code;
		
		private ORDER_STATUS(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static ORDER_STATUS getByCode(String code) {
			ORDER_STATUS type = null;
			
			for (int i = 0; i < ORDER_STATUS.values().length; i++) {
				type = ORDER_STATUS.values()[i];
				if (type.getCode().equals(code)) {
					break;
				}
			}
			
			return type;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			ORDER_STATUS[] statuses = ORDER_STATUS.values();
			List<HashMap<String, String>> kvPairList = new ArrayList<HashMap<String, String>>(statuses.length);
			HashMap<String, String> kvPairMap = null;
			
			for (int i = 0; i < statuses.length; i++) {
				kvPairMap = new HashMap<String, String>(2);
				kvPairMap.put("name", statuses[i].name());
				kvPairMap.put("code", statuses[i].code);
				
				kvPairList.add(kvPairMap);
			}
			return kvPairList;
		}
	}
}
