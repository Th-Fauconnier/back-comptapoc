package com.fath.comptapoc.repository;

import com.fath.comptapoc.model.entities.FinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {
}
