package com.example.expense_tracker.repositories;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws ResourceNotFoundException;

    Category findById(Integer userId,Integer categoryId) throws ResourceNotFoundException;

    Integer create(Integer userId,String title,String description) throws BadRequestException;

    void update(Integer userId,String CategoryId,Category category) throws BadRequestException;

    void removeById(Integer userId,Integer categoryId);
}
