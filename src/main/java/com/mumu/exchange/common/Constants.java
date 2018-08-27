package com.mumu.exchange.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	
	public static enum HUOBI_ACCOUNTS_TYPE {
		
		spot("现货账户"),
		otc("场外帐户");
		
		private final String code;
		
		private HUOBI_ACCOUNTS_TYPE(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			HUOBI_ACCOUNTS_TYPE[] statuses = HUOBI_ACCOUNTS_TYPE.values();
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
	 * buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, buy-ioc：IOC买单, sell-ioc：IOC卖单, buy-limit-maker, sell-limit-maker(详细说明见下)
	 * @author Aaron
	 *buy-limit-maker

		当“下单价格”>=“市场最低卖出价”，订单提交后，系统将拒绝接受此订单；
		
		当“下单价格”<“市场最低卖出价”，提交成功后，此订单将被系统接受。
		
		sell-limit-maker
		
		当“下单价格”<=“市场最高买入价”，订单提交后，系统将拒绝接受此订单；
		
		当“下单价格”>“市场最高买入价”，提交成功后，此订单将被系统接受。
	 */
	public static enum HUOBI_ORDER_TYPE {
		
		buy_market("buy-market"),//：市价买,
		sell_market("sell-market"),//：市价卖, 
		buy_limit("buy-limit"),//：限价买, 
		sell_limit("sell-limit"),//：限价卖, 
		buy_ioc("buy-io"),//：IOC买单, 
		sell_ioc("sell-ioc"),//：IOC卖单, 
		buy_limit_maker	("buy-limit-maker"),//, 
		sell_limit_maker("sell-limit-maker");
		
		public static HUOBI_ORDER_TYPE getByCode(String code) {
			HUOBI_ORDER_TYPE type = null;
			
			for (int i = 0; i < HUOBI_ORDER_TYPE.values().length; i++) {
				type = HUOBI_ORDER_TYPE.values()[i];
				if (type.getCode().equals(code)) {
					break;
				}
			}
			
			return type;
		}
		
		private final String code;
		
		private HUOBI_ORDER_TYPE(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			HUOBI_ORDER_TYPE[] statuses = HUOBI_ORDER_TYPE.values();
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
	 * submitted 已提交, partial-filled 部分成交, partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销
	 * /**
		 * Order status:
		 * UNMATCHED("未成交"), 
		MATCHED("全部成交"),
		CANCELED("已撤销"),
		CANCELING("撤销中"),
		PART_MATCHED("部分成交"),
		PART_CANCELED("部成部撤");
		
	 */
	public static enum HUOBI_ORDER_STATUS {
		
		submitted("submitted", "UNMATCHED"),
		partial_filled("partial-filled", "PART_MATCHED"),
		partial_canceled("partial-canceled", "PART_CANCELED"),
		filled("filled", "MATCHED"), 
		canceled("canceled", "CANCELED");
		
		private final String code;
		private final String status;
		
		private HUOBI_ORDER_STATUS(String code, String status) {
			this.code = code;
			this.status = status;
		}
		public String getCode() {
			return code;
		}
		
		
		public String getStatus() {
			return status;
		}
		public static HUOBI_ORDER_STATUS getByCode(String code) {
			HUOBI_ORDER_STATUS type = null;
			
			for (int i = 0; i < HUOBI_ORDER_STATUS.values().length; i++) {
				type = HUOBI_ORDER_STATUS.values()[i];
				if (type.getCode().equals(code)) {
					break;
				}
			}
			
			return type;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			HUOBI_ORDER_TYPE[] statuses = HUOBI_ORDER_TYPE.values();
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
	 * withdraw
	 * submitted	已提交
	 *	reexamine	审核中
	 *	canceled	已撤销
	 *	pass	审批通过
	 *	reject	审批拒绝
	 *	pre-transfer	处理中
	 *	wallet-transfer	已汇出
	 *	wallet-reject	钱包拒绝
	 *	confirmed	区块已确认
	 *	confirm-error	区块确认错误
	 *	repealed 已撤销
	 */
	public static enum HUOBI_WITHDRAW_STATUS {
		
		submitted("submitted"),
		reexamine("reexamine"),
		canceled("canceled"),
		pass("pass"),
		reject("reject"),
		pre_transfer("pre-transfer"),
		wallet_transfer("wallet-transfer"),
		wallet_reject("wallet-reject"),
		confirmed("confirmed"),
		confirm_error("confirm-error"),
		repealed("repealed");
		
		private final String code;
		
		private HUOBI_WITHDRAW_STATUS(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			HUOBI_WITHDRAW_STATUS[] statuses = HUOBI_WITHDRAW_STATUS.values();
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
	 * 虚拟币充值状态定义： deposit
		状态	描述
		unknown	状态未知
		confirming	确认中
		confirmed	确认中
		safe	已完成
		orphan	待确认
	 */
	public static enum HUOBI_DEPOSIT_STATUS {
		
		unknown("unknown"),
		confirming("confirming"),
		confirmed("confirmed"),
		safe("safe"),
		orphan("orphan");
		
		private final String code;
		
		private HUOBI_DEPOSIT_STATUS(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			HUOBI_DEPOSIT_STATUS[] statuses = HUOBI_DEPOSIT_STATUS.values();
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
	
	public static final String HOUBI_deposit = "deposit"; 
	public static final String HOUBI_withdraw = "withdraw";
	public static final String EXCHANGE_HOUBI_STATUS_OK = "ok";
	
	/**
	 * Symbol status:
	 */
	public static enum BINANCE_SYMBOL_STATUS {
		PRE_TRADING("PRE_TRADING"),
		TRADING("TRADING"),
		POST_TRADING("POST_TRADING"),
		END_OF_DAY("END_OF_DAY"),
		HALT("HALT"),
		AUCTION_MATCH("AUCTION_MATCH"),
		BREAK("BREAK");
		
		private final String code;
		
		private BINANCE_SYMBOL_STATUS(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		
		public static List<HashMap<String, String>> getKVPair() {
			BINANCE_SYMBOL_STATUS[] statuses = BINANCE_SYMBOL_STATUS.values();
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
		 * Symbol type:
		 */

		public static final String BINANCE_SYMBOL_TYPE_SPOT = "SPOT";
		
		/**
		 * Order status:
		 * UNMATCHED("未成交"), 
		MATCHED("全部成交"),
		CANCELED("已撤销"),
		CANCELING("撤销中"),
		PART_MATCHED("部分成交"),
		PART_CANCELED("部成部撤");
		 */
		public static enum BINANCE_ORDER_STATUS {
			NEW("NEW", "UNMATCHED"),
			PARTIALLY_FILLED("PARTIALLY_FILLED", "PART_MATCHED"),
			FILLED("FILLED", "MATCHED"),
			CANCELED("CANCELED","CANCELED"),
			PENDING_CANCEL("PENDING_CANCEL", "CANCELING"),//(currently unused)
			REJECTED("REJECTED", "CANCELED"),
			EXPIRED("EXPIRED", "CANCELED");
			
			private final String code;
			private final String status;
			
			private BINANCE_ORDER_STATUS(String code, String status) {
				this.code = code;
				this.status = status;
			}
			
			public String getCode() {
				return code;
			}
			
			
			public String getStatus() {
				return status;
			}

			public static BINANCE_ORDER_STATUS getByCode(String code) {
				BINANCE_ORDER_STATUS type = null;
				
				for (int i = 0; i < BINANCE_ORDER_STATUS.values().length; i++) {
					type = BINANCE_ORDER_STATUS.values()[i];
					if (type.getCode().equals(code)) {
						break;
					}
				}
				
				return type;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_ORDER_STATUS[] statuses = BINANCE_ORDER_STATUS.values();
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
		 * Order types:
		 */
		public static enum BINANCE_ORDER_TYPE {
			LIMIT("LIMIT"),
			MARKET("MARKET"),
			STOP_LOSS("STOP_LOSS"),//市价止损
			STOP_LOSS_LIMIT("STOP_LOSS_LIMIT"),//限价止损
			TAKE_PROFIT("TAKE_PROFIT"),//市价止盈
			TAKE_PROFIT_LIMIT("TAKE_PROFIT_LIMIT"),//限价止盈
			LIMIT_MAKER("LIMIT_MAKER");
			
			private final String code;
			
			private BINANCE_ORDER_TYPE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_ORDER_TYPE[] statuses = BINANCE_ORDER_TYPE.values();
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
		 * Order side:
		 */
		public static enum BINANCE_ORDER_SIDE {
			BUY("BUY"),
			SELL("SELL");
			
			private final String code;
			
			private BINANCE_ORDER_SIDE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_ORDER_SIDE[] statuses = BINANCE_ORDER_SIDE.values();
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
		 * Time in force:
		 */
		public static enum BINANCE_TIMEINFORCE {
			GTC("GTC"),//good till cancel
			IOC("IOC"),//immediately or cancel,
			FOK("FOK");//Fill Or Kill
			
			private final String code;
			
			private BINANCE_TIMEINFORCE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_TIMEINFORCE[] statuses = BINANCE_TIMEINFORCE.values();
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
		 * newOrderRespType	ENUM	NO	Set the response JSON. 
		 * ACK, RESULT, or FULL; MARKET and LIMIT order types default to FULL, all other orders default to ACK.
		 * @author Aaron
		 *
		 */
		public static enum BINANCE_RESP_TYPE {
			ACK("ACK"),
			RESULT("RESULT"),
			FULL("FULL");
			
			private final String code;
			
			private BINANCE_RESP_TYPE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_RESP_TYPE[] statuses = BINANCE_RESP_TYPE.values();
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
		 * 
		 * @author Aaron
		 *
		 */
		public static enum BINANCE_DEPOSIT_STATUS {
			pending("0"),
			success("1");
			
			private final String code;
			
			private BINANCE_DEPOSIT_STATUS(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_DEPOSIT_STATUS[] statuses = BINANCE_DEPOSIT_STATUS.values();
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
		 * 
		 * @author Aaron
		 *
		 */
		public static enum BINANCE_WITHDRAW_STATUS {
			EmailSent("0"),
			Cancelled("1"),
			AwaitingApproval("2"),
			Rejected("3"),
			Processing("4"),
			Failure("5"),
			Completed("6");
			
			private final String code;
			
			private BINANCE_WITHDRAW_STATUS(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				BINANCE_WITHDRAW_STATUS[] statuses = BINANCE_WITHDRAW_STATUS.values();
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
		
//		/**
//		 * Rate limiters (rateLimitType)
//		 */
//
//		REQUESTS_WEIGHT
//		ORDERS
//		
//		/**
//		 * Rate limit intervals
//		 */
//
//		SECOND
//		MINUTE
//		DAY
		
		
		/**
		 * type	String	是	买卖类型：限价单(buy/sell) 市价单(buy_market/sell_market)
		 * @author Aaron
		 *
		 */
		public static enum OKEX_ORDER_TYPE {
			buy("buy"),//限价单(buy) 
			sell("sell"),//限价单(sell) 
			buy_market("buy_market"),//市价单(buy_market)
			sell_market("sell_market");//市价单(sell_market)
			
			private final String code;
			
			private OKEX_ORDER_TYPE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static OKEX_ORDER_TYPE getByCode(String code) {
				OKEX_ORDER_TYPE type = null;
				
				for (int i = 0; i < OKEX_ORDER_TYPE.values().length; i++) {
					type = OKEX_ORDER_TYPE.values()[i];
					if (type.getCode().equals(code)) {
						break;
					}
				}
				
				return type;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				OKEX_ORDER_TYPE[] statuses = OKEX_ORDER_TYPE.values();
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
		 * status:-1:已撤销   0:未成交 1:部分成交 2:完全成交 4:撤单处理中
		 * /**
		 * Order status:
		 * UNMATCHED("未成交"), 
			MATCHED("全部成交"),
			CANCELED("已撤销"),
			CANCELING("撤销中"),
			PART_MATCHED("部分成交"),
			PART_CANCELED("部成部撤");
		 */
		public static enum OKEX_ORDER_STATUS {
			canceled("-1", "CANCELED"),//:已撤销   
			unmatched("0", "UNMATCHED"),//:未成交 
			part_matched("1", "PART_MATCHED"),//:部分成交 
			matched("2", "MATCHED"),//:完全成交 
			canceling("4", "CANCELING");//:撤单处理中
			
			private final String code;
			private final String status;
			
			private OKEX_ORDER_STATUS(String code, String status) {
				this.code = code;
				this.status = status;
			}
			public String getCode() {
				return code;
			}
			public String getStatus() {
				return status;
			}
			
			public static OKEX_ORDER_STATUS getByCode(String code) {
				OKEX_ORDER_STATUS type = null;
				
				for (int i = 0; i < OKEX_ORDER_STATUS.values().length; i++) {
					type = OKEX_ORDER_STATUS.values()[i];
					if (type.getCode().equals(code)) {
						break;
					}
				}
				
				return type;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				OKEX_ORDER_STATUS[] statuses = OKEX_ORDER_STATUS.values();
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
		 * 查询状态 0：未完成的订单 1：已经完成的订单(最近两天的数据)
		 */
		public static enum OKEX_COMMON_STATUS {
			processing("0"),//：未完成的订单 
			done("1");//：已经完成的订单(最近两天的数据)
			
			private final String code;
			
			private OKEX_COMMON_STATUS(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				OKEX_COMMON_STATUS[] statuses = OKEX_COMMON_STATUS.values();
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
		 * not_deal: unexecuted;
			part_deal: partly executed;
			done: executed;
			To guarantee server performance, all cancelled unexecuted orders will be deleted.
			UNMATCHED("未成交"), 
			MATCHED("全部成交"),
			CANCELED("已撤销"),
			CANCELING("撤销中"),
			PART_MATCHED("部分成交"),
			PART_CANCELED("部成部撤");
		 */
		public static enum COINEX_ORDER_STATUS {
			not_deal("not_deal", "UNMATCHED"),
			part_deal("part_deal", "PART_MATCHED"),
			pardonet_deal("done", "MATCHED");
			
			private final String code, status;
			
			private COINEX_ORDER_STATUS(String code, String status) {
				this.code = code;
				this.status = status;
			}
			public String getCode() {
				return code;
			}
			
			
			public String getStatus() {
				return status;
			}
			public static COINEX_ORDER_STATUS getByCode(String code) {
				COINEX_ORDER_STATUS type = null;
				
				for (int i = 0; i < COINEX_ORDER_STATUS.values().length; i++) {
					type = COINEX_ORDER_STATUS.values()[i];
					if (type.getCode().equals(code)) {
						break;
					}
				}
				
				return type;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				COINEX_ORDER_STATUS[] statuses = COINEX_ORDER_STATUS.values();
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
		
		
		//status:提现状态（-3:撤销中;-2:已撤销;-1:失败;0:等待提现;1:提现中;2:已汇出;3:邮箱确认;4:人工审核中5:等待身份认证）
		//充值记录:(-1:充值失败;0:等待确认;1:充值成功)
		
		/**
		 * 
		 * @author Aaron
		 *
		 */
		public static enum COINEX_ORDER_TYPE {
			limit("limit"),//限价单
			market("market"),//市价单
			ioc("ioc");//全成或全撤
			
			private final String code;
			
			private COINEX_ORDER_TYPE(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				COINEX_ORDER_TYPE[] statuses = COINEX_ORDER_TYPE.values();
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
		 * 
		 * @author Aaron
		 *
		 */
		public static enum COINEX_ORDER_DIRECTION {
			sell("sell"),//: sell order;
			buy("buy");//: buy order;
			
			private final String code;
			
			private COINEX_ORDER_DIRECTION(String code) {
				this.code = code;
			}
			public String getCode() {
				return code;
			}
			
			public static List<HashMap<String, String>> getKVPair() {
				COINEX_ORDER_DIRECTION[] statuses = COINEX_ORDER_DIRECTION.values();
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
		
		public static final String PATH_PARAM_PREFIX = "path$";
		
		public static Map<String, String> oKexErrorMap = null;
	    static {
	    	oKexErrorMap = new HashMap<String, String>();
			oKexErrorMap.put("10000",	"必选参数不能为空");
			{oKexErrorMap.put("10001"	,	"用户请求频率过快，超过该接口允许的限额");}
			{oKexErrorMap.put("10002"	,	"系统错误");}
			{oKexErrorMap.put("10004"	,	"请求失败");}
			{oKexErrorMap.put("10005"	,	"SecretKey不存在");}
			{oKexErrorMap.put("10006"	,	"Api_key不存在");}
			{oKexErrorMap.put("10007"	,	"签名不匹配");}
			{oKexErrorMap.put("10008"	,	"非法参数");}
			{oKexErrorMap.put("10009"	,	"订单不存在");}
			{oKexErrorMap.put("10010"	,	"余额不足");}
			{oKexErrorMap.put("10011"	,	"买卖的数量小于BTC/LTC最小买卖额度");}
			{oKexErrorMap.put("10012"	,	"当前网站暂时只支持btc_usd ltc_usd");}
			{oKexErrorMap.put("10013"	,	"此接口只支持https请求");}
			{oKexErrorMap.put("10014"	,	"下单价格不得≤0或≥");}
			{oKexErrorMap.put("10015",	"下单价格与最新成交价偏差过大");}
			{oKexErrorMap.put("10016",	"币数量不足");}
			{oKexErrorMap.put("10017",	"API鉴权失败");}
			{oKexErrorMap.put("10018",	"借入不能小于最低限额[USD:100,BTC:0.1,LTC:1]");}
			{oKexErrorMap.put("10019",	"页面没有同意借贷协议");}
			{oKexErrorMap.put("10020",	"费率不能大于1%");}
			{oKexErrorMap.put("10021",	"费率不能小于0.01%");}
			{oKexErrorMap.put("10023",	"获取最新成交价错误");}
			{oKexErrorMap.put("10024",	"可借金额不足");}
			{oKexErrorMap.put("10025",	"额度已满，暂时无法借款");}
			{oKexErrorMap.put("10026",	"借款(含预约借款)及保证金部分不能提出");}
			{oKexErrorMap.put("10027",	"修改敏感提币验证信息，24小时内不允许提现");}
			{oKexErrorMap.put("10028",	"提币金额已超过今日提币限额");}
			{oKexErrorMap.put("10029",	"账户有借款，请撤消借款或者还清借款后提币");}
			{oKexErrorMap.put("10031",	"存在BTC/LTC充值，该部分等值金额需6个网络确认后方能提出");}
			{oKexErrorMap.put("10032",	"未绑定手机或谷歌验证");}
			{oKexErrorMap.put("10033",	"服务费大于最大网络手续费");}
			{oKexErrorMap.put("10034",	"服务费小于最低网络手续费");}
			{oKexErrorMap.put("10035",	"可用BTC/LTC不足");}
			{oKexErrorMap.put("10036",	"提币数量小于最小提币数量");}
			{oKexErrorMap.put("10037",	"交易密码未设置");}
			{oKexErrorMap.put("10040",	"取消提币失败");}
			{oKexErrorMap.put("10041",	"提币地址不存在或未认证");}
			{oKexErrorMap.put("10042",	"交易密码错误");}
			{oKexErrorMap.put("10043",	"合约权益错误，提币失败");}
			{oKexErrorMap.put("10044",	"取消借款失败");}
			{oKexErrorMap.put("10047",	"当前为子账户，此功能未开放");}
			{oKexErrorMap.put("10048",	"提币信息不存在");}
			{oKexErrorMap.put("10049",	"小额委托(<0.15BTC)的未成交委托数量不得大于50个");}
			{oKexErrorMap.put("10050",	"重复撤单");}
			{oKexErrorMap.put("10052",	"提币受限");}
			{oKexErrorMap.put("10056",	"划转受限");}
			{oKexErrorMap.put("10057",	"划转失败");}
			{oKexErrorMap.put("10058",	"NEO只能提整数");}
			{oKexErrorMap.put("10064",	"美元充值后的48小时内，该部分资产不能提出");}
			{oKexErrorMap.put("10100",	"账户被冻结");}
			{oKexErrorMap.put("10101",	"订单类型错误");}
			{oKexErrorMap.put("10102",	"不是本用户的订单");}
			{oKexErrorMap.put("10103",	"私密订单密钥错误");}
			{oKexErrorMap.put("10106",	"apiKey所属域名不匹配");}
			{oKexErrorMap.put("10216",	"非开放API");}
			{oKexErrorMap.put("1002",	"交易金额大于余额");}
			{oKexErrorMap.put("1003",	"交易金额小于最小交易值");}
			{oKexErrorMap.put("1004",	"交易金额小于0");}
			{oKexErrorMap.put("1007",	"没有交易市场信息");}
			{oKexErrorMap.put("1008",	"没有最新行情信息");}
			{oKexErrorMap.put("1009",	"没有订单");}
			{oKexErrorMap.put("1010",	"撤销订单与原订单用户不一致");}
			{oKexErrorMap.put("1011",	"没有查询到该用户");}
			{oKexErrorMap.put("1013",	"没有订单类型");}
			{oKexErrorMap.put("1014",	"没有登录");}
			{oKexErrorMap.put("1015",	"没有获取到行情深度信息");}
			{oKexErrorMap.put("1017",	"日期参数错误");}
			{oKexErrorMap.put("1018",	"下单失败");}
			{oKexErrorMap.put("1019",	"撤销订单失败");}
			{oKexErrorMap.put("1024",	"币种不存在");}
			{oKexErrorMap.put("1025",	"没有K线类型");}
			{oKexErrorMap.put("1026",	"没有基准币数量");}
			{oKexErrorMap.put("1027",	"参数不合法可能超出限制");}
			{oKexErrorMap.put("1028",	"保留小数位失败");}
			{oKexErrorMap.put("1029",	"正在准备中");}
			{oKexErrorMap.put("1030",	"有融资融币无法进行交易");}
			{oKexErrorMap.put("1031",	"转账余额不足");}
			{oKexErrorMap.put("1032",	"该币种不能转账");}
			{oKexErrorMap.put("1035",	"密码不合法");}
			{oKexErrorMap.put("1036",	"谷歌验证码不合法");}
			{oKexErrorMap.put("1037",	"谷歌验证码不正确");}
			{oKexErrorMap.put("1038",	"谷歌验证码重复使用");}
			{oKexErrorMap.put("1039",	"短信验证码输错限制");}
			{oKexErrorMap.put("1040",	"短信验证码不合法");}
			{oKexErrorMap.put("1041",	"短信验证码不正确");}
			{oKexErrorMap.put("1042",	"谷歌验证码输错限制");}
			{oKexErrorMap.put("1043",	"登陆密码不允许与交易密码一致");}
			{oKexErrorMap.put("1044",	"原密码错误");}
			{oKexErrorMap.put("1045",	"未设置二次验证");}
			{oKexErrorMap.put("1046",	"原密码未输入");}
			{oKexErrorMap.put("1048",	"用户被冻结");}
			{oKexErrorMap.put("1050",	"订单已撤销或者撤单中");}
			{oKexErrorMap.put("1051",	"订单已完成交易");}
			{oKexErrorMap.put("1056",	"划转受限");}
			{oKexErrorMap.put("1057",	"划转失败");}
			{oKexErrorMap.put("1058",	"NEO只能提整数");}
			{oKexErrorMap.put("1201",	"账号零时删除");}
			{oKexErrorMap.put("1202",	"账号不存在");}
			{oKexErrorMap.put("1203",	"转账金额大于余额");}
			{oKexErrorMap.put("1204",	"不同种币种不能转账");}
			{oKexErrorMap.put("1205",	"账号不存在主从关系");}
			{oKexErrorMap.put("1206",	"提现用户被冻结");}
			{oKexErrorMap.put("1207",	"不支持转账");}
			{oKexErrorMap.put("1208",	"没有该转账用户");}
			{oKexErrorMap.put("1209",	"当前api不可用");}
			{oKexErrorMap.put("1216",	"市价交易暂停，请选择限价交易");}
			{oKexErrorMap.put("1217",	"您的委托价格超过最新成交价的±5%，存在风险，请重新下单");}
			{oKexErrorMap.put("1218",	"下单失败，请稍后再试");}
			{oKexErrorMap.put("HTTP错误码403",	"用户请求过快，IP被屏蔽");}
			{oKexErrorMap.put("Ping不通",	"用户请求过快，IP被屏蔽");}
		};
}
