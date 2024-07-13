package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface IAccountingBookService {

    AccountingBookDto getAccountingBookById(final Long id) throws ClassNotFoundException;

    Iterable<AccountingBookDto> getAllAccountingBooks();

    void deleteAccountingBook( final Long id);

    AccountingBookDto saveAccountingBook( AccountingBookDto accountingBookDto ) throws ConstraintViolationException;

}
