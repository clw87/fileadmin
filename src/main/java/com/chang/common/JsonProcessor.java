package com.chang.common;


import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonProcessor {

	public static String bean2Json(Object obj, boolean isFormat) throws Exception {   
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        String json;
		try {
			if(isFormat){
				json = mapper.writeValueAsString(obj);//.writeValueAsString(obj);
			}else{
				//bean形成的String会带有换行等格式类空白字符，去除空白字符
				json = mapper.writeValueAsString(obj).replaceAll("\\s+", "");
			}
		} catch (JsonProcessingException e) {
			throw e;
		}
        return json;
    }
	
	
	public static Object json2Bean(String json, Class<?> cls) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
	    Object vo;
		try {
			vo = mapper.readValue(json, cls);
			return vo;
		} catch (JsonParseException e) {
			throw e;
		} catch (JsonMappingException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 如果是json数组，则返回json的字符串，
	 * 否则则返回null
	 * 
	 * @param json
	 * @return
	 */
	public static JSONArray isJsonArray(String json){
		if( null == json || json.trim().equals("") ){
			return null;
		}
		JSONArray array = null;
		try {
			array = new JSONArray(json);
			return array;
		} catch (Exception e) {
			json = "[" + json + "]";
			try {
				array = new JSONArray(json);
				return array;
			} catch (Exception e1) {
				return null;
			}
		}
	}
	
	/**
	 * 判断String是否是一个
	 * 合格的json字符串
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject isJsonObject(String json){
		if( null == json || json.trim().equals("") ){
			return null;
		}
		try{
			JSONObject obj = new JSONObject(json);
			return obj;
		} catch(Exception e){
			return null;
		}
	}
	
}
