package com.mumu.exchange.signature;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.mumu.exchange.common.Constants;

abstract class RequestParamsSigner implements ISignature {
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	protected String apiRoot = null;
	protected String apiUri = null;
	private Map<String, Mac> macCacheMap = new HashMap<String, Mac>();
	
	protected TreeMap<String, String> paramsMap =new TreeMap<String, String>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
        	return o1.compareTo(o2);
        }
    });
	
	@Override
	public ISignature setApiRoot(String apiRoot) {
		this.apiRoot = apiRoot;
		return this;
	}
	
	@Override
	public ISignature setApiUri(String uri) {
		this.apiUri = uri;
		return this;
	}
	
	@Override
	public ISignature putParam(String key, String value) {
		this.paramsMap.put(key, value);
		return this;
	}
	
	/**
	 * 
	 * @param charset
	 * @return
	 */
	protected String generateQryStr(String charset) {
		StringBuilder sb = new StringBuilder();
		String key = null;
		try {
//			for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
//			  key = entry.getKey();
//			  if (!key.startsWith(Constants.PATH_PARAM_PREFIX)) {
//				  sb.append(key).append("=").append(java.net.URLEncoder.encode(entry.getValue(), charset)).append("&");
//			  }
//			}
//			
			Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
	        while(it.hasNext()){
	            Map.Entry<String, String> entry = it.next();
	            
	            key = entry.getKey();
				  if (!key.startsWith(Constants.PATH_PARAM_PREFIX)) {
					  sb.append(key).append("=").append(java.net.URLEncoder.encode(entry.getValue(), charset)).append("&");
				  } else {
					  it.remove();
				  }
	        }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (0 < sb.length()) {
			sb.replace(sb.length() - 1, sb.length(), ""); 
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param signMap
	 * @param charset
	 * @return
	 */
	protected String generateSignStr(Map<String, String> signMap, String charset) {
		StringBuilder sb = new StringBuilder();
		String key = null;
		try {
			for(Map.Entry<String, String> entry : signMap.entrySet()) {
			  key = entry.getKey();
			  
			  sb.append(key).append("=").append(java.net.URLEncoder.encode(entry.getValue(), charset)).append("&");
			}
			
			Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
	        while(it.hasNext()){
	            Map.Entry<String, String> entry = it.next();
	            
	            key = entry.getKey();
				  if (key.startsWith(Constants.PATH_PARAM_PREFIX)) {
					  it.remove();
				  }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} 
		sb.replace(sb.length() - 1, sb.length(), "");
		return sb.toString();
	}

	@Override
	public Map<String, String> getParams() {
		return Collections.unmodifiableMap(this.paramsMap);
	}
	
	protected String hmacSHA256(String message, String secretkey) {
		String hash = null;
		 try {
			 Mac sha256_HMAC = this.macCacheMap.get(secretkey);
			 if (null == sha256_HMAC) {
			     sha256_HMAC = Mac.getInstance("HmacSHA256");
			     SecretKeySpec secret_key = new SecretKeySpec(secretkey.getBytes(), "HmacSHA256");
			     sha256_HMAC.init(secret_key);
			     
			     macCacheMap.put(secretkey, sha256_HMAC);
			 }

		     hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
		     System.out.println(hash);
		     
	    } catch (Exception e){
	    	logger.error("HmacSHA256:", e);
	    }
		 
		return hash;
	}
	
	protected String hmacSHA256ToHex(String message, String secretkey) {
		String hash = null;
		 try {
			 Mac sha256_HMAC = this.macCacheMap.get(secretkey);
			 if (null == sha256_HMAC) {
			     sha256_HMAC = Mac.getInstance("HmacSHA256");
			     SecretKeySpec secret_key = new SecretKeySpec(secretkey.getBytes(), "HmacSHA256");
			     sha256_HMAC.init(secret_key);
			     
			     macCacheMap.put(secretkey, sha256_HMAC);
			 }
		     hash = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes()));
		     System.out.println(hash);
		     
	    } catch (Exception e){
	    	logger.error("HmacSHA256:", e);
	    }
		 
		return hash;
	}
	
	@Override
	public ISignature addAllParams(Map<String, String> params) {
		this.paramsMap.putAll(params);
		return this;
	}

	@Override
	public ISignature clearParams() {
		this.paramsMap.clear();
		return this;
	}

	@Override
	public String getUri(String method, String charset) {
		if (org.springframework.http.HttpMethod.POST.name().equalsIgnoreCase(method)) {
			StringBuilder uri = new StringBuilder(this.apiRoot);
			uri.append(this.apiUri);
			return uri.toString();
		} else {
			StringBuilder uri = new StringBuilder(this.apiRoot);
			uri.append(this.apiUri).append("?").append(generateQryStr(charset));
			return uri.toString();
			
		}
		
	}

	@Override
	public String sign(String accessKey, String secretkey) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
