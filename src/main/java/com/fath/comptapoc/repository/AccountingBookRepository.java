package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.AccountingBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountingBookRepository extends JpaRepository<AccountingBook, Long> {

    Optional<AccountingBook> findByName(String name);
}
