package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.repository.AccountingEntryRepository;
import com.fath.comptapoc.services.contracts.IAccountingEntryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountingEntryServiceImpl implements IAccountingEntryService {

    @Autowired
    private Validator validator;

    @Autowired
    private AccountingEntryRepository accountingEntryRepository;

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
