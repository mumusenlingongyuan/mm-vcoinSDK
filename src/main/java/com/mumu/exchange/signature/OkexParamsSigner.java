package com.mumu.exchange.signature;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.mumu.exchange.api.OkexAPI;
import com.mumu.exchange.common.Constants;

public final class OkexParamsSigner extends RequestParamsSigner {
	
	public static final String charset_utf8 = "UTF-8";
	
	public OkexParamsSigner() {
	}

	@Override
	public String sign(String accessKey, String secretkey) {
		TreeMap<String, String> signMap =new TreeMap<String, String>(new Comparator<String>() {
	        @Override
	        public int compare(String o1, String o2) {
	        	return o1.compareTo(o2);
	        }
	    });
		
		signMap.put(OkexAPI.API_SIGN_KEY_AccessKeyId, accessKey);
		super.paramsMap.putAll(signMap);
		String method = getMethodByChangeoverUri(this.apiUri, super.getParams());
		StringBuilder sb = new StringBuilder();
		String queryStr = null;
		
		if (org.springframework.http.HttpMethod.GET.name().equalsIgnoreCase(method)) {
			queryStr = super.generateQryStr(charset_utf8);
			sb.append(queryStr).append("&"+ OkexAPI.API_SIGN_KEY_Secret_key +"=").append(secretkey);
			System.out.println("sign=" + sb.toString());
			
		} else {
			queryStr = super.generateQryStr(charset_utf8);//super.generateSignStr(signMap, charset_utf8);
			sb.append(queryStr).append("&"+ OkexAPI.API_SIGN_KEY_Secret_key +"=").append(secretkey);
			System.out.println("sign=" + sb.toString());
		}
		org.apache.shiro.crypto.hash.Md5Hash md5Hash = new Md5Hash(sb.toString());
		return md5Hash.toHex().toUpperCase();
		
//		return getMD5String(sb.toString());
	}

	private String getMethodByChangeoverUri(String apiUri, Map<String, String> params) {
		String method = OkexAPI.getMethodByApiUri(apiUri);
		String key = null;
		if (StringUtils.isBlank(method)) {
			for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
			  key = entry.getKey();
			  if (key.startsWith(Constants.PATH_PARAM_PREFIX)) {
				  apiUri = apiUri.replace(entry.getValue(), "{"+ key +"}");
			  }
			}
			method = OkexAPI.getMethodByApiUri(apiUri);
		}
		
		return method;
	}
	
//	/**
//	 * 生成32位大写MD5值
//	 */
//	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
//			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
//
//	public static String getMD5String(String str) {
//		try {
//			if (str == null || str.trim().length() == 0) {
//				return "";
//			}
//			byte[] bytes = str.getBytes();
//			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//			messageDigest.update(bytes);
//			bytes = messageDigest.digest();
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
//						+ HEX_DIGITS[bytes[i] & 0xf]);
//			}
//			return sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
}
