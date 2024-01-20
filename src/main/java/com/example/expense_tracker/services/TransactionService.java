package com.example.expense_tracker.services;

import com.example.expense_tracker.domain.Transaction;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionService {

    List<Transaction> fetchAllTransactions(Integer userId,Integer categoryId);

    Transaction fetchTransactionById(Integer userId,Integer categoryId,Integer transactionId) throws ResourceNotFoundException;

    Transaction addTransaction(Integer userId,Integer categoryId,Double amount,String note,Long transactionDate) throws BadRequestException;

    void updateTransaction(Integer userId,Integer categoryId,Integer transactionId,Transaction transaction) throws BadRequestException;

    void removeTransaction(Integer userId,Integer categoryId,Integer transactionId) throws ResourceNotFoundException;

}
