package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class DemoBase64Service {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
	
	Base64.Encoder  encode  = Base64.getEncoder();
	Base64.Decoder  decode = Base64.getDecoder();
	HashMap<String, Object> map = new HashMap<String, Object>();
	

    public Map<String, Object> encodeStringByBase64(String text) throws Exception { 	
    	byte[] textByte = text.getBytes("UTF-8");
    	map.clear();
    	
    	map.put("encodeStringBybase64 : ", encode.encodeToString(text.getBytes("UTF-8")));
    	
    	LOGGER.info("Excute Method:{}  textByte : {}", Thread.currentThread().getStackTrace()[1].getMethodName(),textByte);
    	
        return map;
    }
    
    public Map<String, Object> encodeByteByBase64(byte[] bytetxt) throws Exception {
    	map.clear();
    	//byte buffer[] = getRequestPostBytes();
    	
    	//map.put("encodeByteByBase64 : ", encode.encodeToString(bytetxt));
        return map;
    }
    
    public Map<String, Object> decodeStringByBase64(String stringBase64) throws Exception {
    	map.clear();
    	map.put("decodeByBase64 : ", new String(decode.decode(stringBase64), "UTF-8"));
    	return map;
    }
    
}