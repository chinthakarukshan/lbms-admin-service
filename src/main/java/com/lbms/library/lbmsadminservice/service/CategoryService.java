package com.lbms.library.lbmsadminservice.service;

import com.lbms.library.core.dto.category.CategoryDTO;
import com.lbms.library.core.dto.category.CategorySummaryDTO;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryCreateRequest categoryCreateRequest);

    List<CategorySummaryDTO> getCategoryList();

    CategoryDTO getCategoryById(String categoryId);
}
