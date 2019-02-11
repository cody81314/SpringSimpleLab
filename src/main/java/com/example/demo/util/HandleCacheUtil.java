package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import com.example.demo.dto.BookDTO;

@SuppressWarnings("unchecked")
@Component
public class HandleCacheUtil {

	TransformDateUtil transformDate = new TransformDateUtil();

	public void queryAll4SingleRead(Cache bookCache, List<BookDTO> listBook) {

		for (BookDTO bDTO : listBook) {
			bookCache.put(bDTO.getName(), bDTO);
		}
	}

	public void deleteFromQueryAll(Cache bookCache, String dataId) {
		ArrayList<BookDTO> alist = (ArrayList<BookDTO>) bookCache.get("queryall").get();
		for (int i = 0; i < alist.size(); i++) {
			String name = alist.get(i).getName();
			if (dataId.equals(name)) {
				alist.remove(alist.get(i));
				break;
			}
		}
		bookCache.put("queryall", alist);
	}

	public void update2QueryAll(Cache bookCache, BookDTO bookDTO) {
		ArrayList<BookDTO> alist = (ArrayList<BookDTO>) bookCache.get("queryall").get();
		for (int i = 0; i < alist.size(); i++) {
			String name = alist.get(i).getName();
			if (bookDTO.getName().equals(name)) {
				alist.get(i).setAuthor(bookDTO.getAuthor());
				alist.get(i).setPublicationDate(transformDate.sDate2SFormatDate(bookDTO.getPublicationDate()));
				break;
			}
		}
		bookCache.put("queryall", alist);
	}
	
	public void add2QueryAll(Cache bookCache, BookDTO bookDTO) {
		ArrayList<BookDTO> alist = (ArrayList<BookDTO>) bookCache.get("queryall").get();
		for (int i = 0; i < alist.size(); i++) {
			String name = alist.get(i).getName();
			if (bookDTO.getName().equals(name)) {
				alist.get(i).setAuthor(bookDTO.getAuthor());
				alist.get(i).setPublicationDate(transformDate.sDate2SFormatDate(bookDTO.getPublicationDate()));
				break;
			}else if(i == (alist.size()-1)) {
				alist.add(alist.size(),bookDTO);
			}
		}
		bookCache.put("queryall", alist);
	}

}
