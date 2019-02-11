package com.example.demo.error;

import org.springframework.stereotype.Component;

@Component
public class DemoMessageInfo {

	private StringBuilder builder = new StringBuilder();

	private final static String string1 = "查無書名為: ";
	private final static String string2 = " 該本書";
	private final static String string3 = ",無法";
	
	private final static String string4 = "查無作者: ";
	private final static String string5 = "和出版日期： ";
	private final static String string6 = "的所有相關書籍 ";
	private final static String stringM = "修改";

	private void msg4Init() {
		builder.delete(0, builder.length());
	}

	private String msg4Common(String name) {
		builder.append(string1);
		builder.append(name);
		builder.append(string2);
		return builder.toString();
	}

	public String msg4Query(String name) {
		msg4Init();
		return msg4Common(name);
	}
	
	public String msg4QueryAuthor(String author) {
		msg4Init();
		builder.append(string4);
		builder.append(author);
		builder.append(string6);
		return builder.toString();
	}
	
	public String msg4QueryAnP(String author,String publicationDate) {
		msg4Init();
		builder.append(string4);
		builder.append(author);
		builder.append(string5);
		builder.append(publicationDate);
		builder.append(string2);
		return builder.toString();
	}

	public String msg4Modify(String name) {
		msg4Init();
		msg4Common(name);
		builder.append(string3);
		builder.append(stringM);
		return builder.toString();
	}

}
