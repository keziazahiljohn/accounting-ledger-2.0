package com.pluralsight.accountingledger.repository;

import com.pluralsight.accountingledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
