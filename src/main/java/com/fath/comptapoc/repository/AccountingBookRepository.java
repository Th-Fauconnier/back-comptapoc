package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.AccountingBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingBookRepository extends CrudRepository<AccountingBook, Long> {
}
