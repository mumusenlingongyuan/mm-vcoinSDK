package com.mumu.exchange.signature;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.mumu.exchange.api.HoubiAPI;
import com.mumu.exchange.common.Constants;

public final class HuobiParamsSigner extends RequestParamsSigner {
	
	public static final String charset_utf8 = "UTF-8";
	
	public HuobiParamsSigner() {
	}

	@Override
	public String sign(String accessKey, String secretkey) {
		TreeMap<String, String> signMap =new TreeMap<String, String>(new Comparator<String>() {
	        @Override
	        public int compare(String o1, String o2) {
	        	return o1.compareTo(o2);
	        }
	    });
		
		signMap.put(HoubiAPI.API_SIGN_KEY_AccessKeyId, accessKey);
		signMap.put(HoubiAPI.API_SIGN_KEY_SignatureMethod, HoubiAPI.API_SIGN_SignatureMethod);
		signMap.put(HoubiAPI.API_SIGN_KEY_SignatureVersion, HoubiAPI.API_SIGN_SignatureVersion);
		String timestamp = DateFormatUtils.formatUTC(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime(), "yyyy-MM-dd'T'HH:mm:ss");
		signMap.put(HoubiAPI.API_SIGN_KEY_Timestamp, timestamp);
		
//		org.apache.shiro.crypto.hash.Sha256Hash sha256 = null;
		String method = getMethodByChangeoverUri(this.apiUri, super.getParams());
		String sign_rest_trading_root = this.apiRoot.replaceFirst("^(https://|http://)", "");
		StringBuilder sb = new StringBuilder();
		String queryStr = null;
		
		if (org.springframework.http.HttpMethod.GET.name().equalsIgnoreCase(method)) {
			super.paramsMap.putAll(signMap);
			queryStr = super.generateQryStr(charset_utf8);
			sb.append(method).append("\n")
			.append(sign_rest_trading_root).append("\n")
			.append(this.apiUri).append("\n")
			.append(queryStr);
			System.out.println("sign=" + sb.toString());
			
//			sha256 = new Sha256Hash(sb.toString(), secretkey);
		} else {
			queryStr = super.generateSignStr(signMap, charset_utf8);
			sb.append(method).append("\n")
			.append(sign_rest_trading_root).append("\n")
			.append(this.apiUri).append("\n")
			.append(queryStr);
			System.out.println("sign=" + sb.toString());
//			sha256 = new Sha256Hash(sb.toString(), secretkey);
		}
		
		try {
			String signStr = super.hmacSHA256(sb.toString(), secretkey);
			sb = new StringBuilder();
			sb.append(this.apiRoot)
			.append(this.apiUri).append("?")
			.append(queryStr)
			.append("&Signature=").append(java.net.URLEncoder.encode(signStr, charset_utf8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	private String getMethodByChangeoverUri(String apiUri, Map<String, String> params) {
		String method = HoubiAPI.getMethodByApiUri(apiUri);
		String key = null;
		if (StringUtils.isBlank(method)) {
			for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
			  key = entry.getKey();
			  if (key.startsWith(Constants.PATH_PARAM_PREFIX)) {
				  apiUri = apiUri.replace(entry.getValue(), "{"+ key +"}");
			  }
			}
			method = HoubiAPI.getMethodByApiUri(apiUri);
		}
		
		return method;
	}
	
	

	public static void main(String[] args) {
		System.out.println("https://api.huobi.pro/v1".replaceFirst("^(https://|http://)", ""));
	}
}
