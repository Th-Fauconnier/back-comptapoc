package com.fath.comptapoc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "accountBook")
public class AccountingBookController {

    @GetMapping("/test")
    public void getTest(){
        System.out.println( "IT'S OK !");
    }
}
