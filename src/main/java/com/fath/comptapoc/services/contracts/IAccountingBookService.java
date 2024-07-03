package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.AccountingBook;

import java.util.Optional;

public interface IAccountingBookService {

    Optional<AccountingBook> getAccountingBookById(final Long id);

    Iterable<AccountingBook> getAllAccountingBooks();

    void deleteAccountingBook( final Long id);

    AccountingBook saveAccountingBook( AccountingBook accountingBook );
}
