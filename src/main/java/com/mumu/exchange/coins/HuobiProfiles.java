package com.mumu.exchange.coins;

import org.apache.http.client.fluent.Request;
public class HuobiProfiles {

	/**
	 * POST请求头信息中必须声明 Content-Type:application/json;
	 * GET请求头信息中必须声明 Content-Type:application/x-www-form-urlencoded。
	 * (汉语用户建议设置 Accept-Language:zh-cn)
	 * 
	 * 请务必在header中设置user agent为 'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36'
	 * 
	 * Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36
		Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 《-
		Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0
	 * 
	 * @param request
	 */
	public static void addHeader(Request request, String method) {
		request.addHeader("Accept-Language", "zh-cn");
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
		if (org.springframework.http.HttpMethod.GET.name().equalsIgnoreCase(method)) {
			request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		} else {
			request.addHeader("Content-Type", "application/json");
		}
	}
}
