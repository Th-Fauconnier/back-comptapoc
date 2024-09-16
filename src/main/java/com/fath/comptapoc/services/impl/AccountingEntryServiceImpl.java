package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.repository.AccountingEntryRepository;
import com.fath.comptapoc.services.contracts.IAccountingEntryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountingEntryServiceImpl implements IAccountingEntryService {

    @Autowired
    private Validator validator;

    @Autowired
    private AccountingEntryRepository accountingEntryRepository;

    @Override
    public AccountingEntry saveAccountingEntry(AccountingEntry accountingEntry) throws ConstraintViolationException {
        System.out.println( "test2");
        isAccountingEntryValid( accountingEntry );
        System.out.println( "test3");
        return accountingEntryRepository.save( accountingEntry );
    }

    @Override
    public List<AccountingEntry> getAll() {
        return accountingEntryRepository.findAll();
    }

    @Override
    public AccountingEntry getAccountingEntryById(Long id) throws ClassNotFoundException {
        Optional<AccountingEntry> optionalAccountingEntry = accountingEntryRepository.findById( id );
        if( optionalAccountingEntry.isEmpty() ){
            throw new ClassNotFoundException( "AccountingEntry not found" );
        }else {
            return optionalAccountingEntry.get();
        }
    }

    @Override
    public void deleteAccountingEntry(Long id) {
        accountingEntryRepository.deleteById( id );
    }

    private void isAccountingEntryDtoValid( AccountingEntryDto accountingEntryDto ){

    }

    /**
     *
     * @param accountingEntry as bean to check
     * @throws ConstraintViolationException
     * if the accountingEntry isn't a valid bean, a ConstraintViolation will be thrown
     * constraints are define in the accountingEntry bean
     */
    private void isAccountingEntryValid( AccountingEntry accountingEntry ){
        Set<ConstraintViolation<AccountingEntry>> violations = validator.validate( accountingEntry );

        if( !violations.isEmpty() ){
            StringBuilder sb = new StringBuilder();
            violations.forEach( v -> sb.append( v.getMessage() ).append(". "));
            throw new ConstraintViolationException( "Error occured: " + sb.toString(), violations );
        }
    }
}
