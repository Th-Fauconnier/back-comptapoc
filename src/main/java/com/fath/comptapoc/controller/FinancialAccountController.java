package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping( path = "financialAccount")
public class FinancialAccountController {

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    private ModelMapper modelMapper;

    public FinancialAccountController() { this.modelMapper = new ModelMapper(); }

    @PostMapping("/create")
    public ResponseEntity<?> createFinancialAccount(@Valid @RequestBody FinancialAccountDto financialAccountDto) {
        FinancialAccount financialAccount = financialAccountService.saveFinancialAccount(
                convertToEntity( financialAccountDto )
        );
        return new ResponseEntity<>(
                convertToDto( financialAccount ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByFinancialAccountId(@PathVariable Long id) throws ClassNotFoundException {
        FinancialAccount financialAccount = financialAccountService.getFinancialAccountById( id );
        return new ResponseEntity<>(
                convertToDto( financialAccount ),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFinancialAccount(){
        Set< FinancialAccountDto > financialAccountDtoSet = new HashSet<>();
        financialAccountService.getAllFinancialAccount().forEach( financialAccount -> {
            financialAccountDtoSet.add(
                    convertToDto( financialAccount )
            );
        });
        return new ResponseEntity<>(
                financialAccountDtoSet,
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    public ResponseEntity<?> putFinancialAccount(@Valid @RequestBody FinancialAccountDto financialAccountDto ) throws ClassNotFoundException {
        FinancialAccount financialAccountFromBDD = financialAccountService.getFinancialAccountById( financialAccountDto.getId() );
        financialAccountFromBDD.setName( financialAccountDto.getName() );
        financialAccountFromBDD.setCode( financialAccountDto.getCode() );
        FinancialAccount financialAccount = financialAccountService.saveFinancialAccount( financialAccountFromBDD );
        return new ResponseEntity<>(
                convertToDto( financialAccount ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFinancialAccount( @PathVariable Long id ){
        financialAccountService.deleteFinancialAccount( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    private FinancialAccount convertToEntity( FinancialAccountDto financialAccountDto ){
        return modelMapper.map( financialAccountDto, FinancialAccount.class );
    }

    private FinancialAccountDto convertToDto(FinancialAccount financialAccount ){
        return modelMapper.map( financialAccount, FinancialAccountDto.class );
    }
}
