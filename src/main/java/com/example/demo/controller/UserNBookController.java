package com.example.demo.controller;

import com.example.demo.dto.UserNBookDTO;
import com.example.demo.service.UserNBookServcie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(path = "/bnu")
public class UserNBookController {

    @Autowired
    private UserNBookServcie userNBookServcie;

    
    @PostMapping
    public UserNBookDTO addUserNBook(@RequestBody UserNBookDTO userNBookDTO) throws Exception {
    	return userNBookServcie.addUserNBook(userNBookDTO);
    }
    
    @PutMapping
    public UserNBookDTO updateUserNBook(@RequestBody UserNBookDTO userNBookDTO) throws Exception {
    	return userNBookServcie.updateUserNBook(userNBookDTO);
    }
    
    @PostMapping(path = "/noTrx")
    public UserNBookDTO addUserNBook1(@RequestBody UserNBookDTO userNBookDTO) throws Exception {
    	return userNBookServcie.addUserNBook1(userNBookDTO);
    }
    
    @PostMapping(path = "/noEx")
    public UserNBookDTO addUserNBook2(@RequestBody UserNBookDTO userNBookDTO) throws Exception {
    	return userNBookServcie.addUserNBook2(userNBookDTO);
    }
    
    @PostMapping(path = "/ExRNR")
    public UserNBookDTO addUserNBook3(@RequestBody UserNBookDTO userNBookDTO) throws Exception {
    	return userNBookServcie.addUserNBook3(userNBookDTO);
    }
    

}
