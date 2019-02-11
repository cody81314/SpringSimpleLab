package com.example.demo.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class TransformDateUtil {
	
	public  Timestamp string2Timestamp(String sDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime lDateTime = LocalDate.parse(sDate, formatter).atStartOfDay();
		Timestamp timestamp = Timestamp.valueOf(lDateTime);		
		return timestamp;
	}
	
	public LocalDateTime string2LocalDateTime(String sDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");	
		return LocalDate.parse(sDate, formatter).atStartOfDay();
	}
	
	public String localDateTime2String(LocalDateTime lDate) {		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String stringDate = lDate.format(formatter);
		return stringDate;
	}
	
	public String sDate2SFormatDate(String sDate) {	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime ldate= LocalDate.parse(sDate, formatter).atStartOfDay();
		return localDateTime2String(ldate);
	}
	
}
