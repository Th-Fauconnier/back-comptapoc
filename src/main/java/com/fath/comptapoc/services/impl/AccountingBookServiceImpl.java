package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.repository.AccountingBookRepository;
import com.fath.comptapoc.services.contracts.IAccountingBookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AccountingBookServiceImpl implements IAccountingBookService {

    @Autowired
    private AccountingBookRepository accountingBookRepository;

    public Optional<AccountingBook> getAccountingBookById(final Long id){
        return accountingBookRepository.findById( id );
    }

    public Iterable<AccountingBook> getAllAccountingBooks(){
        return accountingBookRepository.findAll();
    }

    public void deleteAccountingBook( final Long id) {
        accountingBookRepository.deleteById( id );
    }

    public AccountingBook saveAccountingBook( AccountingBook accountingBook ){
        return accountingBookRepository.save( accountingBook );
    }
}
