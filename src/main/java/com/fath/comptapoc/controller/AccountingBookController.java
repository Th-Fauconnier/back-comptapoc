package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping( path = "accountBook")
public class AccountingBookController {

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/test")
    public void getTest(){
        System.out.println( "IT'S OK !");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountingBookById(@PathVariable Long id){
        try{
            AccountingBook accountingBook = accountingBookService.getAccountingBookById( id );
            return new ResponseEntity<>(
                    convertToDto( accountingBook ),
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
        Set< AccountingBookDto > accountingBookDtoSet = new HashSet<>();
        accountingBookService.getAllAccountingBooks().forEach( accountingBook -> {
            accountingBookDtoSet.add(
                    convertToDto( accountingBook )
            );
        });
        return new ResponseEntity<>(
                accountingBookDtoSet,
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccountingBook( @Valid @RequestBody AccountingBookDto accountingBookDto ) {
        try{
            AccountingBook accountingBook = accountingBookService.saveAccountingBook( convertToEntity( accountingBookDto ) );
            return new ResponseEntity<>(
                    convertToDto( accountingBook ),
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

    private AccountingBook convertToEntity( AccountingBookDto accountingBookDto ) {
        return modelMapper.map( accountingBookDto, AccountingBook.class );
    }

    private AccountingBookDto convertToDto( AccountingBook accountingBook ) {
        return modelMapper.map( accountingBook, AccountingBookDto.class );
    }
}
