package com.example.demo.dto;

import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.util.TransformDateUtil;
import com.example.demo.validator.annotation.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import static com.example.demo.constant.VerifyRegexConst.PATTERN_NAME;
import static com.example.demo.constant.VerifyRegexConst.PATTERN_AUTHOR;
import static com.example.demo.constant.VerifyRegexConst.PATTERN_PUBLICATIONDATE;

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
public class BookDTO {

	
	@Validator(pattern = PATTERN_NAME)
    private String name;
	@Validator(pattern = PATTERN_AUTHOR)
    private String author;
	@Validator(pattern = PATTERN_PUBLICATIONDATE)
    private String publicationDate;    

    public static BookDTO createByBookPO(BookPO po) {
    	TransformDateUtil transformDate = new TransformDateUtil();
        BookDTO dto = new BookDTO();
        BeanUtils.copyProperties(po, dto);
        dto.setPublicationDate(transformDate.localDateTime2String(po.getPublicationDate()));

        return dto;
    }

    @JsonIgnore
    public BookPO getBookPO() {
        BookPO po = new BookPO();
        BeanUtils.copyProperties(this, po);

        return po;
    }
    
    
    
    
    
    
}
