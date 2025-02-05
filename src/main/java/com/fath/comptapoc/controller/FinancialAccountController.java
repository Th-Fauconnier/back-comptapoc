package com.fath.comptapoc.controller;

import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "financialAccount")
public class FinancialAccountController {

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    private ModelMapper modelMapper;

    public FinancialAccountController() { this.modelMapper = new ModelMapper(); }
}
