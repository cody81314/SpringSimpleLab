package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Base64Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Util.class);
    
    static Base64.Encoder  encode  = Base64.getEncoder();
    static Base64.Decoder  decode = Base64.getDecoder();
	


    public static String stringToBase64String(String inputString) throws UnsupportedEncodingException {
		//編碼
		String encodedText = encode.encodeToString(inputString.getBytes());		

		return encodedText;
	}
    
    public static String bytesToBase64String(byte[] bytes) throws UnsupportedEncodingException {
    	String encodedText = encode.encodeToString(bytes);
		
		return encodedText;
	}
    
    public static void decodeBase64String(String base64String) throws UnsupportedEncodingException {
    	String decodeText = new String(decode.decode(base64String));
    	LOGGER.info("Base64 decode 輸出:   {}",decodeText);
    	LOGGER.info("byte[] array: {}", decodeText.getBytes());
    	LOGGER.info("byte[] array length: {}",decodeText.getBytes().length);
	}

   
}
