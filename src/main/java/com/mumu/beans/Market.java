package com.mumu.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mumu.common.BaseForm;

public class Market extends BaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7592585925124568142L;
	private Calendar calendar = null;//Calendar.getInstance();
	private String date = null;
	private String time = null;

	/**
	 * 行情时间戳
	 */
	private long ts;
	/**
	 * 标的
	 */
	private String symbol;
	/**
	 * 开盘价
	 */
	private double open;
	/**
	 * 最新价
	 */
	private double last;
	/**
	 * 买价
	 */
	private double bid;
	/**
	 * 买量
	 */
	private double bidVolume;
	/**
	 * 卖价
	 */
	private double ask;
	/**
	 * 卖量
	 */
	private double askVolume;
	/**
	 * 最高价
	 */
	private double highest;
	/**
	 * 最低价
	 */
	private double lowest;
	/**
	 * 成交量
	 */
	private double volume;
	/**
	 * 成交额
	 */
	private double amount;
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		if (null == calendar) {
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(ts);
		}
		this.ts = ts;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getLast() {
		return last;
	}
	public void setLast(double last) {
		this.last = last;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getBidVolume() {
		return bidVolume;
	}
	public void setBidVolume(double bidVolume) {
		this.bidVolume = bidVolume;
	}
	public double getAsk() {
		return ask;
	}
	public void setAsk(double ask) {
		this.ask = ask;
	}
	public double getAskVolume() {
		return askVolume;
	}
	public void setAskVolume(double askVolume) {
		this.askVolume = askVolume;
	}
	public double getHighest() {
		return highest;
	}
	public void setHighest(double highest) {
		this.highest = highest;
	}
	public double getLowest() {
		return lowest;
	}
	public void setLowest(double lowest) {
		this.lowest = lowest;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getDate() {
		if (null == date) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = dateFormat.format(this.calendar.getTime());
		}
		
		return date;
	}
	
	public String getTime() {
		if (null == time) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			time = dateFormat.format(this.calendar.getTime());
		}
		
		return time;
	}
}
