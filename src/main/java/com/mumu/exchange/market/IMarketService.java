package com.mumu.exchange.market;

import java.util.List;

import com.mumu.beans.DealtInfo;
import com.mumu.beans.Market;

public interface IMarketService {
	
//	public Map<String, Object> quotationTicker(String accessKey, String  secretkey, String ... symblos);
	
	public List<Market> quotationTicker(String accessKey, String  secretkey, String ... symbols);
	
	public DealtInfo getSettlementPrice(String accessKey, String  secretkey, Integer size, String ... symbols);
}
