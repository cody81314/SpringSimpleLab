package com.example.demo.dao;

import com.example.demo.po.BookPO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDao extends CrudRepository<BookPO, Integer> {

    Optional<BookPO> findByName(String name);
    
    //int countBookByAll();
    

    Optional<String> deleteByName(String name);
    
    @Query(value = "SELECT * FROM DEMO_BOOK  WHERE AUTHOR = :author", nativeQuery = true)	
    Optional<List<BookPO>> findByAuthor(@Param("author")String author); 

	@Query(value = "SELECT * FROM DEMO_BOOK  WHERE AUTHOR = :author AND PUBLICATION_DATE = :publicationDate ", nativeQuery = true)	
    Optional<BookPO> findByAnP(@Param("author")String author, @Param("publicationDate")String publicationDate); 
    

}
