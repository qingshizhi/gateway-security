package com.david.demo.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/index")
@RestController
public class IndexController {
	

    @GetMapping("/hello")
    public String  demo(){
        return "hello";
    }
    
    
    
}






