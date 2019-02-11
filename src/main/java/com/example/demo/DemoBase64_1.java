package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.Base64Util;


public class DemoBase64_1 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		Logger LOGGER = LoggerFactory.getLogger(DemoBase64_1.class);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("請輸入要編碼的字串：");
		String str1 = scanner.next();
		
		
		/* 實作一個方法將一個字串做Base64編碼 */
		String encodedText = Base64Util.stringToBase64String(str1);

		LOGGER.info("Base64 encode 輸出:   {} ", encodedText);
		

	}
	
	

}
