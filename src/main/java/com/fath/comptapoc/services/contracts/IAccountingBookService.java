package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.AccountingBook;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface IAccountingBookService {

    /**
     * Retrieve an accountingBook by his id in the database
     * @param id as Long
     * @return if it's existing in database, the accountingBook linked by the id
     * @throws ClassNotFoundException if none is found with given id
     */
    AccountingBook getAccountingBookById(final Long id) throws ClassNotFoundException;

    /**
     * Retrieve an accountingBook by his name attribute in the database
     * @param name as String
     * @return if it's existing in database, the accountingBook linked by the name attribute
     * @throws ClassNotFoundException if none is found with given name attribute
     */
    AccountingBook getAccountingBookByName(String name) throws ClassNotFoundException;

    /**
     * Retrieve all accountingBook in the database
     * @return Set of accountingBooks found
     */
    List<AccountingBook> getAllAccountingBooks();

    /**
     * Remove in database the accountingBook linked by id parameter, all children will be deleted by cascade
     * @param id as Long
     */
    void deleteAccountingBook( final Long id);

    /**
     * Create an accountingBook in database
     * @param accountingBook as AccountingBook
     */
    AccountingBook saveAccountingBook( AccountingBook accountingBook ) throws ConstraintViolationException;

    /**
     * update an accountingBook in database
     * @param accountingBook as AccountingBook
     */
    AccountingBook putAccountingBook( AccountingBook accountingBook );
}
