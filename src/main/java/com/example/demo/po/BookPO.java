package com.example.demo.po;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Persistant Object 持久物件
 *
 * 一個 PO 就是 DB 中的一條記錄。好處是可以把一條記錄作為一個物件處理，可以方便的轉為其它物件。
 */
@Entity
@Table(name = "DEMO_BOOK")
@DynamicInsert
@DynamicUpdate
@Data
public class BookPO {

    @Id
    @Column(name = "BOOK_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookSeq;
    
    
    @Column(name = "NAME", unique = true, length = 40)
    private String name;

    @Column(name = "AUTHOR", length = 500)
    private String author;
    
    @Column(name = "PUBLICATION_DATE", length = 20)
    private  LocalDateTime  publicationDate;
    
    /*
    public int getBookSeq() {
        return bookSeq;
    }

    public void setBookSeq(int bookSeq) {
        this.bookSeq = bookSeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
  
	
	public String getPublicationDate() {
		return publicationDate;
	}

	
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	*/
    
}
