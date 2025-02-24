package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.repository.FinancialAccountRepository;
import com.fath.comptapoc.services.contracts.IFinancialAccountService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FinancialAccountServiceImpl implements IFinancialAccountService {

    @Autowired
    private Validator validator;

    @Autowired
    private FinancialAccountRepository financialAccountRepository;

    @Override
    public List< FinancialAccount > getAllFinancialAccount(){
        return financialAccountRepository.findAll();
    }

    @Override
    public FinancialAccount getFinancialAccountByName(String name)throws ClassNotFoundException {
        Optional< FinancialAccount > optionalFinancialAccount = financialAccountRepository.findByName( name );
        if( optionalFinancialAccount.isEmpty() ){
            throw new ClassNotFoundException( "FinancialAccount not found" );
        }else {
            return optionalFinancialAccount.get();
        }
    }

    @Override
    public FinancialAccount getFinancialAccountById(Long id) throws ClassNotFoundException {
        Optional< FinancialAccount > optionalFinancialAccount = financialAccountRepository.findById( id );
        if( optionalFinancialAccount.isEmpty() ){
            throw new ClassNotFoundException( "FinancialAccount not found" );
        }else {
            return optionalFinancialAccount.get();
        }
    }

    @Override
    public void deleteFinancialAccount(Long id){
        //todo à tester: il ne doit pas être possible de supprimer des comptes avec des opérations.
        financialAccountRepository.deleteById( id );
    }

    @Override
    public FinancialAccount saveFinancialAccount(FinancialAccount financialAccount) throws ConstraintViolationException {
        isFinancialAccountValid( financialAccount );
        return financialAccountRepository.save( financialAccount );
    }

   /* @Override
    public FinancialAccount putFinancialAccount(FinancialAccount financialAccount ){
        isFinancialAccountValid( financialAccount );
        return financialAccountRepository.save( financialAccount );
    }*/

    /**
     *
     * @param financialAccount as bean to check
     * @throws ConstraintViolationException
     * if the accountingEntry isn't a valid bean, a ConstraintViolation will be thrown
     * constraints are define in the accountingEntry bean
     */
    private void isFinancialAccountValid( FinancialAccount financialAccount ){
        Set<ConstraintViolation< FinancialAccount > > violations = validator.validate( financialAccount );

        if( !violations.isEmpty() ){
            StringBuilder sb = new StringBuilder();
            violations.forEach( v -> sb.append( v.getMessage() ).append(". "));
            throw new ConstraintViolationException( "Error occured: " + sb.toString(), violations );
        }
    }
}
