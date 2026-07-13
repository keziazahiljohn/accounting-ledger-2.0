package com.pluralsight.accountingledger.repository;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByType(TransactionType type);

}
