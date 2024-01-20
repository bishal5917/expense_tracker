package com.example.expense_tracker.services;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);

    Category fetchCategoryById(Integer userId,Integer categoryId) throws ResourceNotFoundException;

    Category addCategory(Integer userId,String title,String description) throws BadRequestException;

    void updateCategory(Integer userId,Integer categoryId,Category category) throws BadRequestException;

    void removeCategoryWithAllTransactions(Integer userId,Integer categoryId) throws ResourceNotFoundException;

}
