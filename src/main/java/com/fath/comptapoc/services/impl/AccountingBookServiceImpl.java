package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.repository.AccountingBookRepository;
import com.fath.comptapoc.services.contracts.IAccountingBookService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountingBookServiceImpl implements IAccountingBookService {

    @Autowired
    private Validator validator;

    @Autowired
    private AccountingBookRepository accountingBookRepository;

    /**
     * Retrieve an accountingBook by his id in the database
     * @param id as Long
     * @return if it's existing in database, the accountingBook linked by the id
     * @throws ClassNotFoundException if none is found with given id
     */
    public AccountingBook getAccountingBookById(final Long id) throws ClassNotFoundException {
        Optional< AccountingBook > optionalAccountingBook = accountingBookRepository.findById( id );
        if( optionalAccountingBook.isEmpty() ) {
            throw new ClassNotFoundException( "AccountingBook not found" );
        }else {
            return optionalAccountingBook.get();
        }
    }

    /**
     * Retrieve all accountingBook in the database
     * @return Set of accountingBooks found
     */
    public Set<AccountingBook> getAllAccountingBooks(){
        return accountingBookRepository.findAll();
    }

    public void deleteAccountingBook( final Long id) {
        accountingBookRepository.deleteById( id );
    }

    public AccountingBook saveAccountingBook( AccountingBook accountingBook ) throws ConstraintViolationException {

        isAccountingBookValid( accountingBook );

        return accountingBookRepository.save( accountingBook  );
    }

    /**
     *
     * @param accountingBookDto as bean to check
     * @throws ConstraintViolationException
     * if the accountingBookDto isn't a valid bean, a ConstraintViolation will be threw
     * constraints are define in the accountingBookDto bean
     */
    private void isAccountingBookDtoValid( AccountingBookDto accountingBookDto ) throws ConstraintViolationException {
        Set<ConstraintViolation<AccountingBookDto>> violations = validator.validate( accountingBookDto );

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AccountingBookDto> constraintViolation : violations) {
                sb.append( constraintViolation.getMessage() );
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }

    /**
     *
     * @param accountingBook as bean to check
     * @throws ConstraintViolationException
     * if the accountingBook isn't a valid bean, a ConstraintViolation will be threw
     * constraints are define in the accountingBook bean
     */
    private void isAccountingBookValid( AccountingBook accountingBook ) throws ConstraintViolationException {
        Set<ConstraintViolation<AccountingBook>> violations = validator.validate( accountingBook );

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AccountingBook> constraintViolation : violations) {
                sb.append( constraintViolation.getMessage() );
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }
}
