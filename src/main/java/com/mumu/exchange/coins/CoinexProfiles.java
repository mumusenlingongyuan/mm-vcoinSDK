package com.mumu.exchange.coins;

import org.apache.http.client.fluent.Request;

public class CoinexProfiles {

	/**
	 * All requests based on the Https protocol should set the request header information Content-Type as:'application/json’.
	 * Request header information must be declared: 
	 * User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36
	 * Request submission frequency: 20 times per second per IP (for Trading and Account API); no limit on Market API
	 * 
	 * @param request
	 */
	public static void addHeader(Request request, String method) {
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
		request.addHeader("Content-Type", "application/json");
	}
}
