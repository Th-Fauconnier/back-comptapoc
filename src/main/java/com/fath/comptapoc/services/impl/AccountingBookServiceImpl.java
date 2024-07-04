package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.mappers.IAccountingBookMapper;
import com.fath.comptapoc.repository.AccountingBookRepository;
import com.fath.comptapoc.services.contracts.IAccountingBookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AccountingBookServiceImpl implements IAccountingBookService {

    @Autowired
    private AccountingBookRepository accountingBookRepository;

    @Autowired
    private IAccountingBookMapper accountingBookMapper;

    public AccountingBookDto getAccountingBookById(final Long id) throws ClassNotFoundException {
        Optional< AccountingBook > optionalAccountingBook = accountingBookRepository.findById( id );
        if( optionalAccountingBook.isEmpty() ) {
            throw new ClassNotFoundException( "AccountingBook not found" );
        }else {
            return accountingBookMapper.convertToAccountingBookDto( optionalAccountingBook.get() );
        }
    }

    public Iterable<AccountingBookDto> getAllAccountingBooks(){
        return accountingBookMapper.mapToSetOfAccountingBookDto(
                accountingBookRepository.findAll()
        );
    }

    public void deleteAccountingBook( final Long id) {
        accountingBookRepository.deleteById( id );
    }

    public AccountingBookDto saveAccountingBook( AccountingBookDto accountingBook ){
        return accountingBookMapper.convertToAccountingBookDto(
                accountingBookRepository.save(
                        accountingBookMapper.convertToAccountingBook( accountingBook )
                )
        );
    }
}
