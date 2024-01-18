package com.example.expense_tracker.services;

import com.example.expense_tracker.domain.User;
import com.example.expense_tracker.exceptions.AuthException;

public interface UserService {
    User validateUser(String email,String password) throws AuthException;

    User registerUser(String firstName,String lastName,String email,String password) throws AuthException;

}
