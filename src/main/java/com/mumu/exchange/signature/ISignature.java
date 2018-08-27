package com.mumu.exchange.signature;

import java.util.Map;

public interface ISignature {

	public ISignature setApiRoot(String apiRoot);
	
	public ISignature setApiUri(String uri);
	
	public ISignature putParam(String key, String value);
	
	public ISignature addAllParams(Map<String, String> params);
	
	/**
	 * 清空参数后，在保证线程安全的情况下，可以使用同一SPI实例请求其它接口
	 * @return
	 */
	public ISignature clearParams();
	
	public String sign(String accessKey, String secretkey);
	
	public String getUri(String method, String charset);
	
	public Map<String, String> getParams();
	
	
}
