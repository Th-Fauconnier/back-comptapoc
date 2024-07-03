package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping( path = "accountBook")
public class AccountingBookController {

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    @GetMapping("/test")
    public void getTest(){
        System.out.println( "IT'S OK !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountingBookById(@PathVariable Long id){
        Optional<AccountingBook> optionalAccountingBook = accountingBookService.getAccountingBookById( id );
        if( optionalAccountingBook.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>(
                    optionalAccountingBook.get(),
                    HttpStatus.FOUND
            );
        }
    }
}
