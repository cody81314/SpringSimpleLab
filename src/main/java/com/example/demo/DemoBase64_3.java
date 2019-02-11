package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.Base64Util;


public class DemoBase64_3 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnsupportedEncodingException {
		

		Logger LOGGER = LoggerFactory.getLogger(DemoBase64_3.class);
		
		
		Base64.Encoder  encode  = Base64.getEncoder();
		
		
		
		Scanner scanner = new Scanner(System.in);
		//QmVhcjEyNA==
		
		System.out.println("請輸入要編碼的字串：");
		
		String str1 = scanner.next();
		String encodedText = encode.encodeToString(str1.getBytes());		
		
		LOGGER.info("Base64 encode 輸出:   {} ", encodedText);
		
		/* 實作一個方法將Base64字串做Base64解碼，並印出byte[]的長度 */
		Base64Util.decodeBase64String(encodedText);

	}
	
	

}
