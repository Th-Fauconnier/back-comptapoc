package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.AccountingBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountingBookRepository extends CrudRepository<AccountingBook, Long> {
    Set<AccountingBook> findAll();
}
