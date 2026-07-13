package com.pluralsight.accountingledger.service;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.model.TransactionType;
import com.pluralsight.accountingledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> allTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
    }

    public List<Transaction> getAllDeposits(){
        return transactionRepository.findByType(TransactionType.DEPOSIT);
    }

    public List<Transaction> getAllPayments(){
       return transactionRepository.findByType(TransactionType.PAYMENT);
    }

    public  List<Transaction> searchByDateRange(LocalDate date, LocalDate today){
        return transactionRepository.findByDateBetween(date, today);
    }

    public List<Transaction> findByVendor(String vendor){
        return transactionRepository.findByVendor(vendor);
    }
}
