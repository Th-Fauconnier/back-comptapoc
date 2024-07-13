package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
            AccountingBookDto accountingBookDto = accountingBookService.getAccountingBookById( id );
            return new ResponseEntity<>(
                    accountingBookDto,
                    HttpStatus.FOUND
            );
        } catch ( ClassNotFoundException e ) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }catch ( Exception e ){
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
    public ResponseEntity<?> createAccountingBook( @Valid @RequestBody AccountingBookDto accountingBookDto ) {
        try{
            return new ResponseEntity<>(
                    accountingBookService.saveAccountingBook( accountingBookDto ),
                    HttpStatus.CREATED
            );
        } catch (ConstraintViolationException e ){
            System.out.println( e.getMessage() );
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch( Exception e ){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
