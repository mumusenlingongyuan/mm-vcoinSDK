import com.mumu.exchange.MarketServiceFactory;

public class MarketTest {
	
	public static void main(String[] args) {
		String accessKey1 = "";
		String secretkey1 = "";
//		MarketServiceFactory.getInstance(Constants.EXCHANGE_HOUBI).quotationTicker(accessKey1, secretkey1, "bchbtc");
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_HOUBI).getSettlementPrice(accessKey1, secretkey1, 60, "bchbtc");
//		
		String accessKey2 = "";
		String secretkey2 = "";
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX).quotationTicker(accessKey2, secretkey2, "bch_btc");
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_OKEX).getSettlementPrice(accessKey2, secretkey2, 60, "bch_btc");
//		
		String accessKey3 = "";
		String secretkey3 = "";
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX).quotationTicker(accessKey3, secretkey3, "BTCBCH");//BTCBCH
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_COINEX).getSettlementPrice(accessKey3, secretkey3, 60, "BTCBCH");
		
		
		String accessKey4 = "";
		String secretkey4 = "";
		MarketServiceFactory.getInstance(com.mumu.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE).quotationTicker(accessKey4, secretkey4, "BTCUSDT");// "BNBBTC,LTCBTC"
//		MarketServiceFactory.getInstance(com.xinqidian.business.exchange.common.Constants.EXCHANGE_NAME.EXCHANGE_BINANCE).getSettlementPrice(accessKey4, secretkey4, 60, "BTCUSDT");
	}
	
	
}
