package com.mumu.exchange.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class JacksonHelper {

	private static final ObjectMapper objectMapper = createObjectMapper();
	public static final JavaType mapType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);
	
    public static String writeValueAsString(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T readValue(String s, TypeReference<T> ref) throws IOException {
        return objectMapper.readValue(s, ref);
    }

    public static ObjectMapper getMapper() {
    	return objectMapper;
    }
    

    static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // disabled features:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
    
    public static Map<String, Object> getJsonMap(String jsonStr) {
    	Map<String, Object> jsonMap = null;
		try {
			jsonMap = (Map<String, Object>) objectMapper.readValue(jsonStr, JacksonHelper.mapType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return jsonMap;
    }

}
