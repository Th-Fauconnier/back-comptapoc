package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.services.contracts.IAccountingEntryService;
import jakarta.validation.ConstraintViolationException;

public class AccountingEntryServiceImpl implements IAccountingEntryService {
    @Override
    public AccountingEntry saveAccountingEntry(AccountingEntry accountingEntry) throws ConstraintViolationException {
        return null;
    }

    @Override
    public Iterable<AccountingEntry> getAll() {
        return null;
    }

    @Override
    public AccountingEntry getAccountingEntryById(Long id) throws ClassNotFoundException {
        return null;
    }

    @Override
    public void deleteAccountingEntry(Long id) {

    }
}
