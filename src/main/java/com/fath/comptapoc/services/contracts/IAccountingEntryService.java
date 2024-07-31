package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.AccountingEntry;
import jakarta.validation.ConstraintViolationException;

public interface IAccountingEntryService {

    AccountingEntry saveAccountingEntry( AccountingEntry accountingEntry ) throws ConstraintViolationException;

    Iterable<AccountingEntry> getAll();

    AccountingEntry getAccountingEntryById( Long id ) throws ClassNotFoundException;

    void deleteAccountingEntry( Long id );

}
