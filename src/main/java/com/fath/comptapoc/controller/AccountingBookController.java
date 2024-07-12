package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        try{
            Optional<AccountingBook> optionalAccountingBook = accountingBookService.getAccountingBookById( id );
            if( optionalAccountingBook.isEmpty() ){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND );
            } else {
                return new ResponseEntity<>(
                        optionalAccountingBook.get(),
                        HttpStatus.FOUND
                );
            }
        } catch( Exception e ){
            return new ResponseEntity<>(
                    "Unespected server error",
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccountingBooks() {
        return new ResponseEntity<>(
                accountingBookService.getAllAccountingBooks(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccountingBook( @Valid @RequestBody AccountingBook accountingBook ) {
        return new ResponseEntity<>(
                accountingBookService.saveAccountingBook( accountingBook ),
                HttpStatus.CREATED
        );
    }
}
