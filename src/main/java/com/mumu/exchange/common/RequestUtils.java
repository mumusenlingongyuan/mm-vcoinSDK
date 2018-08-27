package com.mumu.exchange.common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class RequestUtils {

	
	/**
	 * 
	 * @param request
	 * @param params
	 * @param charset
	 */
	public static void addForm(Request request, Map<String, String> params, String charset) {
		Form form = Form.form();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			if (!entry.getKey().startsWith(Constants.PATH_PARAM_PREFIX)) {
				form.add(entry.getKey(), entry.getValue());
			}
		}
		
		request.bodyForm(form.build(), Charset.forName(charset));
	}
	
	public static void addJson(Request request, Map<String, String> params, String charset) throws UnsupportedCharsetException, IOException {
//        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry<String, String> entry = it.next();
//            if (entry.getKey().startsWith(Constants.PATH_PARAM_PREFIX)) {
//				it.remove();
//			}
//        }
		
		request.bodyString(JacksonHelper.writeValueAsString(params), ContentType.create("application/json", charset));
	}
	
	/**
	 * 
	 * @param request
	 */
	public static void setProxy(Request request) {
		HttpHost proxy = new HttpHost("localhost", 1080);
		request.viaProxy(proxy);
	}
}
