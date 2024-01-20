package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.Transaction;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS(TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?,?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return null;
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws BadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1,categoryId);
                ps.setInt(2,userId);
                ps.setDouble(3,amount);
                ps.setString(3,note);
                ps.setLong(3,transactionDate);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }
        catch(Exception e){
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws BadRequestException {

    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {

    }
}
