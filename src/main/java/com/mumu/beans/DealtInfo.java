package com.mumu.beans;

import com.mumu.common.BaseForm;
import com.mumu.common.Constants.TRADING_DIRECTION;

public class DealtInfo extends BaseForm {


	 /**
	 * 
	 */
	private static final long serialVersionUID = -7957675042135563397L;
	/**
	  * "id": 成交id,
	  */
	 private String id;
     /**
      * "price": 成交价,
      */
     private double price;
     /**
      * "amount": 成交量,
      */
     private double volume;
     /**
      * "direction": 主动成交方向,
      */
     private TRADING_DIRECTION direction;
     /**
      * "ts": 成交时间
      */
     private long ts;
     
     
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public TRADING_DIRECTION getDirection() {
		return direction;
	}
	public void setDirection(TRADING_DIRECTION direction) {
		this.direction = direction;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	
     
}
