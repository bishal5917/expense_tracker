package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.Transaction;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    private static final String SQL_FIND_ALL = "SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID=? AND CATEGORY_ID=?";
    private static final String SQL_FIND_BY_ID = "SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID=? AND CATEGORY_ID=? AND TRANSACTION_ID=?";
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS(TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?,?,?)";
    private static final String SQL_UPDATE = "UPDATE ET_TRANSACTIONS SET AMOUNT=?,NOTE=?,TRANSACTION_DATE=? WHERE USER_ID=? AND CATEGORY_ID=? AND TRANSACTION_ID=?";
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTIONS WHERE USER_ID=? AND CATEGORY_ID=? AND TRANSACTION_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        try{
            return jdbcTemplate.query(SQL_FIND_ALL,new Object[]{userId,categoryId},transactionRowMapper);
        }
        catch(Exception e){
            throw new ResourceNotFoundException("No transactions to show");
        }
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId,transactionId},transactionRowMapper);
        }
        catch(Exception e){
            throw new ResourceNotFoundException("Not Found");
        }
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
        try{
            jdbcTemplate.update(SQL_UPDATE,new Object[]{transaction.getAmount(),transaction.getNote(),transaction.getTransactionDate(),userId,categoryId,transactionId});
        }
        catch(Exception e){
            throw new BadRequestException("Bad Request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
     int count = jdbcTemplate.update(SQL_DELETE,new Object[]{userId,categoryId,transactionId});
     if (count==0){
         throw new ResourceNotFoundException("Transaction not found");
     }
    }

    private RowMapper<Transaction> transactionRowMapper = (((rs, rowNum) -> {
        return new Transaction(
                rs.getInt("TRANSACTION_ID"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE")
        );
    }));
}
