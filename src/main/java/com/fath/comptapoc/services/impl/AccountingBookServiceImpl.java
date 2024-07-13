package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.mappers.IAccountingBookMapper;
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

    public AccountingBookDto saveAccountingBook( AccountingBookDto accountingBook ) throws ConstraintViolationException {

        isAccountingBookDtoValid( accountingBook );

        return accountingBookMapper.convertToAccountingBookDto(
                accountingBookRepository.save(
                        accountingBookMapper.convertToAccountingBook( accountingBook )
                )
        );
    }

    /**
     *
     * @param AccountingBookDto
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
}
