package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.dtos.PutAccountingEntryDto;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping( path = "accountingEntry" )
public class AccountingEntryController {

    @Autowired
    private AccountingEntryServiceImpl accountingEntryService;

    private final ModelMapper modelMapper;

    public AccountingEntryController(){
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountingEntryById(@PathVariable Long id) throws ClassNotFoundException {
            AccountingEntry accountingEntry = accountingEntryService.getAccountingEntryById( id );
            return new ResponseEntity<>(
                    convertToDto( accountingEntry ),
                    HttpStatus.FOUND
            );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccountingEntry() {
        try {
            Set< AccountingEntryDto > accountingEntryDtoSet = new HashSet<>();

            accountingEntryService.getAll().forEach( accountingEntry -> {
                accountingEntryDtoSet.add(
                        convertToDto( accountingEntry )
                );
            });

            return new ResponseEntity<>(
                    accountingEntryDtoSet,
                    HttpStatus.OK
            );
        } catch (Exception e ){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccountingEntry(@Valid @RequestBody AccountingEntryDto accountingEntryDto ){
        try{
            AccountingEntry accountingEntry = accountingEntryService.saveAccountingEntry(
                    convertToEntity( accountingEntryDto )
            );
            return new ResponseEntity<>(
                    convertToDto( accountingEntry ),
                    HttpStatus.CREATED
            );
        } catch(ConstraintViolationException e ) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch(Exception e ){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> putAccountingEntry( @Valid @RequestBody PutAccountingEntryDto accountingEntryDto ){
        AccountingEntry accountingEntry = accountingEntryService.saveAccountingEntry(
                convertToEntity( accountingEntryDto )
        );
        return new ResponseEntity<>(
                convertToDto( accountingEntry ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountingEntry(@PathVariable Long id ){
        try {
            accountingEntryService.deleteAccountingEntry( id );
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (Exception e ){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private AccountingEntry convertToEntity( AccountingEntryDto accountingEntryDto ){
        return modelMapper.map( accountingEntryDto, AccountingEntry.class );
    }

    private AccountingEntryDto convertToDto(AccountingEntry accountingEntry ){
        return modelMapper.map( accountingEntry, AccountingEntryDto.class );
    }


}
