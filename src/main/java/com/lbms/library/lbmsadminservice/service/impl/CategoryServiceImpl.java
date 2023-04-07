package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.dto.category.CategoryDTO;
import com.lbms.library.core.dto.category.CategorySummaryDTO;
import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.core.util.constant.CategoryStatus;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.dto.CategoryPatchRequest;
import com.lbms.library.lbmsadminservice.entity.nosql.Category;
import com.lbms.library.lbmsadminservice.repository.mongodb.CategoryRepository;
import com.lbms.library.lbmsadminservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Override
    public CategoryDTO getCategoryById(String categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        ModelMapper modelMapper = new ModelMapper();

        if (categoryOptional.isEmpty()) {
            throw new LBMSException(LBMSError.CATEGORY_WITH_ID_DOES_NOT_EXIST);
        } else {
            Category category = categoryOptional.get();
            CategoryDTO categoryDTO;

            categoryDTO = modelMapper.map(category,CategoryDTO.class);

            return categoryDTO;
        }

    }

    @Override
    public void patchCategory(String categoryId, CategoryPatchRequest categoryPatchRequest) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new LBMSException(LBMSError.CATEGORY_WITH_ID_DOES_NOT_EXIST);
        }

        validateStatus(categoryPatchRequest);

        Category category = optionalCategory.get();

        category.setStatus(categoryPatchRequest.getStatus());

        categoryRepository.save(category);
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

    private void validateStatus(CategoryPatchRequest categoryPatchRequest) {
        if (!Arrays.stream(CategoryStatus.values()).anyMatch(categoryStatus -> categoryStatus.getStatus().equals(categoryPatchRequest.getStatus()))) {
            throw new LBMSException(LBMSError.INVALID_CATEGORY_STATUS);
        }
    }
}
