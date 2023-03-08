package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.entity.nosql.Category;
import com.lbms.library.lbmsadminservice.repository.mongodb.CategoryRepository;
import com.lbms.library.lbmsadminservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void addCategory(CategoryCreateRequest categoryCreateRequest) {
        checkCategoryExists(categoryCreateRequest.getCategory());

        Category category = new Category();
        category.setCategory(categoryCreateRequest.getCategory());
        category.setDescription(categoryCreateRequest.getDescription());
        category.setCreatedBy("Admin");
        category.setCreatedDate(new Date());
        category.setActive(true);

        categoryRepository.save(category);
    }

    private void checkCategoryExists(String category) {
        List<Category> existingCategoryList = categoryRepository.findByCategory(category);
        List<Category> activeCategory = existingCategoryList.stream().filter((category1) -> category1.isActive()).collect(Collectors.toList());

        if (activeCategory.size() > 0) {
            throw new LBMSException(LBMSError.CATEGORY_EXISTS);
        }
    }
}
