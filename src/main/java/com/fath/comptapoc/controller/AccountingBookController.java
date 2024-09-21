package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.PutAccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping( path = "accountBook")
public class AccountingBookController {

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    private final ModelMapper modelMapper;

    public AccountingBookController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountingBookById(@PathVariable Long id) throws ClassNotFoundException {
        AccountingBook accountingBook = accountingBookService.getAccountingBookById( id );
        return new ResponseEntity<>(
                convertToDto( accountingBook ),
                HttpStatus.FOUND
        );
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
            AccountingBook accountingBook = accountingBookService.saveAccountingBook( convertToEntity( accountingBookDto ) );
           return new ResponseEntity<>(
                    convertToDto( accountingBook ),
                    HttpStatus.CREATED
           );
    }

    @PutMapping("/update")
    public ResponseEntity<?> putAccountingBook( @Valid @RequestBody PutAccountingBookDto putAccountingBookDto) throws ClassNotFoundException {
        AccountingBook accountingBookFromBdd = accountingBookService.getAccountingBookById( putAccountingBookDto.getId() );

        accountingBookFromBdd.setName( putAccountingBookDto.getName() );

        AccountingBook accountingBook = accountingBookService.putAccountingBook( accountingBookFromBdd );
        return new ResponseEntity<>(
                convertToDto( accountingBook ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountingBook( @PathVariable Long id ){
            accountingBookService.deleteAccountingBook( id );
            return new ResponseEntity<>( HttpStatus.OK );
    }

    private AccountingBook convertToEntity( AccountingBookDto accountingBookDto ) {
        return modelMapper.map( accountingBookDto, AccountingBook.class );
    }

    private AccountingBookDto convertToDto( AccountingBook accountingBook ) {
        return modelMapper.map( accountingBook, AccountingBookDto.class );
    }

    private AccountingBook convertToEntity( PutAccountingBookDto putAccountingBookDto) {
        return modelMapper.map(putAccountingBookDto, AccountingBook.class );
    }

}
