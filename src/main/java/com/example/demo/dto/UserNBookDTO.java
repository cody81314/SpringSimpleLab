package com.example.demo.dto;

import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.util.TransformDateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Data Transfer Object
 *
 * 數據傳輸物件主要用於遠程調用等需要大量傳輸物件的地方。
 * 比如我們一張表有100個欄位，那麼對應的 PO 就有100個屬性。
 * 但是我們畫面上只要顯示其中的10個欄位，客戶端用 WEB service 來獲取數據，沒有必要把整個 PO 物件傳遞到客戶端，
 * 這時我們就可以用只有這10個屬性的 DTO 來傳遞結果到客戶端，這樣也不會暴露服務端表結構。
 */
@Data
public class UserNBookDTO {

    private String name;
    private String author;
    private String publicationDate;   
    
    private String userCode;
    private String pcode;

    public static UserNBookDTO createByBookPO(BookPO po) {
    	TransformDateUtil transformDate = new TransformDateUtil();
        UserNBookDTO dto = new UserNBookDTO();
        BeanUtils.copyProperties(po, dto);
        dto.setPublicationDate(transformDate.localDateTime2String(po.getPublicationDate()));
        return dto;
    }

    @JsonIgnore
    public BookPO getBookPO() {
        BookPO bpo = new BookPO();
        BeanUtils.copyProperties(this, bpo);

        return bpo;
    }
    
    @JsonIgnore
    public UserPO getUserPO() {
        UserPO upo = new UserPO();
        BeanUtils.copyProperties(this, upo);

        return upo;
    }
    
    
    
    
}
