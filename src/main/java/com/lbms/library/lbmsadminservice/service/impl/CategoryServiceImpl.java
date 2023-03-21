package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.dto.category.CategorySummaryDTO;
import com.lbms.library.core.dto.member.MemberSummaryDTO;
import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.core.util.constant.CategoryStatus;
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

        //TODO Improve the code to set the created by field using authenticated context
        category.setCreatedBy("Admin");
        category.setCreatedDate(new Date());
        category.setStatus(CategoryStatus.ACTIVE.getStatus());

        categoryRepository.save(category);
    }

    @Override
    public List<CategorySummaryDTO> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        ModelMapper modelMapper = new ModelMapper();

        List<CategorySummaryDTO> categorySummaryDTOList = categoryList.stream()
                                                                            .map(category -> modelMapper.map(category, CategorySummaryDTO.class))
                                                                            .collect(Collectors.toList());

        return categorySummaryDTOList;
    }

    private void checkCategoryExists(String category) {
        List<Category> existingCategoryList = categoryRepository.findByCategory(category);
        List<Category> activeCategory = existingCategoryList.stream()
                                                            .filter((category1) -> category1.getStatus()
                                                                                            .equals(CategoryStatus.ACTIVE.getStatus()))
                                                            .collect(Collectors.toList());

        if (activeCategory.size() > 0) {
            throw new LBMSException(LBMSError.CATEGORY_EXISTS);
        }
    }
}
