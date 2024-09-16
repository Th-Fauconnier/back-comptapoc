package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.AccountingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Long> {
 //   L<AccountingEntry> findAll();
}
