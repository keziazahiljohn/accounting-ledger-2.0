package com.pluralsight.accountingledger.service;

import com.pluralsight.accountingledger.model.Transaction;
import com.pluralsight.accountingledger.model.TransactionType;
import com.pluralsight.accountingledger.repository.TransactionRepository;

import java.util.List;

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
}
