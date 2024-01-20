package com.example.expense_tracker.services;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.exceptions.BadRequestException;
import com.example.expense_tracker.exceptions.ResourceNotFoundException;
import com.example.expense_tracker.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId) throws ResourceNotFoundException {
        return categoryRepository.findById(userId,categoryId);
    }

    @Override
    public Category addCategory(Integer userId, String title, String description) throws BadRequestException {
        int catId = categoryRepository.create(userId, title, description);
        return categoryRepository.findById(userId,catId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId ,Category category) throws BadRequestException {
         categoryRepository.update(userId,categoryId,category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws ResourceNotFoundException {

    }
}
