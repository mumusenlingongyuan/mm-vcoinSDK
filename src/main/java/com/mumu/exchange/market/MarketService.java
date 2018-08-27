package com.mumu.exchange.market;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.mumu.beans.DealtInfo;
import com.mumu.beans.Market;

public class MarketService implements IMarketService {
	protected final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<Market> quotationTicker(String accessKey, String secretkey, String... symblos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DealtInfo getSettlementPrice(String accessKey, String secretkey, Integer size, String... symbols) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public Map<String, Object> quotationTicker(String accessKey, String  secretkey, String... symblos) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	protected long getSplittingTime() {
		Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, -1);
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 999);
    	
    	return calendar.getTimeInMillis();
	}
}
