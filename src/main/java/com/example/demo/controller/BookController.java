package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import com.example.demo.validator.annotation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.constant.VerifyRegexConst.PATTERN_NAME;

//import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@GetMapping(path = "/all/{id}")
	public List<BookDTO> getAllBook(@PathVariable("id") String id) {
		return bookService.getAllBook(id);
	}
	
	
	@GetMapping
	public List<BookDTO> getAllBookSort(@RequestParam(value="order",required = false) String order) {
		return bookService.getAllBookSort(order);
	}
	
	/*
	@GetMapping
	public List<BookDTO> getAllBookSort(@RequestParam(value="order",required = true) String order,
			@RequestParam(value="order2",required = false) String order2) {
		return bookService.getAllBookSort(order,order2);
	}
	*/

	@GetMapping(path = "/{name}")
	public BookDTO getBook(@Validator(pattern = PATTERN_NAME) @PathVariable("name") String name) {
		return bookService.getBookByName(name);
	}

	@GetMapping(path = "/au/{author}")
	public List<BookDTO> getBook1(@PathVariable("author") String author) {
		return bookService.getBookByAuthor(author);
	}

	@GetMapping(path = "/au/{author}/{publicationDate}")
	public BookDTO getBook1(@PathVariable("author") String author,
			@PathVariable("publicationDate") String publicationDate) {
		return bookService.getBookByAnP(author, publicationDate);
	}

	@PostMapping
	public BookDTO addBook(@RequestBody BookDTO book) {
		return bookService.addBook(book);
	}

	@DeleteMapping(path = "/{name}")
	public void removeBook(@Validator(pattern = PATTERN_NAME) @PathVariable("name") String name) {
		bookService.removeBookByName(name);
	}

	@PutMapping
	public BookDTO updateBook(@RequestBody BookDTO book) {
		return bookService.updateBook(book);
	}
}
