package com.example.demo.scheduler;

import com.example.demo.service.BookService;
import com.example.demo.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.DB_SCHEDULER_CLEAN_UP_LOGOUT_TOKEN_CRON;
import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.DB_SCHEDULER_PRINT_OUT_BOOK_QUANTITY_CRON;
@Component
public class DbScheduler {

    @Autowired
    private SchedulerService schedulerService;
    
    @Autowired
    private BookService bookService;

    @Scheduled(cron = DB_SCHEDULER_CLEAN_UP_LOGOUT_TOKEN_CRON)
    public void cleanUpLogoutToken() {
        schedulerService.cleanUpExpiredLogoutToken();
    }
    
    //@Scheduled(fixedRate = 10000, initialDelay = 5000)	
  	//@Scheduled(fixedDelay = 10000)
  	//@Scheduled(fixedRateString = "10000",initialDelay = 20000)
  	@Scheduled(cron=DB_SCHEDULER_PRINT_OUT_BOOK_QUANTITY_CRON)
  	public  void pringBookQuantity() {
  		bookService.pringBookQuantity();
  	}
}