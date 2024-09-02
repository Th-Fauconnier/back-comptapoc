package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.AccountingBook;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public interface IAccountingBookService {

    AccountingBook getAccountingBookById(final Long id) throws ClassNotFoundException;

    Set<AccountingBook> getAllAccountingBooks();

    void deleteAccountingBook( final Long id);

    AccountingBook saveAccountingBook( AccountingBook accountingBook ) throws ConstraintViolationException;

}
