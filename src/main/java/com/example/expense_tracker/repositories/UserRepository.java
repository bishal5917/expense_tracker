package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.exceptions.EtAuthException;

public interface UserRepository {

    Integer create(String firstName,String lastName,String email,String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

}
