package com.fath.comptapoc.controller;

import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "accountBook")
public class AccountingBookController {

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    @GetMapping("/test")
    public void getTest(){
        System.out.println( "IT'S OK !");
    }
}
