package com.example.demo.service;

import com.example.demo.dao.BookDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserNBookDTO;
import com.example.demo.error.DemoException;
import com.example.demo.error.DemoMessageInfo;
import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.util.TransformDateUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0001;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0002;

@Service
public class UserNBookServcie {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

	TransformDateUtil transformDate = new TransformDateUtil();

	DemoMessageInfo dm = new DemoMessageInfo();

	/* 同時新增使用者和書籍(有Transactional for Exception and runtimeException) */
	// @Transactional(rollbackFor = Exception.class, noRollbackFor=
	// {Exception.class,RuntimeException.class})
	// @Transactional(rollbackFor = Exception.class, noRollbackFor=
	// {Exception.class,RuntimeException.class})
	@Transactional(rollbackFor = Exception.class)
	public UserNBookDTO addUserNBook(UserNBookDTO userNBookDTO) throws Exception {
		return addUserNBookC(userNBookDTO);
	}

	/* 同時更新使用者和書籍(有Transactional) */
	@Transactional(noRollbackFor = { Exception.class, RuntimeException.class })
	public UserNBookDTO updateUserNBook(UserNBookDTO userNBookDTO) throws Exception {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());

		// Update User
		LOGGER.info("======= 修改User開始 ==========");
		UserPO userPO = getUserByUserCode(userNBookDTO.getUserCode());
		if (StringUtils.isBlank(userNBookDTO.getUserCode())) {
			throw new Exception(DemoException.createByCodeAndExtInfo(E0001, "User Code cannot be null or Blank"));
		} else {
			userPO.setUserCode(userNBookDTO.getUserCode());
		}

		if (!StringUtils.equals(null, userNBookDTO.getPcode())) {
			userPO.setPcode(userNBookDTO.getPcode());
		}

		userDao.save(userPO);
		LOGGER.info("======= 修改User完畢 ==========");
		/*-------------------------------*/

		// Update Book
		LOGGER.info("======= 修改Book開始 ==========");
		BookPO bookPO = getBookByName1(userNBookDTO.getName());

		if (StringUtils.isBlank(userNBookDTO.getName())) {
			throw new Exception(DemoException.createByCodeAndExtInfo(E0001, "Book Name cannot be null or Blank"));
		} else {
			bookPO.setName(userNBookDTO.getName());
		}

		if (!StringUtils.equals(null, userNBookDTO.getAuthor())) {
			bookPO.setAuthor(userNBookDTO.getAuthor());
		}

		if (!StringUtils.equals(null, userNBookDTO.getPublicationDate())) {
			bookPO.setPublicationDate(transformDate.string2LocalDateTime(userNBookDTO.getPublicationDate()));
		}

		bookDao.save(bookPO);
		LOGGER.info("======= 修改Book完畢 ==========");
		/*-------------------------------*/

		return userNBookDTO;
	}

	/* 同時新增使用者和書籍(無Transactional) */
	public UserNBookDTO addUserNBook1(UserNBookDTO userNBookDTO) throws Exception {
		return addUserNBookC(userNBookDTO);
	}

	/* 同時新增使用者和書籍(有Transactional但無控制Exception rollback) */
	@Transactional
	public UserNBookDTO addUserNBook2(UserNBookDTO userNBookDTO) throws Exception {
		return addUserNBookC(userNBookDTO);
	}

	/* 同時新增使用者和書籍(有Transactional 同時用rollbackc和 noRollbackFor控制Exception rollback) */
	@Transactional(rollbackFor = Exception.class, noRollbackFor = { Exception.class, RuntimeException.class })
	public UserNBookDTO addUserNBook3(UserNBookDTO userNBookDTO) throws Exception {
		return addUserNBookC(userNBookDTO);
	}

	public UserNBookDTO addUserNBookC(UserNBookDTO userNBookDTO) throws Exception {
		LOGGER.info("======= Excute Method: {} ==========", Thread.currentThread().getStackTrace()[1].getMethodName());

		// JSONObject

		// Add User
		LOGGER.info("======= 新增User開始 ==========");
		UserDTO userDTO = new UserDTO();

		if (StringUtils.isBlank(userNBookDTO.getUserCode())) {
			throw new Exception(DemoException.createByCodeAndExtInfo(E0001, "User Code cannot be null or Blank"));
		} else {
			userDTO.setUserCode(userNBookDTO.getUserCode());
		}

		userDTO.setPcode(userNBookDTO.getPcode());
		UserPO userPO = userDTO.getUserPO();
		userDao.save(userPO);
		LOGGER.info("======= 新增User完畢 ==========");

		// Add Book
		LOGGER.info("======= 新增Book開始 ==========");
		BookDTO bookDTO = new BookDTO();

		if (StringUtils.isBlank(userNBookDTO.getName())) {
			throw new Exception(DemoException.createByCodeAndExtInfo(E0001, "Book Name cannot be null or Blank "));
		} else {
			bookDTO.setName(userNBookDTO.getName());
		}

		if (!StringUtils.equals(null, userNBookDTO.getAuthor())) {
			bookDTO.setAuthor(userNBookDTO.getAuthor());
		}

		bookDTO.setPublicationDate(userNBookDTO.getPublicationDate());
		BookPO bookPO = bookDTO.getBookPO();
		bookPO.setPublicationDate(transformDate.string2LocalDateTime(bookDTO.getPublicationDate()));
		bookDao.save(bookPO);
		LOGGER.info("======= 新增Book完畢 ==========");

		// UserDTO.createByUserPO(userDao.save(userPO));

		return userNBookDTO;
	}

	public BookPO getBookByName1(String name) {

		return bookDao.findByName(name)
				.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0002, dm.msg4Modify(name)));

	}

	public UserPO getUserByUserCode(String userCode) {
		return userDao.findByUserCode(userCode)
				.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found User by userSeq."));

	}

}
