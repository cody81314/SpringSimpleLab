package com.example.demo.service;

import static com.example.demo.constant.CacheConst.CACHE_NAME_BOOK_CACHE;
import static com.example.demo.constant.CacheConst.CACHE_NAME_USER_CACHE;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0001;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0002;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.springframework.cache.annotation.CacheConfig;
import com.example.demo.dao.BookDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.error.DemoException;
import com.example.demo.error.DemoMessageInfo;
import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.util.HandleCacheUtil;
import com.example.demo.util.TransformDateUtil;

/**
 * @author BEAR
 *
 */
@Service
//@EnableCaching
//@CacheConfig(cacheNames = CACHE_NAME_BOOK_CACHE)
public class BookService implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private BookDao bookDao;

	@Autowired
	private Cache bookCache;

	@Autowired
	private TransformDateUtil transformDate;

	@Autowired
	private HandleCacheUtil handleCacheUtil;

	DemoMessageInfo dm = new DemoMessageInfo();

	@Cacheable(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#id")
	public List<BookDTO> getAllBook(String id) {

		LOGGER.info("======= Excute Method: {} ==========",
				Thread.currentThread().getStackTrace()[1].getMethodName());

		List<BookDTO> listBook = StreamSupport.stream(bookDao.findAll().spliterator(), false)
				.map(BookDTO::createByBookPO).collect(Collectors.toList());

		handleCacheUtil.queryAll4SingleRead(bookCache, listBook);

		LOGGER.info("======= End Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		return listBook;
	}

	@Cacheable(cacheNames = CACHE_NAME_BOOK_CACHE)
	public List<BookDTO> getAllBookSort(String order) {

		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());

		Comparator<BookDTO> compareParam = null;
		List<BookDTO> listBook2 = new ArrayList<BookDTO>();

		// compareParam = switchCase(order, order2);
		if (null != order) {
			compareParam = switchCase(order);
		}

		if (null != compareParam) {
			listBook2 = StreamSupport.stream(bookDao.findAll().spliterator(), false).map(BookDTO::createByBookPO)
					.sorted(compareParam).collect(Collectors.toList());
		} else {
			listBook2 = StreamSupport.stream(bookDao.findAll().spliterator(), false).map(BookDTO::createByBookPO)
					.collect(Collectors.toList());
		}

		LOGGER.info("======= End Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		return listBook2;
	}

	private Comparator<BookDTO> switchCase(String order) {
		Comparator<BookDTO> compareOrder = null;

		switch (order) {
		case "name":
			compareOrder = (book1, book2) -> Integer.compare(book1.getName().length(), book2.getName().length());
			compareOrder = compareOrder.reversed();

			break;
		case "author":
			compareOrder = (book1, book2) -> book1.getAuthor().compareTo(book2.getAuthor());

			break;
		case "date":
			compareOrder = (book1, book2) -> book1.getPublicationDate().compareTo(book2.getPublicationDate());
			compareOrder = compareOrder.reversed();
			break;
		default:
			LOGGER.error("===== order參數輸入錯誤,參數三擇一: name,	author,	date ========");
			break;
		}

		return compareOrder;
	}

	private Comparator<BookDTO> switchCase1(String order, String order2) {
		Comparator<BookDTO> compareOrder = null;

		switch (order) {
		case "name":
			compareOrder = (book1, book2) -> Integer.compare(book1.getName().length(), book2.getName().length());
			compareOrder = compareOrder.reversed();
			if (!StringUtils.equals(null, order2)) {
				compareOrder = switchCase2(order2, compareOrder);
			}
			break;
		case "author":
			compareOrder = (book1, book2) -> book1.getAuthor().compareTo(book2.getAuthor());
			if (!StringUtils.equals(null, order2)) {
				compareOrder = switchCase2(order2, compareOrder);
			}
			break;
		case "date":
			compareOrder = (book1, book2) -> book1.getPublicationDate().compareTo(book2.getPublicationDate());
			compareOrder = compareOrder.reversed();
			if (!StringUtils.equals(null, order2)) {
				compareOrder = switchCase2(order2, compareOrder);
			}
			break;
		default:
			LOGGER.error("===== order參數輸入錯誤,參數三擇一: name,	author,	date ========");
			break;
		}

		return compareOrder;
	}

	private Comparator<BookDTO> switchCase2(String order2, Comparator<BookDTO> compareOrder) {
		Comparator<BookDTO> compareOrder1 = null;

		switch (order2) {
		case "name":
			compareOrder1 = (book1, book2) -> Integer.compare(book1.getName().length(), book2.getName().length());
			compareOrder1 = compareOrder1.reversed();
			break;
		case "author":
			compareOrder1 = (book1, book2) -> book1.getAuthor().compareTo(book2.getAuthor());
			break;
		case "date":
			compareOrder1 = (book1, book2) -> book1.getPublicationDate().compareTo(book2.getPublicationDate());
			compareOrder1 = compareOrder1.reversed();
			break;
		default:
			break;
		}

		compareOrder = compareOrder.thenComparing(compareOrder1);

		return compareOrder;

	}

	@Cacheable(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#name")
	public BookDTO getBookByName(String name) {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		BookDTO book = bookDao.findByName(name).map(BookDTO::createByBookPO)
				.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, dm.msg4Query(name)));
		// cacheconfig.bookCache().put(name, book);

		LOGGER.info("======= End Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		return book;
	}

	@Cacheable(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#author")
	public List<BookDTO> getBookByAuthor(String author) {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());

		if (bookDao.findByAuthor(author).isPresent()) {
			return StreamSupport.stream(bookDao.findByAuthor(author).get().spliterator(), false)
					.map(BookDTO::createByBookPO).collect(toList());
		} else {
			throw DemoException.createByCodeAndExtInfo(E0001, dm.msg4QueryAuthor(author));
		}
	}

	public BookDTO getBookByAnP(String author, String publicationDate) {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());

		return bookDao.findByAnP(author, publicationDate)
				.map(BookDTO::createByBookPO)
				.orElseThrow(
				() -> DemoException.createByCodeAndExtInfo(E0001, dm.msg4QueryAnP(author, publicationDate)));
	}

	@Transactional
	@CachePut(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#bookDTO.name")
	public BookDTO addBook(BookDTO bookDTO) {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		BookPO bookPO = bookDTO.getBookPO();
		if (null == bookDTO.getName()) {
			throw DemoException.createByCodeAndExtInfo(E0001, "書名不能為null");
		}

		if (null == bookDTO.getAuthor()) {
			throw DemoException.createByCodeAndExtInfo(E0001, "作者不能為null");
		}

		if (null != bookDTO.getPublicationDate()) {
			bookPO.setPublicationDate(transformDate.string2LocalDateTime(bookDTO.getPublicationDate()));
		} else {
			throw DemoException.createByCodeAndExtInfo(E0001, "日期不能為null");
		}

		if (null != bookCache.get("queryall")) {
			handleCacheUtil.add2QueryAll(bookCache, bookDTO);
		}
		return BookDTO.createByBookPO(bookDao.save(bookPO));
	}

	@Transactional
	@CacheEvict(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#name")
	public void removeBookByName(String name) {

		bookDao.deleteByName(name);

		if (null != bookCache.get("queryall")) {
			handleCacheUtil.deleteFromQueryAll(bookCache, name);
		}
	}

	@Transactional
	@CachePut(cacheNames = CACHE_NAME_BOOK_CACHE, key = "#bookDTO.name")
	public BookDTO updateBook(BookDTO bookDTO) {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());
		return updateBook(
				bookDao -> bookDao.findByName(bookDTO.getName()).orElseThrow(
						() -> DemoException.createByCodeAndExtInfo(E0002, dm.msg4Modify(bookDTO.getName()))),
				bookPO -> {
					if (!StringUtils.equals(null, bookDTO.getName())) {
						bookPO.setName(bookDTO.getName());
					}
					if (!StringUtils.equals(null, bookDTO.getAuthor())) {
						bookPO.setAuthor(bookDTO.getAuthor());
					}
					if (!StringUtils.equals(null, bookDTO.getPublicationDate())) {
						bookPO.setPublicationDate(transformDate.string2LocalDateTime(bookDTO.getPublicationDate()));
					}

					if (null != bookCache.get("queryall")) {
						handleCacheUtil.update2QueryAll(bookCache, bookDTO);
					}
				}

		);
	}

	private BookDTO updateBook(Function<BookDao, BookPO> selector, Consumer<BookPO> setter) {
		BookPO bookPO = selector.apply(bookDao);
		setter.accept(bookPO);

		return BookDTO.createByBookPO(bookPO);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author BEAR note: set schedule to print Quantity of Book
	 */
	public void pringBookQuantity() {
		LOGGER.info("======= The quantity of book is : {} 本　==========", bookDao.count());
	}

}
