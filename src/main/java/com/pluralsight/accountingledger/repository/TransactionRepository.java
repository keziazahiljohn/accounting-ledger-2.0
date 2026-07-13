package com.pluralsight.accountingledger.repository;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByType(TransactionType type);

    public List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);

    public List<Transaction> findByVendor(String vendor);

}
