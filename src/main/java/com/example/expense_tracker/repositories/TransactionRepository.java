package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.Transaction;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll(Integer userId,Integer categoryId);

    Transaction findById(Integer userId,Integer categoryId,Integer transactionId) throws ResourceNotFoundException;

    Integer create(Integer userId,Integer categoryId,Double amount,String note,Long transactionDate) throws BadRequestException;

    void update(Integer userId,Integer categoryId,Integer transactionId,Transaction transaction) throws BadRequestException;

    void removeById(Integer userId,Integer categoryId,Integer transactionId) throws ResourceNotFoundException;

}
