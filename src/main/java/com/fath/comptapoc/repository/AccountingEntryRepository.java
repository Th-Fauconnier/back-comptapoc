package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.AccountingEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountingEntryRepository extends CrudRepository<AccountingEntry, Long> {
    Set<AccountingEntry> findAll();
}
