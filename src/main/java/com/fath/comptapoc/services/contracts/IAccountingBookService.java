package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface IAccountingBookService {

    AccountingBook getAccountingBookById(final Long id) throws ClassNotFoundException;

    Iterable<AccountingBook> getAllAccountingBooks();

    void deleteAccountingBook( final Long id);

    AccountingBook saveAccountingBook( AccountingBook accountingBook ) throws ConstraintViolationException;

}
