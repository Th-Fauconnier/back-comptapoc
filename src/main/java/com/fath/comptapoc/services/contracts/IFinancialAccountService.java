package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.FinancialAccount;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface IFinancialAccountService {

    /**
     * Retrieve all financialAccount in the database
     * @return Set of financialAccount found
     */
    List< FinancialAccount > getAllFinancialAccount();

    /**
     * Retrieve an financialAccount by his name attribute in the database
     * @param name as String
     * @return if it's existing in database, the financialAccount linked by the name attribute
     * @throws ClassNotFoundException if none is found with given name attribute
     */
    FinancialAccount getFinancialAccountByName(String name)throws ClassNotFoundException;

    /**
     * Retrieve an financialAccount by his id in the database
     * @param id as Long
     * @return if it's existing in database, the financialAccount linked by the id
     * @throws ClassNotFoundException if none is found with given id
     */
    FinancialAccount getFinancialAccountById(Long id) throws ClassNotFoundException;

    /**
     * Remove in database the financialAccount linked by id parameter, only if does not have operation
     * @param id as Long
     */
    void deleteFinancialAccount(Long id);

    /**
     * Create an financialAccount in database, with empty operation List
     * @param financialAccount as FinancialAccount
     */
    FinancialAccount saveFinancialAccount(FinancialAccount financialAccount) throws ConstraintViolationException;
}
