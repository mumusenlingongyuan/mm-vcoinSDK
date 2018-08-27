package com.mumu.exchange.signature;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.mumu.exchange.api.BinanceAPI;
import com.mumu.exchange.common.Constants;

public final class BinanceParamsSigner extends RequestParamsSigner {
	
	public static final String charset_utf8 = "UTF-8";
	
	public BinanceParamsSigner() {
	}

	@Override
	public String sign(String accessKey, String secretkey) {
		TreeMap<String, String> signMap =new TreeMap<String, String>(new Comparator<String>() {
	        @Override
	        public int compare(String o1, String o2) {
	        	return o1.compareTo(o2);
	        }
	    });
		
		signMap.put(BinanceAPI.API_SIGN_KEY_recvWindow, BinanceAPI.API_SIGN_DEFVAL_recvWindow);
		signMap.put(BinanceAPI.API_SIGN_KEY_Timestamp, System.currentTimeMillis()+"");
		super.paramsMap.putAll(signMap);
//		String method = getMethodByChangeoverUri(this.apiUri, super.getParams());
		StringBuilder sb = new StringBuilder();
		String queryStr = null;
		
//		if (org.springframework.http.HttpMethod.GET.name().equalsIgnoreCase(method)) {
//			queryStr = super.generateQryStr(charset_utf8);
//			sb.append(queryStr);//.append("&"+ BinanceAPI.API_SIGN_KEY_Secret_key +"=").append(secretkey);
//			System.out.println("sign=" + sb.toString());
//			
//		} else {
			queryStr = super.generateQryStr(charset_utf8);//super.generateSignStr(signMap, charset_utf8);
			sb.append(queryStr);//.append("&"+ BinanceAPI.API_SIGN_KEY_Secret_key +"=").append(secretkey);
			System.out.println("sign=" + sb.toString());
//		}
		return hmacSHA256ToHex(sb.toString(), secretkey);
	}

	private String getMethodByChangeoverUri(String apiUri, Map<String, String> params) {
		String method = BinanceAPI.getMethodByApiUri(apiUri);
		String key = null;
		if (StringUtils.isBlank(method)) {
			for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
			  key = entry.getKey();
			  if (key.startsWith(Constants.PATH_PARAM_PREFIX)) {
				  apiUri = apiUri.replace(entry.getValue(), "{"+ key +"}");
			  }
			}
			method = BinanceAPI.getMethodByApiUri(apiUri);
		}
		
		return method;
	}
	
}
