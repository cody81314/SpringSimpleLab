package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.example.demo.util.Base64Util;


public class DemoBase64_2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		Logger LOGGER = LoggerFactory.getLogger(DemoBase64_2.class);
		
		Scanner scanner = new Scanner(System.in);		
		System.out.println("請輸入要編碼的字串：");		
		String str1 = scanner.next();
		
		final byte[] str1Byte = str1.getBytes();
		
		/* 實作一個方法將byte[]做Base64編碼  */
		String encodedText = Base64Util.bytesToBase64String(str1Byte);

		LOGGER.info("Base64 encode 輸出:   {} ", encodedText);

	}
	
	

}
