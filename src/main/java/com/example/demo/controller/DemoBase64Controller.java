package com.example.demo.controller;

import com.example.demo.service.DemoBase64Service;
import com.example.demo.service.DemoService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Base64")
public class DemoBase64Controller {

    @Autowired
    private DemoBase64Service demoBase64Service;

    @GetMapping
    public String getDemo() {
        return "demo";
    }

    
    @PostMapping(path = "/es")
    public Map<String, Object> encodeStringByBase64(@RequestBody String text) throws Exception { 
    	return demoBase64Service.encodeStringByBase64(text);
    	
    }
    @PostMapping(path = "/eb")
    public Map<String, Object> encodeByteByBase64(@RequestBody byte[] bytetxt) throws Exception {
    	return demoBase64Service.encodeByteByBase64(bytetxt);
    	
    }
    @PostMapping(path = "/ds")
    public Map<String, Object> decodeStringByBase64(@RequestBody String stringBase64) throws Exception {
    	return demoBase64Service.decodeStringByBase64(stringBase64);
    	
    }
    
    
    
    
    
}