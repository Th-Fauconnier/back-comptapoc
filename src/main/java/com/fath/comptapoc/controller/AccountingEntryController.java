package com.fath.comptapoc.controller;

import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "accountingEntry" )
public class AccountingEntryController {

    @Autowired
    private AccountingEntryServiceImpl accountingEntryService;

    private final ModelMapper modelMapper;

    public AccountingEntryController(){
        this.modelMapper = new ModelMapper();
    }


}
